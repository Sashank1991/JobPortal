package com.Jobportal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobSeeker;
import com.Jobportal.Service.CompanyService;
import com.Jobportal.Service.JobSeekerProfileServices;

@RestController
public class jobSeekerRestController {

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	@RequestMapping(value = "/seeker/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public JobSeeker getSeeker(@PathVariable("id") int seekerId) {
		JobSeeker abc = _jobSeekerProfileServices.getSeeker(seekerId);
		return abc;
	}

}
