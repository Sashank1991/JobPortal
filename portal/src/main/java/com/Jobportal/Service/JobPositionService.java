package com.Jobportal.Service;

import com.Jobportal.Dao.JobPositionDao;
import com.Jobportal.Entities.Company;
import com.Jobportal.Entities.JobPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Configurable
public class JobPositionService {

    @Autowired
    JobPositionDao _jobPositionDao;

    public JobPosition create(JobPosition jobPosition) {
        return _jobPositionDao.save(jobPosition);
    }

    public JobPosition update(JobPosition jobPosition) {
        // TODO: When a job is updated, all the current applicants (applications in terminal states are not considered)are notified about the change.
        return _jobPositionDao.save(jobPosition);
    }

    public JobPosition getJobDetail(int jobId) {
        return _jobPositionDao.findByJobId(jobId);
    }

    public List<JobPosition> getAllPositions(Company company) {
        return _jobPositionDao.findByCompany(company);
    }


}
