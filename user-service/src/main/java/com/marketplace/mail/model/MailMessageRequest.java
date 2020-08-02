package com.marketplace.mail.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailMessageRequest {
    private EmailType emailType;
    private String receiverEmail;
}
