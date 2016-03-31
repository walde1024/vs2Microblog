package com.vs2.microblog.dao.api;

import com.vs2.microblog.entity.Message;
import com.vs2.microblog.entity.User;

/**
 * Created by Walde on 29.03.16.
 */
public interface MessageDao {
    Message storeMessage(User user, String text);
}
