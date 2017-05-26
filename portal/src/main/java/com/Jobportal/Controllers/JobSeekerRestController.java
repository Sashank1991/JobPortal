package com.Jobportal.Controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.Jobportal.Service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.Jobportal.Dao.CustomerRepositoryDao;
import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.CustomerImage;
import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.FileArchiveService;
import com.Jobportal.Service.JobSeekerApplicationServices;
import com.Jobportal.Service.JobSeekerProfileServices;

import antlr.collections.List;

@RestController
@SessionAttributes({ "currentUserProfile", "currentResume" })
public class JobSeekerRestController {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@Autowired
	private FileArchiveService fileArchiveService;

	@Autowired
	private CustomerRepositoryDao customerRepository;

	@Autowired
	JobSeekerApplicationServices _jobSeekerApplicationServices;

	@Autowired
	EmailServiceImpl _emailServiceImpl;

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
		currentUserProfile.setSelfIntroduction(profile.get("selfIntroduction"));
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
			@RequestBody Application _jobApplication) throws Exception {
		try {
			int count = 0;
			boolean error = false;
			Set<Application> listApplications = currentUserProfile.getApplications();

			Iterator<Application> itr = listApplications.iterator();

			while (itr.hasNext()) {

				Application application = itr.next();

				if (application.getStatus().equalsIgnoreCase("pending")) {
					count++;
				}
				if (_jobApplication.getJobPosition().getJobId() == application.getJobPosition().getJobId() 
						&& (application.getStatus().equalsIgnoreCase("pending")
								|| application.getStatus().equalsIgnoreCase("offered"))) {
					error = true;
				}

			}

			if (count > 5 || error) {

				return 1;
			}

			Application _newApplication = new Application();
			_newApplication.setJobPosition(_jobApplication.getJobPosition());
			_newApplication.setJobSeeker(currentUserProfile);
			_newApplication.setStatus(_jobApplication.getStatus());
			_newApplication.setResumeKey(_jobApplication.getResumeKey());

			listApplications.add(_newApplication);
			_jobSeekerProfileServices.updateSeeker(currentUserProfile);

			_emailServiceImpl.sendSimpleMessage(currentUserProfile.getEmail(), "Job Applied",
					"Thank for applying for " + _jobApplication.getJobPosition().getJobId() + "-"
							+ _jobApplication.getJobPosition().getTitle() + "position");

			// update session
			JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
			model.addAttribute("currentUserProfile", currentJobSeeker);
			// if no session redirect him to the login
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	@RequestMapping(value = "/cancelApplied", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int cancelfromApplied(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Application _jobApplication) {

		_jobSeekerApplicationServices.cancelJob(currentUserProfile, _jobApplication, "cancelled");
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/acceptfromApplied", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int acceptfromApplied(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Application _jobApplication) {

		_jobSeekerApplicationServices.cancelJob(currentUserProfile, _jobApplication, "offerAccepted");
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/rejectfromApplied", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public int rejectfromApplied(@ModelAttribute("currentUserProfile") JobSeeker currentUserProfile, Model model,
			@RequestBody Application _jobApplication) {

		_jobSeekerApplicationServices.cancelJob(currentUserProfile, _jobApplication, "offerRejcted");
		// update session
		JobSeeker currentJobSeeker = _jobSeekerProfileServices.getSeeker(currentUserProfile.getUserId());
		model.addAttribute("currentUserProfile", currentJobSeeker);
		// if no session redirect him to the login
		return 0;
	}

	@RequestMapping(value = "/uploadCurrentResume", method = RequestMethod.POST)
	public String uploadCurrentResume(@RequestParam(value = "image", required = true) MultipartFile doc, Model model)
			throws IOException {
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

class ManipulateApplication {
	private String status;
	private Application jobItem;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Application get_jobApplication() {
		return jobItem;
	}

	public void set_jobApplication(Application _jobApplication) {
		this.jobItem = _jobApplication;
	}

}