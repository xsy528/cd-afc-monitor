/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*
 *
 */
package com.insigma.afc.monitor.healthIndicator;

import com.insigma.afc.monitor.properties.HealthProperties;
import com.insigma.afc.workbench.rmi.IBaseCommandService;
import com.insigma.afc.workbench.rmi.RegisterResult;
import com.insigma.commons.communication.ftp.FtpInfo;
import com.insigma.commons.spring.datasource.DESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Ticket: 通讯前置机连接检测
 *
 * @author xuzhemin
 * 2019/3/12 20:51
 */
public class RegisterHealthIndicator extends AbstractHealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterHealthIndicator.class);

    private boolean isOnline;
    private Throwable throwable;
    private IBaseCommandService cmdService;
    private HealthProperties properties;
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,new ThreadFactory());

    public RegisterHealthIndicator(IBaseCommandService cmdService, HealthProperties properties) {
        this.cmdService = cmdService;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        this.executorService.scheduleWithFixedDelay(new Task(),properties.getInitialDelay(),properties.getFixedDelay()
                ,TimeUnit.SECONDS);
    }

    @Override
    protected void doHealthCheck(Health.Builder builder){
        if (isOnline) {
            builder.up();
        } else if (this.throwable != null) {
            builder.down(throwable);
        } else {
            builder.down();
        }
    }

    @PreDestroy
    public void destroy() {
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e1) {
                executorService.shutdownNow();
            } finally {
                executorService = null;
            }
        }
    }

    private void logFtpInfo(List<FtpInfo> infos) {
        for (FtpInfo info : infos) {
            LOGGER.info("FTP的KEY类型=" + info.getFtpKey());
            LOGGER.info("FTP目录类别=" + info.getFileType());
            LOGGER.info("FTP服务器IP=" + info.getHost());
            LOGGER.info("FTP服务器访问用户名=" + info.getUser());
            LOGGER.info("FTP服务器访问密码=" + info.getPass());
            LOGGER.info("FTP目录工作目录=" + info.getWorkDir());
            LOGGER.info("FTP是否为被动模式=" + info.isPassiveMode());
            LOGGER.info("-----------------------------------");
        }
    }

    private class ThreadFactory implements java.util.concurrent.ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            Thread singleThread = new Thread(r);
            singleThread.setName("通讯前置机连接检测线程");
            return singleThread;
        }
    }

    private class Task implements Runnable{

        @Override
        public void run() {
            throwable = null;
            if (isOnline) {
                try {
                    cmdService.isAlive();
                    isOnline = true;
                } catch (Exception e) {
                    throwable = e;
                    isOnline = false;
                    LOGGER.error("检测到服务器离线。", e);
                }
            } else {
                try {
                    // RMI调用
                    RegisterResult registerResult = cmdService.register();
                    if (registerResult != null && registerResult.getResult() == 0) {
                        List<FtpInfo> importFtpList = registerResult.getImportFTPList();
                        for (FtpInfo ftpInfo : importFtpList) {
                            if (!ftpInfo.isEmpty() && !"".equals(ftpInfo.getPass()) && ftpInfo.getPass() != null) {
                                String pass = DESUtil.decrypt(ftpInfo.getPass());
                                ftpInfo.setPass(pass);
                            }
                        }
                        List<FtpInfo> exportFtpList = registerResult.getExportFTPList();
                        for (FtpInfo ftpInfo : exportFtpList) {
                            if (!ftpInfo.isEmpty() && !"".equals(ftpInfo.getPass()) && ftpInfo.getPass() != null) {
                                String pass = DESUtil.decrypt(ftpInfo.getPass());
                                ftpInfo.setPass(pass);
                            }
                        }
                        LOGGER.info("获取服务器ftp信息");
                        if (registerResult.getExportFTPList() != null) {
                            LOGGER.info("-----------------导出FTP信息------------------");
                            logFtpInfo(registerResult.getExportFTPList());
                        }
                        if (registerResult.getImportFTPList() != null) {
                            LOGGER.info("-----------------导入FTP信息------------------");
                            logFtpInfo(registerResult.getImportFTPList());
                        }
                        isOnline = true;
                        LOGGER.info("注册服务端成功。");
                    } else {
                        LOGGER.info("注册服务端失败。");
                    }
                } catch (Exception e) {
                    LOGGER.error("无法注册到到服务器。", e);
                    // 修改状态
                    throwable = e;
                    isOnline = false;
                }
            }
        }
    }
}
