package com.esprit.backend.Services;

import com.esprit.backend.DTO.Mail;
import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendMail(Mail mail) throws MessagingException;
}
