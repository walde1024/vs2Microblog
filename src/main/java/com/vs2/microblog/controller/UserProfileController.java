package com.vs2.microblog.controller;

import com.vs2.microblog.controller.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class UserProfileController {

    @Autowired
    private UserUtils userUtils;

    @RequestMapping(path = "/userprofile", method = RequestMethod.GET)
    public String userprofile(HttpSession session, Model model) {
        model.addAttribute("user", userUtils.getUserFromSession(session));

        return "userprofile";
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public String me(HttpSession session, Model model) {
        model.addAttribute("user", userUtils.getUserFromSession(session));

        return "userprofile";
    }

    @RequestMapping(path = "/userprofile/ifollow", method = RequestMethod.GET)
    public String iFollow(HttpSession session, Model model) {
        model.addAttribute("user", userUtils.getUserFromSession(session));

        return "ifollow";
    }

    @RequestMapping(path = "/userprofile/followme", method = RequestMethod.GET)
    public String followMe(HttpSession session, Model model) {
        model.addAttribute("user", userUtils.getUserFromSession(session));

        return "followme";
    }
}
