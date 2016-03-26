package com.vs2.microblog.entity;

import java.io.Serializable;

/**
 * Created by Walde on 26.03.16.
 */
public class Session implements Serializable {

    long userId;
    String session;

    public Session(long userId, String session) {
        this.userId = userId;
        this.session = session;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
