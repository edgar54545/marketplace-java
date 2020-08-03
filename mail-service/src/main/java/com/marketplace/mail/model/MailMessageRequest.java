package com.marketplace.mail.model;

public class MailMessageRequest {
    private EmailType emailType;
    private String receiverEmail;

    public MailMessageRequest(EmailType emailType, String receiverEmail) {
        this.emailType = emailType;
        this.receiverEmail = receiverEmail;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }
}
