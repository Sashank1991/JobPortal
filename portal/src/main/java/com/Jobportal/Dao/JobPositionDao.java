package com.Jobportal.Dao;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.JobPosition;

public interface JobPositionDao extends CrudRepository<JobPosition,Long>
{

	public JobPosition findByjobId(int jobId);
}
