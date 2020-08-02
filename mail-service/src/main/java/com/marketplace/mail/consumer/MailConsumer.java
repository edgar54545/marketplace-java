package com.marketplace.mail.consumer;

import com.marketplace.mail.config.JmsConfig;
import com.marketplace.mail.model.MailMessageRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {

    @JmsListener(destination = JmsConfig.MAIL_DESTINATION)
    public void processMessage(MailMessageRequest mail) {

    }
}
