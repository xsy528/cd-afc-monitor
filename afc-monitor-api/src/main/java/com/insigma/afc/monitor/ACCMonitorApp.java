/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor;

import com.insigma.afc.monitor.util.CDNodeIdStrategy;
import com.insigma.commons.dic.annotation.DicScan;
import com.insigma.commons.util.NodeIdUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * Ticket: ACC工作台
 * @author xuzhemin
 *
 */

@SpringBootApplication
@DicScan("com.insigma")
@EnableJpaRepositories(basePackages = "com.insigma.afc.monitor.dao")
@EntityScan(basePackages = "com.insigma.afc.monitor.model.entity")
public class ACCMonitorApp {

	public static void main(String[] args) {
		NodeIdUtils.nodeIdStrategy = new CDNodeIdStrategy();
		SpringApplication app = new SpringApplication(ACCMonitorApp.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}

}
