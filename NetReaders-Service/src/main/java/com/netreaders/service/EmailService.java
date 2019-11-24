package com.netreaders.service;

import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.netreaders.dto.SignUpForm;
import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;

@Service
public class EmailService {

	private JavaMailSender mailSender;
	
	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendEmail(User user,RegistrationToken token ) {
		try {
			mailSender.send(constructEmailMessage(user,token));
		} catch (MailException | MessagingException e) {
			e.printStackTrace();
		}
    }
	
    private MimeMessage constructEmailMessage(User user,RegistrationToken token) throws MessagingException {
    	MimeMessage msg = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(msg);
    	helper.setTo(user.getEmail());
    	helper.setSubject("Registration confirmation");
		StringBuilder builder = new StringBuilder();
		builder.append("<h2>To finish your registration please click the link below:</h2>\n");
		builder.append("<a href=http://localhost:8080/confirmRegistration?token=");
		builder.append(token.getToken());
		builder.append("><button style=\"width: 200px; padding: 20px; color: #f0f0f0; border: none; background: #5db4e9; font-size: 14px; margin: 20px auto; display: block; cursor: pointer!important;\">Confirm Registration</button></a>");
		helper.setText(builder.toString(),true);
		helper.setFrom("NetReaders-noreply");
		
        return msg;
    }


	
}
