package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.model.properties.ApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * author: xuzhemin
 * 2018/10/15 18:07
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties(ApiProperties.class)
public class SwaggerConfig {

	@Bean
	public Docket docket(ApiProperties apiProperties) {
		ApiInfo apiInfo = new ApiInfo(apiProperties.getTitle(), apiProperties.getDescription(),
				apiProperties.getVersion(), apiProperties.getTermsOfServiceUrl(),
				new Contact(apiProperties.getContactName(), apiProperties.getContactUrl(),
						apiProperties.getContactEmail()),
				apiProperties.getLicense(), apiProperties.getLicenseUrl(), Collections.emptyList());
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build().apiInfo(apiInfo);
	}
}
