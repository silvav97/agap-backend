package com.agap.management.application.ports;

import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendEmail(String to, String subject, String content, String url, String buttonMessage) throws MessagingException;
}
