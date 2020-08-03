package com.marketplace.mail.model;

public class ProfileUpdateMessage implements MailMessage {
    private static ProfileUpdateMessage INSTANCE;

    private final String content;
    private final String subject;

    private ProfileUpdateMessage() {
        subject = "Profile";
        content = "Profile Updated Successfully!!!";
    }

    static ProfileUpdateMessage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProfileUpdateMessage();
        }

        return INSTANCE;
    }


    @Override
    public EmailType getType() {
        return EmailType.PROFILE_UPDATE_MAIL;
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
