package com.Jobportal.Dao;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.Company;

public interface CompanyDao extends CrudRepository<Company, Long> {

	public Company findByuserId(int user_id);

}
