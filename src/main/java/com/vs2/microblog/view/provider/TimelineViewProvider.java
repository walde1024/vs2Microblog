package com.vs2.microblog.view.provider;

import com.google.gson.Gson;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.Message;
import com.vs2.microblog.entity.User;
import com.vs2.microblog.view.TimelineView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walde on 04.04.16.
 */
public class TimelineViewProvider {

    @Autowired
    MessageDao messageDao;

    @Autowired
    UserDao userDao;

    public String getPersonalTimelineViews(int fromMessage, int toMessage, String email) {
        List<Message> messages = messageDao.getPersonalTimelineMessages(fromMessage, toMessage, email);
        List<TimelineView> timelineViews = new ArrayList<>();
        Gson gson = new Gson();

        for (Message message: messages) {
            timelineViews.add(new TimelineView(message.getBody(), message.getDateTime(), getAuthorOfMessage(message), message.getUserEmail()));
        }

        return gson.toJson(timelineViews);
    }

    public String getGlobalTimelineViews(int fromMessage, int toMessage) {
        List<Message> messages = messageDao.getGlobalTimelineMessages(fromMessage, toMessage);
        List<TimelineView> timelineViews = new ArrayList<>();
        Gson gson = new Gson();

        for (Message message: messages) {
            timelineViews.add(new TimelineView(message.getBody(), message.getDateTime(), getAuthorOfMessage(message), message.getUserEmail()));
        }

        return gson.toJson(timelineViews);
    }

    private String getAuthorOfMessage(Message message) {
        User user = userDao.getUserByEmail(message.getUserEmail());
        return user.getFirstname() + " " + user.getLastname();
    }
}
