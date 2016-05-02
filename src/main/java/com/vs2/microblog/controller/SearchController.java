package com.vs2.microblog.controller;

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

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class SearchController {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileViewProvider userProfileViewProvider;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(HttpSession session, Model model, @RequestParam(name = "searchString") String searchString) {
        User me = userUtils.getUserFromSession(session);
        model.addAttribute("user", me);

        List<User> users = userDao.searchUsers(searchString);

        List<UserProfileView> userProfileViews = users.stream().filter(user -> {
            return (user.equals(me)) ? false : true;
        })
        .map(user -> {
            return userProfileViewProvider.getUserProfileView(user.getEmail());
        })
        .collect(Collectors.toList());

        //TODO: Update html with link to user and follow button
        model.addAttribute("userProfiles", userProfileViews);

        return "search";
    }
}
