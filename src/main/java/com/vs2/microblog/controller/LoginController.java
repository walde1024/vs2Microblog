package com.vs2.microblog.controller;

import com.vs2.microblog.controller.form.LoginForm;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Walde on 19.03.16.
 */
@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String postLogin(@ModelAttribute LoginForm loginForm, Model model, HttpSession session) {
        User user = userDao.getUserByEmail(loginForm.getEmail());

        if (user == null || !user.getPassword().equals(loginForm.getPassword())) {
            model.addAttribute("error", "Benutzername oder Passwort ist falsch.");
            return "login";
        }

        session.setAttribute("email", user.getEmail());

        return "redirect:/";
    }

}
