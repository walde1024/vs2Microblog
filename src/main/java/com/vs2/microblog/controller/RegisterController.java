package com.vs2.microblog.controller;

import com.google.gson.Gson;
import com.vs2.microblog.controller.form.RegisterForm;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class RegisterController {

    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());

        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String postRegister(HttpSession session, @ModelAttribute RegisterForm registerForm, Model model) {
        //TODO: Create validate method in registerForm
        //TODO: Check if user already exists
        userDao.storeUser(
                registerForm.getFirstname(),
                registerForm.getLastname(),
                registerForm.getEmail(),
                registerForm.getPassword()
        );

        session.setAttribute("email", registerForm.getEmail());

        return "redirect:/";
    }
}
