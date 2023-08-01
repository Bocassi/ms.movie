package com.example.ms.movie.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(String recipient, String subject, String content);
}
