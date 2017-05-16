/**
 * 
 */
package com.Jobportal.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

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
	public List<JobPosition> findJobs(String searchString) {
		System.out.println(searchString);
		List<String> test = new ArrayList<>();
		test.add("");
		List<JobPosition> l = _jobSearchDao.findJobs(searchString, test, test,"0","1234567");
		System.out.println(l);
		return l; 
	}
}
