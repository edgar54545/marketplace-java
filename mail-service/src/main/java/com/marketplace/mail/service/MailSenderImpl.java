package com.marketplace.mail.service;

import com.marketplace.mail.model.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailSenderImpl implements MailSender {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(MailMessage mailMessage, String receiverEmail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(mailMessage.getSubject());
        msg.setText(mailMessage.getContent());
        msg.setSentDate(new Date());
        msg.setTo(receiverEmail);

        javaMailSender.send(msg);
    }
}
