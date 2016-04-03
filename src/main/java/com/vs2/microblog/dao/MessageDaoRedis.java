package com.vs2.microblog.dao;

import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.Message;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by Walde on 29.03.16.
 */
public class MessageDaoRedis implements MessageDao {

    private static final String MESSAGE_KEY_PREFIX = "message:";
    private static final String USERS_MESSAGES_SET_PREFIX = "usermessages:";
    private static final String MESSAGE_SEQUENCE = "messagekey";
    private static final String GLOBAL_TIMELINE_KEY = "globaltimeline";
    private static final String PERSONAL_TIMELINE_PREFIX = "personaltimeline:";

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private UserDao userDao;

    /**
     * Stores the message on the db.
     * @param authorEmail
     * @param text
     * @return The stored message.
     */
    @Override
    public Message storeMessage(String authorEmail, String text) {
        Message message = new Message(generateMessageKey(), authorEmail, text, System.currentTimeMillis());

        saveMessageToRedis(message);
        addMessageReferenceToUsersMessages(message);

        addMessageReferenceToGlobalTimeline(message);
        addMessageReferenceToPersonalTimeline(message);
        addMessageReferenceToPersonalTimelineOfFollowers(message);

        return message;
    }

    /**
     * Saves the message on redis.
     * @param message
     */
    private void saveMessageToRedis(Message message) {
        String jsonMessage = message.toJson();
        long messageId = message.getMessageId();

        template.opsForValue().set(MESSAGE_KEY_PREFIX + String.valueOf(messageId), jsonMessage);
    }

    /**
     * Adds a message to a users messages list.
     * @param message
     */
    private void addMessageReferenceToUsersMessages(Message message) {
        template.opsForZSet().add(USERS_MESSAGES_SET_PREFIX + message.getUserEmail(),
                String.valueOf(message.getMessageId()), message.getDateTime());
    }

    /**
     * Adds the message reference to the global timeline.
     * @param message
     */
    private void addMessageReferenceToGlobalTimeline(Message message) {
        template.opsForZSet().add(GLOBAL_TIMELINE_KEY, String.valueOf(message.getMessageId()), message.getDateTime());
    }

    /**
     * Adds a message reference to the personal timeline.
     * @param message
     */
    private void addMessageReferenceToPersonalTimeline(Message message) {
        template.opsForZSet().add(PERSONAL_TIMELINE_PREFIX + message.getUserEmail(), String.valueOf(message.getMessageId()), message.getDateTime());
    }

    /**
     * Adds message reference to timeline of followers.
     * @param message
     */
    private void addMessageReferenceToPersonalTimelineOfFollowers(Message message) {
        List<User> myFollowers = userDao.getUsersFollowingMe(message.getUserEmail());

        for(User follower: myFollowers) {
            template.opsForZSet().add(PERSONAL_TIMELINE_PREFIX + follower.getEmail(), String.valueOf(message.getMessageId()),  message.getDateTime());
        }
    }

    private long generateMessageKey() {
        return template.opsForValue().increment(MESSAGE_SEQUENCE, 1);
    }
}
