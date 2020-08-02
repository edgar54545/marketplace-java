package com.marketplace.users.services;

import com.marketplace.mail.model.EmailType;
import com.marketplace.mail.model.MailMessageRequest;
import com.marketplace.users.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    private final JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(EmailType type, String receiverMail) {
        jmsTemplate.convertAndSend(JmsConfig.MAIL_DESTINATION, new MailMessageRequest(type, receiverMail));
    }
}
