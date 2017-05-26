package com.Jobportal.Service;

import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.Jobportal.Dao.JobPositionDao;
import com.Jobportal.Dao.JobSeekerApplicationsDao;
import com.Jobportal.Dao.JobSeekerProfileDao;
import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.JobSeeker;

@Service
@Configurable
public class JobSeekerApplicationServices {

	@Autowired
	JobSeekerProfileDao _jobSeekerProfileDao;

	@Autowired
	JobSeekerApplicationsDao _jobSeekerApplicationsDao;
	@Autowired
	JobPositionDao _jobPositionDao;

	@Autowired
	JobSeekerProfileServices _jobSeekerProfileServices;

	// create a seeker

	@Transactional
	public int cancelJob(JobSeeker _jobSeeker, Application _application,String status) {
		Application _foundApplication = null;
		Set<Application> listApplications = _jobSeeker.getApplications();
		for (Iterator<Application> it = listApplications.iterator(); it.hasNext();) {
			Application f = it.next();
			if (f.getApplicationId() == _application.getApplicationId()) {
				// it.remove();
				_foundApplication = _jobSeekerApplicationsDao.findByapplicationId(f.getApplicationId());
			}
		}
		// _jobSeekerProfileServices.updateSeeker(_jobSeeker);

		_foundApplication.setStatus(status);

		_jobSeekerApplicationsDao.save(_foundApplication);

		return 0;
	}

	@Transactional
	void updateApplicationStatus(Application application, String status) {
		application.setStatus(status);
		_jobSeekerApplicationsDao.save(application);
	}

}
