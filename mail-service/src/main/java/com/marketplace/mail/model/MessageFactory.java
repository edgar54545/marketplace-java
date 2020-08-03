package com.marketplace.mail.model;

public class MessageFactory {

    public static MailMessage getMessage(EmailType emailType) {
        switch (emailType) {
            case GREETING_MAIL:
                return GreetingMessage.getInstance();
            case PROFILE_UPDATE_MAIL:
                return ProfileUpdateMessage.getInstance();
            default:
                throw new IllegalArgumentException("Invalid Email Type: " + emailType);
        }
    }
}
