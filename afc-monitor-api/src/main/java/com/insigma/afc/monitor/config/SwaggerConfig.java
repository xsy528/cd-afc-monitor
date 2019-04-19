package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.model.properties.ApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		//增加认证请求头
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(parameterBuilder
				.name("Authorization")
				.description("认证信息")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false).build());

		return new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(true)
				.select()
					.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
					.paths(PathSelectors.any())
					.build()
				.globalOperationParameters(parameters)
				.apiInfo(apiInfo(apiProperties));
	}

	private ApiInfo apiInfo(ApiProperties apiProperties){
		return new ApiInfo(apiProperties.getTitle(),
				apiProperties.getDescription(),
				apiProperties.getVersion(),
				apiProperties.getTermsOfServiceUrl(),
				new Contact(apiProperties.getContactName(), apiProperties.getContactUrl(),
						apiProperties.getContactEmail()),
				apiProperties.getLicense(),
				apiProperties.getLicenseUrl(),
				Collections.emptyList());
	}
}
