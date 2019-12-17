package com.netreaders.service.impl;

import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.netreaders.service.RegistrationTokenService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;
    private RegistrationTokenService registrationTokenService;

    @Override
    public void sendEmail(User user) {
    	RegistrationToken token = registrationTokenService.createToken(user);
        try {
            mailSender.send(constructEmailMessage(user, token));
        } catch (MailException | MessagingException e) {
            log.error("Email couldn't be sent");
        }
    }

    private MimeMessage constructEmailMessage(User user, RegistrationToken token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setTo(user.getEmail());
        helper.setSubject("Registration confirmation");
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>To finish your registration please click the link below:</h2>\n");
        builder.append("<a href=https://netreaders.herokuapp.com/confirmRegistration?token=");
        builder.append(token.getToken());
        builder.append("><button style=\"width: 200px; padding: 20px; color: #f0f0f0; border: none; background: #5db4e9; font-size: 14px; margin: 20px auto; display: block; cursor: pointer!important;\">Confirm Registration</button></a>");
        helper.setText(builder.toString(), true);
        helper.setFrom("NetReaders-noreply");

        return msg;
    }


}
