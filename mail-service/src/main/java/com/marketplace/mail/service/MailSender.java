package com.marketplace.mail.service;

import com.marketplace.mail.model.MailMessage;

public interface MailSender {
    void sendMessage(MailMessage mailMessage, String receiverEmail);
}
