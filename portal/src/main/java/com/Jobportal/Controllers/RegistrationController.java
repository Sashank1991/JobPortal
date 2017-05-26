package com.Jobportal.Controllers;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.CompanyService;
import com.Jobportal.Service.EmailServiceImpl;
import com.Jobportal.Service.JobSeekerProfileServices;

@RestController
@SessionAttributes({ "currentCompany", "currentUserProfile", "currentResume" })
public class RegistrationController {

	@Autowired
	EmailServiceImpl _emailServiceImpl;
	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;
	@Autowired
	CompanyService _companyService;

	@RequestMapping(value = "/registerJobSeeker", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response RegisterJobSeeker(Model model, @RequestBody HashMap<String, String> _jobApplication) {

		JobSeeker _jobSeeker = new JobSeeker();
		Random rnd = new Random();
		int verifyCode = 100000 + rnd.nextInt(900000);
		_jobSeeker.setEmail(_jobApplication.get("email"));
		_jobSeeker.setFirstName(_jobApplication.get("firstName"));
		_jobSeeker.setLastName(_jobApplication.get("lastName"));
		_jobSeeker.setPassword(_jobApplication.get("password"));
		_jobSeeker.setVerificationCode(Integer.toString(verifyCode));
		_jobSeeker.setStatus(0);

		if (_jobSeekerProfileServices.getSeeker(_jobApplication.get("email")) == null
				&& _companyService.getCompany(_jobApplication.get("email")) == null) {

			_jobSeekerProfileServices.updateSeeker(_jobSeeker);
			_emailServiceImpl.sendSimpleMessage(_jobApplication.get("email"), "Verification code from job portal",
					"please verify your account with this code " + Integer.toString(verifyCode));
			return new Response(200, "success");
		}
		return new Response(404, "error");

	}

	@RequestMapping(value = "/retrieveJobSeeker", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response GetJobSeeker(Model model, @RequestBody HashMap<String, String> _retrieveJobSeeker,
			@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile) {

		JobSeeker _jobSeeker = _jobSeekerProfileServices.getSeeker(_retrieveJobSeeker.get("email"));

		if (_jobSeeker != null) {

			if (_jobSeeker.getPassword().equals(_retrieveJobSeeker.get("password"))) {

				if (_jobSeeker.getStatus() != 0
						|| _jobSeeker.getVerificationCode().equals(_retrieveJobSeeker.get("verificationCode"))) {
					if (_jobSeeker.getStatus() == 0) {
						_jobSeeker.setStatus(1);
						_jobSeekerProfileServices.updateSeeker(_jobSeeker);
					}
					JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(_jobSeeker.getUserId());
					model.addAttribute("currentUserProfile", currentJobSeeker);
					return new Response(200, "success");
				} else {

					return new Response(404, "verify");
				}
			}
		}
		return new Response(404, "error");
	}

	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response RegisterCompany(Model model, @RequestBody HashMap<String, String> _company) {

		Company currentCompany = new Company();
		Random rnd = new Random();
		int verifyCode = 100000 + rnd.nextInt(900000);
		currentCompany.setEmail(_company.get("newEmail"));
		currentCompany.setAddressHQ(_company.get("addressHQ"));
		currentCompany.setLogoImageURL(_company.get("logoImageURL"));
		currentCompany.setName(_company.get("name"));
		currentCompany.setWebsite(_company.get("website"));
		currentCompany.setPassword(_company.get("newPassword"));
		currentCompany.setVerificationCode(Integer.toString(verifyCode));
		currentCompany.setStatus(0);

		if (_jobSeekerProfileServices.getSeeker(_company.get("newEmail")) == null
				&& _companyService.getCompany(_company.get("newEmail")) == null) {

			_companyService.createOrUpdate(currentCompany);
			_emailServiceImpl.sendSimpleMessage(_company.get("newEmail"), "Verification code from job portal",
					"please verify your account with this code " + Integer.toString(verifyCode));
			return new Response(200, "success");
		}
		return new Response(404, "error");
	}

	@RequestMapping(value = "/retrieveCompany", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Response GetCompany(Model model, @RequestBody HashMap<String, String> _retrieveCompany,
			@ModelAttribute("currentCompany") JobSeeker _currentCompany) {

		Company _company = _companyService.getCompany(_retrieveCompany.get("email"));

		if (_company != null) {

			if (_company.getPassword().equals(_retrieveCompany.get("password"))) {
				if (_company.getStatus() != 0
						|| _company.getVerificationCode().equals(_retrieveCompany.get("verificationCode"))) {
					if (_company.getStatus() == 0) {
						_company.setStatus(1);
						_companyService.createOrUpdate(_company);
					}
					Company currentCompany = _companyService.getCompany(_company.getUserId());
					model.addAttribute("currentCompany", currentCompany);
					return new Response(200, "success");
				} else {

					return new Response(404, "verify");
				}

			}
		}
		return new Response(404, "error");
	}

}

class Response {

	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	int code;
	String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
