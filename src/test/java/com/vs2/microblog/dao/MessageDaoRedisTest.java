package com.vs2.microblog.dao;

import com.vs2.microblog.Application;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Walde on 02.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class MessageDaoRedisTest {

    private static final String MESSAGE_KEY_PREFIX = "message:";
    private static final String USERS_MESSAGES_SET_PREFIX = "usermessages:";
    private static final String MESSAGE_SEQUENCE = "messagekey";
    private static final String GLOBAL_TIMELINE_KEY = "globaltimeline";
    private static final String PERSONAL_TIMELINE_PREFIX = "personaltimeline:";

    private final String myEmail = "barack.obama@test.com";
    private final String myFollower1 = "myFollower1@test.com";
    private final String myFollower2 = "myFollower2@test.com";
    private final String iFollow1 = "ifollow1@test.com";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RedisTemplate<String, String> template;

    public Message msg;

    @Before
    public void setUp() throws Exception {
        userDao.storeUser("barack", "obama", myEmail, "123");
        userDao.storeUser("test1", "test1", myFollower1, "123");
        userDao.storeUser("test2", "test2", myFollower2, "123");

        userDao.addIFollowUser(myFollower1, myEmail);
        userDao.addIFollowUser(myFollower2, myEmail);
        userDao.addIFollowUser(myEmail, iFollow1);
    }

    @After
    public void tearDown() throws Exception {
        template.getConnectionFactory().getConnection().flushAll();
    }

    @Test
    public void testStoreMessage() throws Exception {
        msg = messageDao.storeMessage(myEmail, "Hallo");
        String jsonMsg = template.opsForValue().get(MESSAGE_KEY_PREFIX + String.valueOf(msg.getMessageId()));
        assertNotNull(jsonMsg);

        //Persisted message
        Message persistedMsg  = Message.fromJson(jsonMsg);
        assertTrue(persistedMsg.getBody().equals("Hallo"));
        assertTrue(persistedMsg.getUserEmail().equals(myEmail));

        //Message reference in users messages list
        Set<String> messageIdSet = template.opsForZSet().reverseRange(USERS_MESSAGES_SET_PREFIX + myEmail, 0, 0);
        messageIdSet.iterator().forEachRemaining(v -> {
            assertEquals(persistedMsg.getMessageId(), Long.parseLong(v));
        });

        //Message reference in global timeline
        messageIdSet = template.opsForZSet().reverseRange(GLOBAL_TIMELINE_KEY, 0, 0);
        messageIdSet.iterator().forEachRemaining(v -> {
            assertEquals(persistedMsg.getMessageId(), Long.parseLong(v));
        });

        //Message reference in personal timeline
        messageIdSet = template.opsForZSet().reverseRange(PERSONAL_TIMELINE_PREFIX + myEmail, 0, 0);
        messageIdSet.iterator().forEachRemaining(v -> {
            assertEquals(persistedMsg.getMessageId(), Long.parseLong(v));
        });

        //Message reference in personal timeline of followers
        messageIdSet = template.opsForZSet().reverseRange(PERSONAL_TIMELINE_PREFIX + myFollower1, 0, 0);
        messageIdSet.iterator().forEachRemaining(v -> {
            assertEquals(persistedMsg.getMessageId(), Long.parseLong(v));
        });

        //Message reference in my personal timeline of a person i follow
        Message msgIFollow = messageDao.storeMessage(iFollow1, "test");
        messageIdSet = template.opsForZSet().reverseRange(PERSONAL_TIMELINE_PREFIX + myEmail, 0, 0);
        messageIdSet.iterator().forEachRemaining(v -> {
            assertEquals(msgIFollow.getMessageId(), Long.parseLong(v));
        });
    }

    @Test
    public void testGetGlobalTimelineMessages() throws Exception {
        messageDao.storeMessage(myEmail, "hallo123");
        String messagesJson = messageDao.getGlobalTimelineMessages(0, 0);

        assertTrue(messagesJson.contains("hallo123"));
    }

    @Test
    public void testGetPersonalTimelineMessages() throws Exception {
        messageDao.storeMessage(myEmail, "hallo123");
        String messagesJson = messageDao.getPersonalTimelineMessages(0, 0, myEmail);

        assertTrue(messagesJson.contains("hallo123"));
    }
}



















