package com.esprit.backend.Services;

import com.esprit.backend.auth.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service

public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
@Override
    public void sendMail(Mail mail) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String htmlContent =  mail.getBody() ;
        helper.setText(htmlContent, true);
        helper.setFrom("haylemskr001@gmail.com");
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());

        javaMailSender.send(message);
    }

}
