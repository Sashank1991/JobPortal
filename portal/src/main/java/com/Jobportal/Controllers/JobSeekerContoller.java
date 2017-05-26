package com.Jobportal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Jobportal.Service.JobSeekerProfileServices;

@Controller
@SessionAttributes({ "currentUserProfile", "currentResume" })
public class JobSeekerContoller {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@RequestMapping("/JobSeekerHome")
	public String JobSeekerHome(Model model) {
		return "JobSeekerHome";
	}

	@RequestMapping("/popup")
	public String index() {
		return "popup";
	}
}
