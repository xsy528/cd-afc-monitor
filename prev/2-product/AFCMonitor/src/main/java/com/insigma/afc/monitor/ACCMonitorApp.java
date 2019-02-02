/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * Ticket: ACC工作台
 * @author xuzhemin
 *
 */

@SpringBootApplication(scanBasePackages = {"com.insigma.afc"})
@EnableJpaRepositories("com.insigma.afc")
@EntityScan("com.insigma.afc")
public class ACCMonitorApp {

	public static void main(String[] args) {
		SpringApplication.run(ACCMonitorApp.class,args);
	}

}
