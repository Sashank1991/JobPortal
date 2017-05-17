package com.Jobportal.Controllers;

import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.CompanyService;
import com.Jobportal.Service.JobSeekerProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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

}
