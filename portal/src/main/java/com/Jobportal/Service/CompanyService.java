package com.Jobportal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.Jobportal.Dao.CompanyDao;

import com.Jobportal.Entities.Company;

@Service
@Configurable
public class CompanyService {

    @Autowired
    CompanyDao _companyDaoDao;

    public Company getCompany(int userId) {
        return _companyDaoDao.findByUserId(userId);
    }

    public Company createOrUpdate(Company company) {
        return _companyDaoDao.save(company);
    }

}
