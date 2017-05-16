/**
 * 
 */
package com.Jobportal.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Service.JobSearchServices;

/**
 * @author rahul.vudutala
 *
 */

@RestController
public class JobSearchRestController {
	
	@Autowired
	JobSearchServices _jobSearchService;
	
	@RequestMapping(value = "/jobs/{searchString}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<JobPosition> getJobPositions(@PathVariable("searchString") String searchString) {
		return _jobSearchService.findJobs(searchString);
	}
}
