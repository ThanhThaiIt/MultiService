package com.example.multiservice.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendConfirmActiveEmail(String data);

}
