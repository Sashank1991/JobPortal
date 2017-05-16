package com.Jobportal.Dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Jobportal.Entities.CustomerImage;


public interface CustomerRepositoryDao extends CrudRepository<CustomerImage, Long> {

    public List<CustomerImage> findByKey(String key); 
}