package com.marketplace.mail.model;

public class GreetingMessage implements MailMessage {
    private static GreetingMessage INSTANCE;

    private final String content;
    private final String subject;

    private GreetingMessage() {
        subject = "Welcome!!!";
        content = "Thank you for joining us!!!";
    }

    static GreetingMessage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GreetingMessage();
        }

        return INSTANCE;
    }

    @Override
    public EmailType getType() {
        return EmailType.GREETING_MAIL;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getSubject() {
        return subject;
    }
}
