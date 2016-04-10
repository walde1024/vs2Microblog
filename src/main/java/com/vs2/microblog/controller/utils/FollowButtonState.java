package com.vs2.microblog.controller.utils;

/**
 * Created by Walde on 09.04.16.
 */
public enum FollowButtonState {

    DEACTIVATED("deactivated"),
    FOLLOW("follow"),
    UNFOLLOW("unfollow");

    private final String text;

    FollowButtonState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
