package com.vs2.microblog.dao.api;

import com.vs2.microblog.entity.User;

import java.util.List;

/**
 * Created by Walde on 27.03.16.
 */
public interface UserDao {
    User storeUser(String firstname, String lastname, String email, String password);
    User getUserByEmail(String email);
    List<User> getUsersFollowingMe(User me);
    List<User> getUsersFollowingMe(String myEmail);
}
