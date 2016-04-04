package com.vs2.microblog.dao.api;

import com.vs2.microblog.entity.Message;
import com.vs2.microblog.entity.User;

import java.util.List;

/**
 * Created by Walde on 29.03.16.
 */
public interface MessageDao {
    Message storeMessage(String authorEmail, String message);

    List<Message> getGlobalTimelineMessages(int fromMessage, int toMessage);
    List<Message> getPersonalTimelineMessages(int fromMessage, int toMessage, String email);
}
