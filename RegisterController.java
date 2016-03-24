package com.vs2.microblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class RegisterController {

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String greeting(Model model) {
        return "register";
    }
}
