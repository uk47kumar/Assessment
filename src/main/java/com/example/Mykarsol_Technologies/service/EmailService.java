package com.example.Mykarsol_Technologies.service;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
    String getNotificationEmail();
}
