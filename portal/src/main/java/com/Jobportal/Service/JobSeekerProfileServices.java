package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.Jobportal.Dao.JobPositionDao;
import com.Jobportal.Dao.JobSeekerApplicationsDao;
import com.Jobportal.Dao.JobSeekerProfileDao;
import com.Jobportal.Entities.JobSeeker;

@Service
@Configurable
public class JobSeekerProfileServices {

	@Autowired
	JobSeekerProfileDao _jobSeekerProfileDao;

	@Autowired
	JobSeekerApplicationsDao _jobSeekerApplicationsDao;
	@Autowired
	JobPositionDao _jobPositionDao;

	/**
	 * @param seekerId
	 * @return
	 */
	public JobSeeker getSeeker(int seekerId) {
		return _jobSeekerProfileDao.findByuserId(seekerId);
	}
	
	public JobSeeker getSeeker(String emailId) {
		return _jobSeekerProfileDao.findByemail(emailId);
	}

	// update seeker

	public JobSeeker updateSeeker(JobSeeker _jobSeeker) {
		return _jobSeekerProfileDao.save(_jobSeeker);
	}

	// create a seeker

	public JobSeeker createSeeker(JobSeeker _jobSeeker) {
		return _jobSeekerProfileDao.save(_jobSeeker);
	}

}
