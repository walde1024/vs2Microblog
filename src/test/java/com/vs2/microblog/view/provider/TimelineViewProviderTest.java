package com.vs2.microblog.view.provider;

import com.vs2.microblog.Application;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.Message;
import com.vs2.microblog.view.TimelineView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Walde on 04.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class TimelineViewProviderTest {

    private final String myEmail = "barack.obama@test.com";
    private final String otherEmail = "hallooweijl@test.com";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private TimelineViewProvider timelineViewProvider;

    public Message msg;

    @Before
    public void setUp() throws Exception {
        userDao.storeUser("barack", "obama", myEmail, "123");
        userDao.storeUser("muster", "mustermann", otherEmail, "4123");
    }

    @After
    public void tearDown() throws Exception {
        template.getConnectionFactory().getConnection().flushAll();
    }

    @Test
    public void testGetPersonalTimelineViews() throws Exception {
        messageDao.storeMessage(myEmail, "Hallo123");
        messageDao.storeMessage("hallooweijl@test.com", "test123");

        String views = timelineViewProvider.getPersonalTimelineViews(0, 0, myEmail);
        assertTrue(views.contains("Hallo123"));
        assertTrue(views.contains("barack"));
        assertTrue(views.contains("obama"));
        assertFalse(views.contains("test123"));
    }

    @Test
    public void testGetGlobalTimelineViews() throws Exception {
        messageDao.storeMessage(myEmail, "Hallo123");
        messageDao.storeMessage(otherEmail, "test123");

        String views = timelineViewProvider.getGlobalTimelineViews(0, -1);
        assertTrue(views.contains("Hallo123"));
        assertTrue(views.contains("barack"));
        assertTrue(views.contains("obama"));
        assertTrue(views.contains("test123"));
    }
}