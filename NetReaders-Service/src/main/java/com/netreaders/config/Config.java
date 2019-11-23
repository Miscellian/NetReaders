package com.netreaders.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = "com.netreaders")
@PropertySource("classpath:application.properties")
public class Config {

//	@Bean
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//
//		viewResolver.setPrefix("WEB-INF/view/");
//		viewResolver.setSuffix(".jsp");
//
//		return viewResolver;
//	}
	
	@Bean
     public MessageSource messageSource() {
	     final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	     messageSource.setBasename("classpath:messages_en.properties");
	     messageSource.setUseCodeAsDefaultMessage(true);
	     messageSource.setDefaultEncoding("UTF-8");
	     messageSource.setCacheSeconds(0);
	     return messageSource;
     }
}

