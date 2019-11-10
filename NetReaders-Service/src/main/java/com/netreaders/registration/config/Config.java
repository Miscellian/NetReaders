package com.netreaders.registration.config;


import java.util.Properties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@EnableWebSecurity
//@EnableTransactionManagement
@ComponentScan(basePackages = "com.netreaders.registration")
@PropertySource("classpath:application.properties")
public class Config {

	// ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}


	@Bean(name = "mailSender")
	public MailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);
		javaMailSender.setProtocol("smtp");
		javaMailSender.setUsername("viktorkysil1997@gmail.com");
		javaMailSender.setPassword("qazxswedc");
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.debug", "true");
		javaMailSender.setJavaMailProperties(mailProperties);
		return javaMailSender;
	}
	
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
