package com.Jobportal.Controllers;


import com.Jobportal.Entities.Response;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createCompany(Model model, @RequestBody Company _company) {

        Company company = _companyService.createOrUpdate(_company);
        // update session
        model.addAttribute("currentCompany", company);

        return new ResponseEntity<>(new Response(200), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getCompany(@PathVariable("id") int id) {

        return new ResponseEntity<>(_companyService.getCompany(id), HttpStatus.OK);
    }

    // done
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getCurrentCompany(@ModelAttribute("currentCompany") Company currentCompany) {
        // if session object is null
        if (currentCompany == null) {
            return new ResponseEntity<>(new Response(408), HttpStatus.REQUEST_TIMEOUT);
        }

        return new ResponseEntity<>(currentCompany, HttpStatus.OK);
    }

    // done
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateCompany(@ModelAttribute("currentCompany") Company currentCompany,
                                           @RequestBody Company _company, Model model) {

        // if session object is null
        if (currentCompany == null) {
            return new ResponseEntity<>(new Response(408), HttpStatus.REQUEST_TIMEOUT);
        }

        currentCompany.setDescription(_company.getDescription());
        currentCompany.setAddressHQ(_company.getAddressHQ());
        currentCompany.setContact(_company.getContact());
        currentCompany.setLogoImageURL(_company.getLogoImageURL());
        currentCompany.setName(_company.getName());
        currentCompany.setWebsite(_company.getWebsite());

        Company company = _companyService.createOrUpdate(currentCompany);

        // update session
        model.addAttribute("currentCompany", company);

        return new ResponseEntity<>(new Response(200), HttpStatus.OK);
    }


}
