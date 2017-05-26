package com.Jobportal.Controllers;

import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobPosition;
import com.Jobportal.Entities.Response;
import com.Jobportal.Service.JobPositionService;
import com.Jobportal.Service.JobSeekerApplicationServices;
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

    @Autowired
    JobSeekerApplicationServices _jobSeekerApplicationServices;

    // done
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getJobPosition(@PathVariable("id") String id) {

        JobPosition jobPosition = _jobPositionService.getJobDetail(Integer.parseInt(id));

        if (jobPosition == null) {
            return new ResponseEntity<>(new Response(404), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jobPosition, HttpStatus.OK);
    }

    // done
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createJobPosition(@ModelAttribute("currentCompany") Company currentCompany,
                                               @RequestBody JobPosition _jobPosition) {

        _jobPosition.setCompany(currentCompany);
        // default status is open
        _jobPosition.setStatus(1);
        JobPosition jobPosition = _jobPositionService.create(_jobPosition);

        if (jobPosition == null) {
            return new ResponseEntity<>(new Response(500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Response(200), HttpStatus.OK);
    }

    // done
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateJobPosition(@RequestBody JobPosition _jobPosition) {

        int resultCode = _jobPositionService.update(_jobPosition);

        if (resultCode == -1) {

            return new ResponseEntity<>(new Response(500), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (resultCode == -2) {

            return new ResponseEntity<>(new Response(400), HttpStatus.OK);
        } else if (resultCode == -3) {

            return new ResponseEntity<>(new Response(403), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(new Response(200), HttpStatus.OK);
        }
    }

    // done
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllJobPositions(@ModelAttribute("currentCompany") Company currentCompany) {

        return new ResponseEntity<>(_jobPositionService.getAllPositions(currentCompany),
                HttpStatus.OK);
    }


    @RequestMapping(value = "/application/accept", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> acceptApplication(@RequestBody Application _application) {

        _jobSeekerApplicationServices.updateApplicationStatus(_application, "offered");
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/application/decline", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> declineApplication(@RequestBody Application _application) {

        _jobSeekerApplicationServices.updateApplicationStatus(_application, "rejected");
        return new ResponseEntity<>("", HttpStatus.OK);
    }


}
