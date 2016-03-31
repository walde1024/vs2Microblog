package com.vs2.microblog.dao;

import com.google.gson.Gson;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.entity.Message;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by Walde on 29.03.16.
 */
public class MessageDaoRedis implements MessageDao {

    private static final String MESSAGE_KEY_PREFIX = "message:";
    private static final String MESSAGE_ID = "messagekey";

    @Autowired
    RedisTemplate<String, String> template;

    @Override
    public Message storeMessage(User user, String text) {
        Gson gson = new Gson();

        Message message = new Message(user.getEmail(), text, System.currentTimeMillis());
        String jsonMessage = gson.toJson(message, Message.class);

        long newId = template.opsForValue().increment(MESSAGE_ID, 1);
        template.opsForValue().set(MESSAGE_KEY_PREFIX + String.valueOf(newId), jsonMessage);

        return message;
    }
}
