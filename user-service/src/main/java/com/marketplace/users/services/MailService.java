package com.marketplace.users.services;

import com.marketplace.mail.model.EmailType;

public interface MailService {
    void sendMessage(EmailType type, String receiverMail);
}
