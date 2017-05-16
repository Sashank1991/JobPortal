package com.Jobportal.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.Jobportal.Entities.JobPosition;

public interface JobSearchDao extends CrudRepository<JobPosition, Long> {
	
	@Query(value="select * from job_position join company on company_id = user_id"
			+ " where (MATCH( job_position.description, office_location, responsibilities, salary, title ) against( :searchString IN NATURAL LANGUAGE MODE) "
			+ "or  MATCH(name) against( :searchString IN NATURAL LANGUAGE MODE)) "
			+ "and ('' in :companyNames or name in :companyNames) "
			+ "and ('' in :locationNames or office_location in :locationNames) "
			+ "and salary between :minSalary and :maxSalary",nativeQuery=true)
	public List<JobPosition> findJobs(@Param("searchString") String searchString, @Param("companyNames") List<String> companyNames, @Param("locationNames") List<String> locationNames,@Param("minSalary") String minSalary, @Param("maxSalary") String maxSalary);
	
	@Query(value="select count(*) from job_position join company on company_id = user_id"
			+ " where (MATCH(job_position.description, office_location, responsibilities, salary, title ) against( :searchString IN NATURAL LANGUAGE MODE) "
			+ "or  MATCH(name) against( :searchString IN NATURAL LANGUAGE MODE)) "
			+ "and ('' in :companyNames or name in :companyNames) "
			+ "and ('' in :locationNames or office_location in :locationNames) "
			+ "and salary between :minSalary and :maxSalary",nativeQuery=true)
	public int findJobsCount(@Param("searchString") String searchString, @Param("companyNames") List<String> companyNames, @Param("locationNames") List<String> locationNames, @Param("minSalary") String minSalary, @Param("maxSalary") String maxSalary);
}
