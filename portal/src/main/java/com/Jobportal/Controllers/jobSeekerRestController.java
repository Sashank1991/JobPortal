package com.Jobportal.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.Jobportal.DAO.CustomerRepositoryDao;
import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.CustomerImage;
import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.FileArchiveService;
import com.Jobportal.Service.JobSeekerApplicationServices;
import com.Jobportal.Service.JobSeekerProfileServices;

@RestController
@SessionAttributes({ "currentUserProfile", "currentResume" })
public class jobSeekerRestController {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;
	
	@Autowired
	private FileArchiveService fileArchiveService;
	
	@Autowired
	private CustomerRepositoryDao customerRepository;

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
	public int applyfromFav(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile,
			@ModelAttribute("currentResume") MultipartFile resume, Model model,
			@RequestBody Application _jobApplication) {
		Application reqApplication = _jobApplication;

		Application _newApplication = new Application();
		_newApplication.setJobPosition(reqApplication.getJobPosition());
		_newApplication.setJobSeeker(currentUserProfile);
		_newApplication.setStatus(reqApplication.getStatus());
		_newApplication.setResumeKey(reqApplication.getResumeKey());
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

	@RequestMapping(value = "/uploadCurrentResume", method = RequestMethod.POST)
	public String uploadCurrentResume(@RequestBody MultipartFile doc, Model model) throws IOException {
		model.addAttribute("currentResume", doc);
		CustomerImage customerImage = fileArchiveService.saveFileToS3(doc);
		customerRepository.save(customerImage);
		return customerImage.getUrl();
	}

}

class ApplyRequestModel {
	private MultipartFile resume;
	private Application _jobApplication;

	public MultipartFile getResume() {
		return resume;
	}

	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}

	public Application get_jobApplication() {
		return _jobApplication;
	}

	public void set_jobApplication(Application _jobApplication) {
		this._jobApplication = _jobApplication;
	}

}
