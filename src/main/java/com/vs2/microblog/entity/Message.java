package com.vs2.microblog.entity;

import com.google.gson.Gson;

/**
 * Created by Walde on 26.03.16.
 */
public class Message {

    private long messageId;
    private String userEmail;
    private String body;
    private long dateTime;

    public Message(long messageId, String userEmail, String body, long dateTime) {
        this.messageId = messageId;
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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Message fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Message.class);
    }
}
