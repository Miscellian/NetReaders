package com.netreaders.service.impl;

import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(User user, RegistrationToken token) {
        try {
            mailSender.send(constructEmailMessage(user, token));
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private MimeMessage constructEmailMessage(User user, RegistrationToken token) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setTo(user.getEmail());
        helper.setSubject("Registration confirmation");
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>To finish your registration please click the link below:</h2>\n");
        builder.append("<a href=http://localhost:4200/confirmUser?token=");
        builder.append(token.getToken());
        builder.append("><button style=\"width: 200px; padding: 20px; color: #f0f0f0; border: none; background: #5db4e9; font-size: 14px; margin: 20px auto; display: block; cursor: pointer!important;\">Confirm Registration</button></a>");
        helper.setText(builder.toString(), true);
        helper.setFrom("NetReaders-noreply");

        return msg;
    }


}