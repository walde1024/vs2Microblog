package com.vs2.microblog.view;

/**
 * Created by Walde on 04.04.16.
 */
public class TimelineView {

    private String body;
    private long dateTime;
    private String author;
    private String authorEmail;

    public TimelineView(String body, long dateTime, String author, String userEmail) {
        this.body = body;
        this.dateTime = dateTime;
        this.author = author;
        this.authorEmail = userEmail;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
