package com.vs2.microblog.controller;

import com.vs2.microblog.controller.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 
 * @author cv
 *
 */
@Controller
public class LogoutController {

	@Autowired
	SessionManager sessionManager;

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String show(HttpSession session, Model model) {
		sessionManager.logout(session);

		return "login";
	}
}