package com.Jobportal.Dao;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.Company;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
public interface CompanyDao extends CrudRepository<Company, Long> {

    Company findByUserId(int userId);

    Company findByemail(String email);

}
