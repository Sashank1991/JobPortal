package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.Jobportal.DAO.JobSeekerProfileDao;
import com.Jobportal.Entities.JobSeeker;

@Service
@Configurable
public class JobSeekerProfileServices {

	@Autowired
	JobSeekerProfileDao _jobSeekerProfileDao;

	public JobSeeker getSeeker(int seekerId) {
		return _jobSeekerProfileDao.findByuserId(seekerId);
	}

}
