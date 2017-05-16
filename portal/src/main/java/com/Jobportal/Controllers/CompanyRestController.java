package com.Jobportal.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Jobportal.Entities.Company;
import com.Jobportal.Service.CompanyService;


@RestController
@RequestMapping(value = "/company")
@SessionAttributes({"currentCompany"})
public class CompanyRestController {

    @Autowired
    CompanyService _companyService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getCompany(@PathVariable("id") int id) {

        return new ResponseEntity<>(_companyService.getCompany(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getCurrentCompany(@ModelAttribute("currentCompany") Company currentCompany) {
        // TODO: if no session redirect him to the login
        return new ResponseEntity<>(currentCompany, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public int createCompany(Model model, @RequestBody Company _company) {

        Company company = _companyService.createOrUpdate(_company);
        // update session
        model.addAttribute("currentCompany", company);

        return 200;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    public int updateCompany(Model model, @RequestBody Company _company) {

        // TODO: if no session redirect him to the login
        Company company = _companyService.createOrUpdate(_company);
        // update session
        model.addAttribute("currentCompany", company);

        return 200;
    }


}
