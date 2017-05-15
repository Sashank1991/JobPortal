package com.Jobportal.Dao;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.Application;

public interface JobSeekerApplicationsDao extends CrudRepository<Application, Long> {
	public Application findByapplicationId(int application_Id);
}
