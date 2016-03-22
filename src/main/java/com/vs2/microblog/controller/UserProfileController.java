package com.vs2.microblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class UserProfileController {

    @RequestMapping(path = "/userprofile", method = RequestMethod.GET)
    public String userprofile(Model model) {
        return "userprofile";
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public String me(Model model) {
        return "userprofile";
    }

    @RequestMapping(path = "/userprofile/ifollow", method = RequestMethod.GET)
    public String iFollow(Model model) {
        return "ifollow";
    }

    @RequestMapping(path = "/userprofile/followme", method = RequestMethod.GET)
    public String followMe(Model model) {
        return "followme";
    }
}
