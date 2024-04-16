package com.esprit.backend.Controller;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("cv")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

  private final JavaMailSender javaMailSender;

  public EmailController(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @PostMapping("/send-email")
  public String sendEmail() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("maatoug.maha16@gmail.com");
    message.setSubject("New Cv Added");
    message.setText("A new cv has been added.");

    javaMailSender.send(message);

    return "Email sent successfully";
  }
}
