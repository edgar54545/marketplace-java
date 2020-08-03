package com.marketplace.mail.service;

import com.marketplace.mail.config.JmsConfig;
import com.marketplace.mail.model.MailMessage;
import com.marketplace.mail.model.MailMessageRequest;
import com.marketplace.mail.model.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MailConsumerServiceImpl implements MailConsumerService {
    private MailSender mailSender;

    @Autowired
    public MailConsumerServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @JmsListener(destination = JmsConfig.MAIL_DESTINATION)
    public void processMessage(MailMessageRequest mail) {
        MailMessage mailMessage = MessageFactory.getMessage(mail.getEmailType());

        mailSender.sendMessage(mailMessage, mail.getReceiverEmail());
    }
}
