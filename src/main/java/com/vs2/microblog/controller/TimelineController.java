package com.vs2.microblog.controller;

import com.vs2.microblog.controller.form.PostForm;
import com.vs2.microblog.controller.form.RegisterForm;
import com.vs2.microblog.controller.session.SessionManager;
import com.vs2.microblog.controller.utils.UserUtils;
import com.vs2.microblog.dao.api.MessageDao;
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
 * 
 * @author cv
 *
 */
@Controller
public class TimelineController {

	public static final String GLOBAL_TIMELINE = "global";
	public static final String PERSONAL_TIMELINE = "personal";
	public static final String TIMELINE_KEY = "timeline";

	@Autowired
	UserUtils userUtils;

	@Autowired
	MessageDao messageDao;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String show(@RequestParam(name = "timeline", defaultValue = GLOBAL_TIMELINE) String timeline,HttpSession session, Model model) {
		model.addAttribute("user", userUtils.getUserFromSession(session));
		model.addAttribute("timeline", timeline);
		model.addAttribute("postForm", new PostForm());

		return "timeline";
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public String createMessage(@RequestParam(name = "timeline", defaultValue = GLOBAL_TIMELINE) String timeline,
								@ModelAttribute PostForm postForm, HttpSession session, Model model) {

		//TODO: clear textarea after submit is pressed
		User user = userUtils.getUserFromSession(session);
		model.addAttribute(User.MODEL_KEY, user);
		model.addAttribute(TIMELINE_KEY, timeline);

		messageDao.storeMessage(user, postForm.getBody());

		return "timeline";
	}
}
