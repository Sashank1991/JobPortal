package com.Jobportal.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Jobportal.Entities.Company;
import com.Jobportal.Service.CompanyService;

@RestController
public class CompanyRestController {

	@Autowired
	CompanyService _companyService;

	@RequestMapping(value = "/company/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Company getCompany(@PathVariable("id") int seekerId) {
		Company abc = _companyService.getSeeker(seekerId);
		return abc;
	}
}
