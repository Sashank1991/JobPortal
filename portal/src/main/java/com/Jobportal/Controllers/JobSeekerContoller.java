package com.Jobportal.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JobSeekerContoller {
	@RequestMapping("/")
	public String JobSeekerHome() {
		return "JobSeekerHome";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
