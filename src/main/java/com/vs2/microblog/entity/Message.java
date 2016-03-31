package com.vs2.microblog.entity;

/**
 * Created by Walde on 26.03.16.
 */
public class Message {

    private long messageId;
    private String userEmail;
    private String body;
    private long dateTime;

    public Message(String userEmail, String body, long dateTime) {
        this.userEmail = userEmail;
        this.body = body;
        this.dateTime = dateTime;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
