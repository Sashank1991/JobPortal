package com.Jobportal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.JobSeekerProfileServices;

@Controller
@SessionAttributes({ "currentUserProfile", "currentResume" })
public class JobSeekerContoller {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@RequestMapping("/")
	public String JobSeekerHome(Model model) {
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(3);
		model.addAttribute("currentUserProfile", currentJobSeeker);
		model.addAttribute("currentResume", "");
		return "JobSeekerHome";
	}

	@RequestMapping("/popup")
	public String index() {
		return "popup";
	}
}
