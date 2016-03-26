package com.vs2.microblog.entity;

/**
 * Created by Walde on 26.03.16.
 */
public class Message {

    private long messageId;
    private long userId;
    private String body;
    private long dateTime;

    public Message(long userId, String body, long dateTime) {
        this.userId = userId;
        this.body = body;
        this.dateTime = dateTime;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
