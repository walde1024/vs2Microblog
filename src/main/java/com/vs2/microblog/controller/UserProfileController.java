package com.vs2.microblog.controller;

import com.google.gson.Gson;
import com.vs2.microblog.controller.utils.FollowButtonState;
import com.vs2.microblog.controller.utils.FollowUtils;
import com.vs2.microblog.controller.utils.UserUtils;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import com.vs2.microblog.view.UserProfileView;
import com.vs2.microblog.view.provider.UserProfileViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class UserProfileController {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FollowUtils followUtils;

    @Autowired
    private UserProfileViewProvider userProfileViewProvider;

    @RequestMapping(path = "/userprofile", method = RequestMethod.GET)
    public String userprofile(@RequestParam(name = "userEmail", required = false) String userEmail, HttpSession session, Model model) {
        User user = userUtils.getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute("userProfile", userDao.getUserByEmail(userEmail));

        if (userEmail == null || userEmail.equals(user.getEmail())) {
            model.addAttribute("userProfile", userProfileViewProvider.getUserProfileView(user.getEmail()));
            model.addAttribute("followButtonState", FollowButtonState.DEACTIVATED.toString());
        }
        else {
            UserProfileView userProfileView = userProfileViewProvider.getUserProfileView(userEmail);
            if (userProfileView == null) {
                return "redirect:/error";
            }
            else {
                model.addAttribute("userProfile", userProfileView);
                if (userDao.doIFollowUser(user.getEmail(), userProfileView.getEmail())) {
                    model.addAttribute("followButtonState", FollowButtonState.UNFOLLOW.toString());
                }
                else {
                    model.addAttribute("followButtonState", FollowButtonState.FOLLOW.toString());
                }
            }
        }

        return "userprofile";
    }

    /**
     * Lets current user follow or unfollow a user.
     * @param userEmail
     * @param follow True to follow and false to unfollow.
     * @param session
     * @return
     */
    @RequestMapping(path = "/userprofile/follow", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
    public @ResponseBody String followOrUnfollowUser(@RequestParam(name = "userEmail") String userEmail,
                       @RequestParam(name = "follow") boolean follow,
                       HttpSession session) {

        User user = userUtils.getUserFromSession(session);

        if (follow) {
            followUtils.addIFollowUser(user, userEmail);
            return new Gson().toJson("{'result': 'OK'}");
        }
        else {
            followUtils.removeIFollowUser(user, userEmail);
            return new Gson().toJson("{'result': 'OK'}");
        }
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public String me(HttpSession session, Model model) {
        User user = userUtils.getUserFromSession(session);

        return userprofile(user.getEmail(), session, model);
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
