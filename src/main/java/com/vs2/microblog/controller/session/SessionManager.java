package com.vs2.microblog.controller.session;

import javax.servlet.http.HttpSession;

/**
 * Created by Walde on 29.03.16.
 */
public class SessionManager {

    private final String EMAIL_KEY = "email";

    /**
     * Returns the Email from the session.
     * @param session
     * @return Email or null.
     */
    public String getEmailFromSession(HttpSession session) {
        return session.getAttribute(EMAIL_KEY).toString();
    }

    /**
     * Logs out the user.
     */
    public void logout(HttpSession session) {
        session.removeAttribute(EMAIL_KEY);
    }
}
