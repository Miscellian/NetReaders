package com.netreaders.registration;

import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.netreaders.models.User;
import com.netreaders.service.UserService;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

	@Autowired
    private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(env.getProperty("smtp.gmail.com"));
	    mailSender.setPort(587);

	    mailSender.setUsername(env.getProperty("spring.mail.username"));
	    mailSender.setPassword(env.getProperty("spring.mail.password"));

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", env.getProperty("spring.mail.properties.mail.transport.protocol"));
	    props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
	    props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
	    props.put("mail.smtp.starttls.required", env.getProperty("spring.mail.properties.mail.smtp.starttls.required"));

	    return mailSender;
	}

	public void onApplicationEvent(final OnRegistrationSuccessEvent event) {
        try {
			this.confirmRegistration(event);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
    }

    private void confirmRegistration(final OnRegistrationSuccessEvent event) throws SQLException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        this.userService.createTokenName(user, token);
        
        SimpleMailMessage email = this.constructEmailMessage(event, user, token);
        this.mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationSuccessEvent event, final User user, final String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:4200/confirmRegistration?token=" + token;
        String message = this.messages.getMessage("To confirm your account click the link: ", (Object[])null, event.getLocale());
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("gt5570a54321z@gmail.com");
        return email;
    }
	
	
}