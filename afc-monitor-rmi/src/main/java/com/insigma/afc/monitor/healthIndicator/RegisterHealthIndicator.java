/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.healthIndicator;

import com.insigma.afc.workbench.rmi.IBaseCommandService;
import com.insigma.afc.workbench.rmi.RegisterResult;
import com.insigma.commons.communication.ftp.FtpInfo;
import com.insigma.commons.spring.datasource.DESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.ArrayList;
import java.util.List;

/**
 * Ticket: 通讯前置机连接检测
 *
 * @author xuzhemin
 * 2019/3/12 20:51
 */
public class RegisterHealthIndicator extends AbstractHealthIndicator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterHealthIndicator.class);

    private boolean isOnline;
    private IBaseCommandService cmdService;

    public RegisterHealthIndicator(IBaseCommandService cmdService){
        this.cmdService = cmdService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (isOnline) {
            try {
                cmdService.isAlive();
                builder.up();
                isOnline = true;
            } catch (Exception e) {
                builder.down(e);
                isOnline = false;
                LOGGER.error("检测到服务器离线。", e);
            }
        } else {
            try {
                // RMI调用
                RegisterResult registerResult = cmdService.register();
                if (registerResult != null && registerResult.getResult() == 0) {

                    List<FtpInfo> importFtpList = registerResult.getImportFTPList();
                    List<FtpInfo> newImportFtpList = new ArrayList<FtpInfo>();

                    for (FtpInfo ftpInfo : importFtpList) {
                        if (!ftpInfo.isEmpty() && !"".equals(ftpInfo.getPass()) && ftpInfo.getPass() != null) {
                            String pass = DESUtil.decrypt(ftpInfo.getPass());
                            ftpInfo.setPass(pass);
                        }
                        newImportFtpList.add(ftpInfo);
                    }

                    List<FtpInfo> exportFtpList = registerResult.getExportFTPList();
                    List<FtpInfo> newExportFtpList = new ArrayList<FtpInfo>();

                    for (FtpInfo ftpInfo : exportFtpList) {
                        if (!ftpInfo.isEmpty() && !"".equals(ftpInfo.getPass()) && ftpInfo.getPass() != null) {
                            String pass = DESUtil.decrypt(ftpInfo.getPass());
                            ftpInfo.setPass(pass);
                        }
                        newExportFtpList.add(ftpInfo);
                    }

//                    IFTPInfoManager infoManager = new FTPInfoManager(newImportFtpList, newExportFtpList);
//                    Application.registerService(IFTPInfoManager.class, infoManager);
                    LOGGER.info("获取服务器ftp信息");
                    if (registerResult.getExportFTPList() != null) {
                        LOGGER.info("-----------------导出FTP信息------------------");
                        for (FtpInfo info : registerResult.getExportFTPList()) {
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

                    if (registerResult.getImportFTPList() != null) {
                        LOGGER.info("-----------------导入FTP信息------------------");
                        for (FtpInfo info : registerResult.getImportFTPList()) {
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
                    builder.up();
                    isOnline = true;
                    LOGGER.info("注册服务端成功。");
                } else {
                    LOGGER.info("注册服务端失败。");
                }
            } catch (Exception e) {
                LOGGER.error("无法注册到到服务器。", e);
                // 修改状态
                builder.down(e);
                isOnline = false;
            }
        }
    }
}