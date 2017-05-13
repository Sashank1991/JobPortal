package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.Jobportal.DAO.CompanyDao;

import com.Jobportal.Entities.Company;

@Service
@Configurable
public class CompanyService {
	@Autowired
	CompanyDao _companyDaoDao;

	public Company getSeeker(int seekerId) {
		return _companyDaoDao.findByuserId(seekerId);
	}
}
