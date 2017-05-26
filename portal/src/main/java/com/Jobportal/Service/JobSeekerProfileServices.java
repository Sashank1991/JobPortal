package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public JobSeeker getSeeker(int seekerId) {
		return _jobSeekerProfileDao.findByuserId(seekerId);
	}
	
	@Transactional
	public JobSeeker getSeeker(String emailId) {
		return _jobSeekerProfileDao.findByemail(emailId);
	}

	// update seeker

	@Transactional
	public JobSeeker updateSeeker(JobSeeker _jobSeeker) {
		return _jobSeekerProfileDao.save(_jobSeeker);
	}

	// create a seeker

	@Transactional
	public JobSeeker createSeeker(JobSeeker _jobSeeker) {
		return _jobSeekerProfileDao.save(_jobSeeker);
	}

}
