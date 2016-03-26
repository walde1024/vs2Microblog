package com.vs2.microblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author cv
 *
 */
@Controller
public class TimelineController {

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String show(
			@RequestParam(value = "name", defaultValue = "World", required = false) String name, Model model) {
		model.addAttribute("name", name);
		return "timeline";
	}
}
