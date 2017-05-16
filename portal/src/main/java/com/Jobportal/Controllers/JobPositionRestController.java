package com.Jobportal.Controllers;

import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/jobPosition")
@SessionAttributes({"currentCompany"})
public class JobPositionRestController {

    @Autowired
    JobPositionService _jobPositionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getJobPosition(@PathVariable("id") String id) {

        return new ResponseEntity<>(_jobPositionService.getJobDetail(Long.parseLong(id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    public int createJobPosition(@ModelAttribute("currentCompany") Company currentCompany,
                                 @RequestBody JobPosition _jobPosition) {

        _jobPosition.setCompany(currentCompany);
        _jobPositionService.create(_jobPosition);

        return 0;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    public int updateJobPosition(@RequestBody JobPosition _jobPosition) {

        _jobPositionService.update(_jobPosition);

        return 0;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllJobPositions(@ModelAttribute("currentCompany") Company currentCompany) {

        return new ResponseEntity<>(_jobPositionService.getAllPositions(currentCompany),
                HttpStatus.OK);
    }


}
