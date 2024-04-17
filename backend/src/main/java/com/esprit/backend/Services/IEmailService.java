package com.esprit.backend.Services;

import com.esprit.backend.auth.Mail;
import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendMail(Mail mail) throws MessagingException;
}