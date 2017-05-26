package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Jobportal.Dao.CompanyDao;

import com.Jobportal.Entities.Company;

@Service
@Configurable
public class CompanyService {

	@Autowired
	CompanyDao _companyDaoDao;
	
	@Transactional
	public Company getCompany(int userId) {
		return _companyDaoDao.findByUserId(userId);
	}
	
	@Transactional
	public Company getCompany(String email) {
		return _companyDaoDao.findByemail(email);
	}
	
	@Transactional
	public Company createOrUpdate(Company company) {
		return _companyDaoDao.save(company);
	}

}
