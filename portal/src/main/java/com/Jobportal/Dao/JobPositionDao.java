package com.Jobportal.Dao;

import com.Jobportal.Entities.Company;
import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.JobPosition;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(Transactional.TxType.REQUIRED)
public interface JobPositionDao extends CrudRepository<JobPosition, Long> {

    List<JobPosition> findByCompany(Company company);

    JobPosition findByJobId(int jobId);

}
