package com.vs2.microblog.view.provider;

import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import com.vs2.microblog.view.UserProfileView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * Created by Walde on 06.04.16.
 */
public class UserProfileViewProvider {

    @Autowired
    private UserDao userDao;

    public UserProfileView getUserProfileView(String userEmail) {
        User user = userDao.getUserByEmail(userEmail);

        if (user == null) {
            return null;
        }

        long usersFollowingUserCount = userDao.getUsersFollowingUserCount(userEmail);
        long usersUserFollowsCount = userDao.getUsersUserFollowsCount(userEmail);

        return new UserProfileView(
                user.getFirstname(),
                user.getLastname(),
                String.valueOf(usersUserFollowsCount),
                String.valueOf(usersFollowingUserCount),
                userEmail);
    }
}
