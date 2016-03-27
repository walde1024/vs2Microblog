package com.vs2.microblog.dao;

import com.google.gson.Gson;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Created by Walde on 27.03.16.
 */
public class UserDaoRedis implements UserDao {

    private static final String USERS_NAMESPACE = "user:";

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
        Gson gson = new Gson();
        User user = new User(
                firstname,
                lastname,
                email,
                password
        );

        String jsonUser = gson.toJson(user);

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
        Gson gson = new Gson();
        String jsonUser = template.opsForValue().get(USERS_NAMESPACE + email);

        if (jsonUser == null) {
            return null;
        }

        return gson.fromJson(jsonUser, User.class);
    }
}
