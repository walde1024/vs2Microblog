package com.vs2.microblog.dao;

import com.vs2.microblog.Application;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Walde on 02.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserDaoRedisTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> template;

    @Before
    public void before() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        template.getConnectionFactory().getConnection().flushAll();
    }

    @Test
    public void testStoreUser() throws Exception {
        userDao.storeUser("Barack", "Obama", "barack.obama@test.com", "barack123");

        String userJson = template.opsForValue().get("user:barack.obama@test.com");
        assertNotNull(userJson);

        User user = User.fromJson(userJson);
        assertTrue(user.getFirstname().equals("Barack"));
        assertTrue(user.getLastname().equals("Obama"));
        assertTrue(user.getEmail().equals("barack.obama@test.com"));
        assertTrue(user.getPassword().equals("barack123"));

        template.delete("user:barack.obama@test.com");
        userJson = template.opsForValue().get("user:barack.obama@test.com");
        assertNull(userJson);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        userDao.storeUser("Barack", "Obama", "barack.obama@test.com", "barack123");
        User user = userDao.getUserByEmail("barack.obama@test.com");

        assertNotNull(user);
        assertTrue(user.getFirstname().equals("Barack"));
        assertTrue(user.getLastname().equals("Obama"));
        assertTrue(user.getEmail().equals("barack.obama@test.com"));
        assertTrue(user.getPassword().equals("barack123"));

        template.delete("user:barack.obama@test.com");

        user = userDao.getUserByEmail("barack.obama@test.com");
        assertNull(user);
    }

    @Test
    public void testGetAndAddUsersFollowingMe() throws Exception {
        String myEmail = "barack.obama@test.com";
        String follower1Email = "barack.obama.follower1@test.com";
        String follower2Email = "barack.obama.follower2@test.com";
        String follower3Email = "barack.obama.follower3@test.com";

        userDao.addIFollowUser(follower1Email, myEmail);
        userDao.addIFollowUser(follower2Email, myEmail);
        userDao.addIFollowUser(follower3Email, myEmail);

        List<User> myFollowers = userDao.getUsersFollowingMe(myEmail);
        assertTrue(myFollowers.size() == 3);

        template.delete("followme:barack.obama@test.com");
        myFollowers = userDao.getUsersFollowingMe(myEmail);
        assertTrue(myFollowers.size() == 0);
    }

    @Test
    public void testGetAndAddUsersIFollow() throws Exception {
        String myEmail = "barack.obama@test.com";
        String iFollow1Email = "barack.obama.follower1@test.com";
        String iFollow2Email = "barack.obama.follower2@test.com";
        String iFollow3Email = "barack.obama.follower3@test.com";

        userDao.addIFollowUser(myEmail, iFollow1Email);
        userDao.addIFollowUser(myEmail, iFollow2Email);
        userDao.addIFollowUser(myEmail, iFollow3Email);

        List<User> myFollowers = userDao.getUsersIFollow(myEmail);
        assertTrue(myFollowers.size() == 3);

        template.delete("followme:barack.obama@test.com");
        myFollowers = userDao.getUsersFollowingMe(myEmail);
        assertTrue(myFollowers.size() == 0);
    }

    @Test
    public void testGetUsersFollowingUserCount() throws Exception {
        String myEmail = "barack.obama@test.com";
        String follower1Email = "barack.obama.follower1@test.com";
        String follower2Email = "barack.obama.follower2@test.com";

        userDao.addIFollowUser(follower1Email, myEmail);
        userDao.addIFollowUser(follower2Email, myEmail);

        assertEquals(2, userDao.getUsersFollowingUserCount(myEmail));
    }

    @Test
    public void testGetUsersUserFollowsCount() throws Exception {
        String myEmail = "barack.obama@test.com";
        String iFollow1Email = "barack.obama.follower1@test.com";
        String iFollow2Email = "barack.obama.follower2@test.com";
        String iFollow3Email = "barack.obama.follower3@test.com";

        userDao.addIFollowUser(myEmail, iFollow1Email);
        userDao.addIFollowUser(myEmail, iFollow2Email);
        userDao.addIFollowUser(myEmail, iFollow3Email);

        assertEquals(3, userDao.getUsersUserFollowsCount(myEmail));
    }
}