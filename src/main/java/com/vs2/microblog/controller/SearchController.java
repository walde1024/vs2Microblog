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
public class SearchController {

    @Autowired
    private UserUtils userUtils;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(HttpSession session, Model model) {
        model.addAttribute("user", userUtils.getUserFromSession(session));

        return "search";
    }
}
