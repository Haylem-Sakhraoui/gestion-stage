package com.esprit.backend.Controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MailController {

    private final JavaMailSender javaMailSender;

    public MailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/send-email")
    public String sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mahjoubioussema928@gmail.com");
        message.setSubject("New Cv Added");
        message.setText("A new cv has been added.");

        javaMailSender.send(message);

        return "Email sent successfully";
    }
}