package com.vs2.microblog.controller.utils;

import com.vs2.microblog.controller.session.SessionManager;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by Walde on 29.03.16.
 */
public class UserUtils {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private UserDao userDao;

    /**
     * Returns current authenticated user.
     * @param session
     * @return Current authenticated user or null.
     */
    public User getUserFromSession(HttpSession session) {
        String email = sessionManager.getEmailFromSession(session);
        return userDao.getUserByEmail(email);
    }
}
