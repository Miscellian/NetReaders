package com.netreaders.registration.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.netreaders.registration.model.User;
import com.netreaders.registration.service.IUserService;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

	@Autowired
    private Environment env;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private MailSender mailSender;

	public void onApplicationEvent(final OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationSuccessEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        this.userService.createVerificationToken(user, token);
        
        SimpleMailMessage email = this.constructEmailMessage(event, user, token);
        this.mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationSuccessEvent event, final User user, final String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/confirmRegistration.html?token=" + token;
        String message = this.messages.getMessage("message.registrationSuccessConfimationLink", (Object[])null, event.getLocale());
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(this.env.getProperty("support.email"));
        return email;
    }
	
	
}
