package com.marketplace.mail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MailMessageRequest {
    private EmailType emailType;
    private String receiverEmail;
}
