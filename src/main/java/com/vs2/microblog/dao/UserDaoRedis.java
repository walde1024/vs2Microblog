package com.vs2.microblog.dao;

import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Walde on 27.03.16.
 */
public class UserDaoRedis implements UserDao {

    private static final String USER_PREFIX = "user:";
    private static final String FOLLOW_ME_PREFIX = "followme:";
    private static final String I_FOLLOW_PREFIX = "ifollow:";

    @Autowired
    private RedisTemplate<String, String> template;

    /**
     * Stores a user on the database.
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @return The stored user.
     */
    @Override
    public User storeUser(final String firstname, final String lastname, final String email, final String password) {
        User user = new User(
                firstname,
                lastname,
                email,
                password
        );

        String jsonUser = user.toJson();

        template.opsForValue().set(USER_PREFIX + user.getEmail(), jsonUser);

        return user;
    }

    /**
     * Returns an user by his email.
     * @param email
     * @return An user or null.
     */
    @Override
    public User getUserByEmail(final String email) {
        String jsonUser = template.opsForValue().get(USER_PREFIX + email);

        if (jsonUser == null) {
            return null;
        }

        return User.fromJson(jsonUser);
    }

    @Override
    public List<User> getUsersFollowingMe(String myEmail) {
        List<String> usersFollowingMeEmail = template.opsForList().range(FOLLOW_ME_PREFIX + myEmail, 0, -1);
        List<User> usersFollowingMe = new ArrayList<>();

        for(String jsonUserEmail: usersFollowingMeEmail) {
            usersFollowingMe.add(this.getUserByEmail(jsonUserEmail));
        }

        return usersFollowingMe;
    }

    @Override
    public List<User> getUsersIFollow(String myEmail) {
        List<String> usersIFollowEmail = template.opsForList().range(I_FOLLOW_PREFIX + myEmail, 0, -1);
        List<User> usersIFollow = new ArrayList<>();

        for(String jsonUserEmail: usersIFollowEmail) {
            usersIFollow.add(this.getUserByEmail(jsonUserEmail));
        }

        return usersIFollow;
    }

    @Override
    public long getUsersFollowingUserCount(String userEmail) {
        return template.opsForList().size(FOLLOW_ME_PREFIX + userEmail);
    }

    @Override
    public long getUsersUserFollowsCount(String userEmail) {
        return template.opsForList().size(I_FOLLOW_PREFIX + userEmail);
    }

    @Override
    public void addIFollowUser(String myEmail, String iFollowUserEmail) {
        template.opsForList().leftPush(I_FOLLOW_PREFIX + myEmail, iFollowUserEmail);
        template.opsForList().leftPush(FOLLOW_ME_PREFIX + iFollowUserEmail, myEmail);
    }

    @Override
    public void deleteUser(String email) {
        template.delete(USER_PREFIX + email);
    }
}
