package com.vs2.microblog.controller.utils;

import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Walde on 28.04.16.
 */
public class FollowUtils {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    /**
     * Lets the current user follow another user and updates the personal timeline.
     * @param me
     * @param otherUserEmail
     */
    public void addIFollowUser(User me, String otherUserEmail) {
        userDao.addIFollowUser(me.getEmail(), otherUserEmail);
        messageDao.addMessagesFromUserToMyPersonalTimeline(me, otherUserEmail);
    }

    /**
     * Lets the current user unfollow another user and updated the personal timeline.
     * @param me
     * @param otherUserEmail
     */
    public void removeIFollowUser(User me, String otherUserEmail) {
        userDao.removeIFollowUser(me.getEmail(), otherUserEmail);
        messageDao.removeMessagesOfUserFromMyPersonalTimeline(me, otherUserEmail);
    }
}
