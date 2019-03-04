package com.insigma.afc.monitor.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-03-04 18:30
 */
public class CommandThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandThreadPoolExecutor.class);

    public CommandThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),(r)->{
            Thread t = new Thread(r);
            t.setName("发送命令线程");
            return t;
        },(r,e)->LOGGER.error("执行命令线程被拒绝:{}",r.toString()));
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
          if (t == null && r instanceof Future<?>) {
            try {
              Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
          }
          if (t != null) {
              LOGGER.error("发送命令异常",t);
          }
    }

}
