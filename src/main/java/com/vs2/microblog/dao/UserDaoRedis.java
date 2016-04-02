package com.vs2.microblog.dao;

import com.google.gson.Gson;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by Walde on 27.03.16.
 */
public class UserDaoRedis implements UserDao {

    private static final String USERS_NAMESPACE = "user:";
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

        template.opsForValue().set(USERS_NAMESPACE + user.getEmail(), jsonUser);

        return user;
    }

    /**
     * Returns an user by his email.
     * @param email
     * @return An user or null.
     */
    @Override
    public User getUserByEmail(final String email) {
        String jsonUser = template.opsForValue().get(USERS_NAMESPACE + email);

        if (jsonUser == null) {
            return null;
        }

        return User.fromJson(jsonUser);
    }

    @Override
    public List<User> getUsersFollowingMe(User me) {
        return getUsersFollowingMe(me.getEmail());
    }

    @Override
    public List<User> getUsersFollowingMe(String myEmail) {
        Set<String> usersFollowingMeJson = template.opsForSet().members(FOLLOW_ME_PREFIX + myEmail);
        List<User> usersFollowingMe = new ArrayList<>();

        for(String jsonUser: usersFollowingMeJson) {
            usersFollowingMe.add(User.fromJson(jsonUser));
        }

        return usersFollowingMe;
    }
}
