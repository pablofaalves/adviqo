package com.adviqo.searchservice;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Application startup class
 * 
 * @author Pablo Alves
 * @since 12/11/2019
 */
@SpringBootApplication
@EnableSwagger2
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

	
	/**
	 * Instantiates a Docket bean for swagger
	 * 
	 * @return
	 */
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName())).paths(PathSelectors.any())
				.build().apiInfo(generateApiInfo());
	}

	/**
	 * Creates ApiInfo object with information about this application for swagger
	 * 
	 * @return ApiInfo
	 */
	@SuppressWarnings("rawtypes")
	private ApiInfo generateApiInfo() {
		return new ApiInfo("Adviqo Challenge - Expert Search Service",
				"This is a small Rest Webservice for Adviqo search service challenge", "Version 1.0 - mw", "urn:tos",
				new Contact("Pablo Alves", "", "pablofaalves@gmail.com"), "Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
	}
}
