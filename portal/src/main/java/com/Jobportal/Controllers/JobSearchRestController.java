/**
 * 
 */
package com.Jobportal.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/jobs", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<JobPosition> getJobPositions(@RequestBody Search search) {

		if (search.getCompanyName().size() < 1) {
			List<String> companyNames = search.getCompanyName();
			companyNames.add("");
			search.setCompanyName(companyNames);
		}

		if (search.getOfficeLocation().size() < 1) {
			List<String> locations = search.getOfficeLocation();
			locations.add("");
			search.setOfficeLocation(locations);
		}

		return _jobSearchService.findJobs(search.getSearchString(), search.getCompanyName(), search.getOfficeLocation(),
				search.getMinSal(), search.getMaxSal());
	}
}

class Search {

	private String searchString;
	private List<String> companyName;
	private List<String> officeLocation;
	private String minSal;
	private String maxSal;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<String> getCompanyName() {
		return companyName;
	}

	public void setCompanyName(List<String> companyName) {
		this.companyName = companyName;
	}

	public List<String> getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(List<String> officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getMinSal() {
		return minSal;
	}

	public void setMinSal(String minSal) {
		this.minSal = minSal;
	}

	public String getMaxSal() {
		return maxSal;
	}

	public void setMaxSal(String maxSal) {
		this.maxSal = maxSal;
	}

}
