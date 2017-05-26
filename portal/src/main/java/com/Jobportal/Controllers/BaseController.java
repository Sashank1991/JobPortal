package com.Jobportal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Jobportal.Service.CompanyService;
import com.Jobportal.Service.JobSeekerProfileServices;

@Controller
@SessionAttributes({ "currentCompany", "currentUserProfile", "currentResume" })
public class BaseController {

	@Autowired
	CompanyService _companyService;

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@RequestMapping("/")
	public String singinReg(Model model) {
		model.addAttribute("currentCompany","");
		// JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(3);
		model.addAttribute("currentUserProfile", "");
		model.addAttribute("currentResume", "");

		return "SigninRegistrationPage";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("currentUserProfile", "");
		model.addAttribute("currentResume", "");

		return "SigninRegistrationPage";
	}

}
