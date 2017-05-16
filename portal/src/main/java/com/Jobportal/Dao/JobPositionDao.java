package com.Jobportal.Dao;

import com.Jobportal.Entities.Company;
import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.JobPosition;

import java.util.List;

public interface JobPositionDao extends CrudRepository<JobPosition, Long> {

    List<JobPosition> findByCompany(Company company);
}
