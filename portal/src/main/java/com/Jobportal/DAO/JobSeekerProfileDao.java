package com.Jobportal.DAO;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.JobSeeker;

public interface JobSeekerProfileDao extends CrudRepository<JobSeeker, Long> {

	public JobSeeker findByuserId(int user_id);

}
