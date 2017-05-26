/**
 * 
 */
package com.Jobportal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Jobportal.Dao.JobSearchDao;
import com.Jobportal.Entities.JobPosition;

/**
 * @author rahul.vudutala
 *
 */

@Service
@Configurable
public class JobSearchServices {

	@Autowired
	JobSearchDao _jobSearchDao;
	
	@Transactional
	public List<JobPosition> findJobs(String searchString, List<String> company, List<String> officeLocation,
			String minSal, String maxSal) {
		List<JobPosition> l = _jobSearchDao.findJobs(searchString, company, officeLocation, minSal, maxSal);
		return l;
	}
}
