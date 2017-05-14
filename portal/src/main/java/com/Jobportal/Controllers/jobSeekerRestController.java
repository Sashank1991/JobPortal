package com.Jobportal.Controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.CompanyService;
import com.Jobportal.Service.JobSeekerApplicationServices;
import com.Jobportal.Service.JobSeekerProfileServices;

@RestController
@SessionAttributes("currentUserProfile")
public class jobSeekerRestController {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@Autowired
	JobSeekerApplicationServices _jobSeekerApplicationServices;

	@RequestMapping(value = "/getseeker", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public JobSeeker getSeeker(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile) {
		// if no session redirect him to the login
		return currentUserProfile;
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public int updateSeeker(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Map<String, String> profile) {
		currentUserProfile.setFirstName(profile.get("firstName"));
		currentUserProfile.setLastName(profile.get("lastName"));
		currentUserProfile.setWorkExperience(profile.get("workExperience"));
		currentUserProfile.setEducation(profile.get("education"));
		currentUserProfile.setSkills(profile.get("skills"));
		currentUserProfile.setPicKey(profile.get("picKey"));
		_jobSeekerProfileServices.updateSeeker(currentUserProfile);
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/updateFavfromFav", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int updateFavfromFav(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Set<JobPosition> _jobPositions) {
		List<JobPosition> list = new ArrayList<JobPosition>(_jobPositions);
		JobPosition obj = list.get(0);
		currentUserProfile.setFavoriteJobs(_jobPositions);
		_jobSeekerProfileServices.updateSeeker(currentUserProfile);
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/applyfromFav", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int applyfromFav(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Application _jobApplication) {

		Application _newApplication = new Application();
		_newApplication.setJobPosition(_jobApplication.getJobPosition());
		_newApplication.setJobSeeker(currentUserProfile);
		_newApplication.setStatus(_jobApplication.getStatus());
		_newApplication.setResumeKey(_jobApplication.getResumeKey());
		Set<Application> listApplications = currentUserProfile.getApplications();
		listApplications.add(_newApplication);
		_jobSeekerProfileServices.updateSeeker(currentUserProfile);
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/cancelApplied", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int cancelfromApplied(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Application _jobApplication) {

		_jobSeekerApplicationServices.cancelJob(currentUserProfile, _jobApplication);
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

}
