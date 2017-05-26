package com.Jobportal.Service;

import com.Jobportal.Dao.JobPositionDao;
import com.Jobportal.Entities.Application;
import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Configurable
public class JobPositionService {

    @Autowired
    JobPositionDao _jobPositionDao;

    @Autowired
    EmailServiceImpl _emailServiceImpl;

    @Autowired
    JobSeekerApplicationServices _jobSeekerApplicationServices;

    public JobPosition create(JobPosition jobPosition) {
        return _jobPositionDao.save(jobPosition);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int update(JobPosition jobPosition) {
        boolean hasOfferAccepted = false;

        for (Application application : jobPosition.getApplications()) {

            // if an application is in OfferAccepted state, the company cannot cancel this position.
            if (jobPosition.getStatus() == 0 && application.getStatus().equals("offerAccepted")) {
                return -2;
            }

            if (!hasOfferAccepted && application.getStatus().equals("offerAccepted")) {
                hasOfferAccepted = true;
            }

        }

        // a position can't be marked as Filled if it does not have an offer accepted.
        if (jobPosition.getStatus() == 2 && !hasOfferAccepted) {
            return -3;
        }

        if (_jobPositionDao.save(jobPosition) != null) {

            for (Application application : jobPosition.getApplications()) {

                // When a job is updated, all the current applicants (applications in terminal states are not considered)are
                // notified about the change..
                if (application.getStatus().equals("pending") || application.getStatus().equals("offered")) {

                    String email = application.getJobSeeker().getEmail();
                    _emailServiceImpl.sendSimpleMessage(email, "Job Position: " + jobPosition.getTitle() +
                            " got updated", "Please check Job Portal for more details.");
                }

            }

            if (jobPosition.getStatus() == 0 || jobPosition.getStatus() == 2) {

                for (Application application : jobPosition.getApplications()) {

                    // When a job is filled or cancelled, all related applications in non terminal state get cancelled
                    if (!(application.getStatus().equals("pending") || application.getStatus().equals("offered"))) {
                        _jobSeekerApplicationServices.updateApplicationStatus(application, "cancelled");
                    }

                }

            }

            return 1;

        } else {

            return -1;
        }

    }

    @Transactional
    public JobPosition getJobDetail(int jobId) {
        return _jobPositionDao.findByJobId(jobId);
    }
    
    @Transactional
    public List<JobPosition> getAllPositions(Company company) {
        return _jobPositionDao.findByCompany(company);
    }


}
