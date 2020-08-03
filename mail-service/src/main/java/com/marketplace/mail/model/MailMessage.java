package com.marketplace.mail.model;

public interface MailMessage {
    EmailType getType();
    String getContent();
    String getSubject();
}
