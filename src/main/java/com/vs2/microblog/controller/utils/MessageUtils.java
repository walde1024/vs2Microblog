package com.vs2.microblog.controller.utils;

import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Walde on 29.03.16.
 */
@Component
public class MessageUtils {

    /**
     * Creates a new message.
     * @param owner
     * @param text
     */
    public void createMessage(User owner, String text) {

    }
}
