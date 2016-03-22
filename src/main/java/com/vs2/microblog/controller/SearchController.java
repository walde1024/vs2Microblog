package com.vs2.microblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class SearchController {

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        return "search";
    }
}
