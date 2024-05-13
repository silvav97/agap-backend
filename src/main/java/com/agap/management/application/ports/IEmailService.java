package com.agap.management.application.ports;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

public interface IEmailService {
    @Async
    void sendEmail(String to, String subject, String content, String url, String buttonMessage) throws MessagingException;
}
