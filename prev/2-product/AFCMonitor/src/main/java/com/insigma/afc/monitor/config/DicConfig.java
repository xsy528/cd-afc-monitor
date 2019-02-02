package com.insigma.afc.monitor.config;

import com.insigma.commons.dic.loader.IDicClassListProvider;
import com.insigma.commons.dic.loader.annotation.ClasspathAnnotationScanProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ticket: 字典配置
 *
 * @author xuzhemin
 * 2019-01-24:11:59
 */
@Configuration
public class DicConfig {

    @Bean
    public IDicClassListProvider dicListProvider(){
        return new ClasspathAnnotationScanProvider();
    }
}
