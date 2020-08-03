package com.marketplace.mail.service;

import com.marketplace.mail.model.MailMessageRequest;

public interface MailConsumerService {
    void processMessage(MailMessageRequest mail);
}
