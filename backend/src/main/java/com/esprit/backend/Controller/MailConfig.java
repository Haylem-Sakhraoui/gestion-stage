package com.esprit.backend.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Set the SMTP hostname (assuming Gmail)
        mailSender.setPort(587); // Set the SMTP port

        mailSender.setUsername("stageservice63@gmail.com"); // Set your Gmail username
        mailSender.setPassword("rjjc qwsa lpmm klsx"); // Set your Gmail password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true"); // Enable SMTP authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        return mailSender;
    }
}
