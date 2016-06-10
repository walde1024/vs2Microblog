package com.vs2.microblog.controller;

import com.vs2.microblog.controller.form.PostForm;
import com.vs2.microblog.controller.form.RegisterForm;
import com.vs2.microblog.controller.session.SessionManager;
import com.vs2.microblog.controller.utils.UserUtils;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import com.vs2.microblog.view.provider.TimelineViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	TimelineViewProvider timelineViewProvider;

	@Autowired
	StringRedisTemplate redisStringtemplate;

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

		User user = userUtils.getUserFromSession(session);
		model.addAttribute(User.MODEL_KEY, user);
		model.addAttribute(TIMELINE_KEY, timeline);
		model.addAttribute("postForm", new PostForm());

		messageDao.storeMessage(user.getEmail(), postForm.getBody());

		redisStringtemplate.convertAndSend("timeline", "Hello from Redis!");

		return "redirect:/?timeline=" + timeline;
	}

	@RequestMapping(path = "/messages", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	public @ResponseBody String getMessages(@RequestParam(name = "timeline") String timeline,
						  	@RequestParam(name = "fromMessage") String fromMessage,
						  	@RequestParam(name = "toMessage") String toMessage,
							@RequestParam(name = "userEmail", required = false) String userEmail,
						  	HttpSession session) {

		if (timeline.equals(GLOBAL_TIMELINE)) {
			return timelineViewProvider.getGlobalTimelineViews(Integer.parseInt(fromMessage), Integer.parseInt(toMessage));
		}
		else {
			String email = (userEmail == null) ? userUtils.getUserFromSession(session).getEmail() : userEmail;

			return timelineViewProvider.getPersonalTimelineViews(Integer.parseInt(fromMessage), Integer.parseInt(toMessage), email);
		}
	}
}
