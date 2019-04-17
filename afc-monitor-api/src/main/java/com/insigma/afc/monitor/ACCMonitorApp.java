/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor;

import com.insigma.commons.dic.annotation.DicScan;
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

@SpringBootApplication(scanBasePackages = "com.insigma")
@DicScan("com.insigma")
@EnableJpaRepositories(basePackages = "com.insigma")
@EntityScan(basePackages = "com.insigma")
public class ACCMonitorApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ACCMonitorApp.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}

}
