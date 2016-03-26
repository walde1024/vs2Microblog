package com.vs2.microblog.entity;

/**
 * Created by Walde on 26.03.16.
 */
public class UserFollowsUser {

    private long userId;
    private long followsUserId;

    public UserFollowsUser(long userId, long followsUserId) {
        this.userId = userId;
        this.followsUserId = followsUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFollowsUserId() {
        return followsUserId;
    }

    public void setFollowsUserId(long followsUserId) {
        this.followsUserId = followsUserId;
    }
}
