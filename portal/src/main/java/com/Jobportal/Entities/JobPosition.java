package com.Jobportal.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "JobPosition")
public class JobPosition {

    @Id
    @GeneratedValue
    @Column
    private int jobId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String responsibilities;

    @Column
    private String officeLocation;

    @Column
    private String salary;

    @Column(name="status", columnDefinition = "int default 1")
    private int status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId")
    private Company company;

    @OneToMany(mappedBy = "jobPosition")
    private List<Application> applications;

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteJobs")
    private List<JobSeeker> jobSeekers;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @JsonIgnoreProperties({"jobPosition"})
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @JsonIgnoreProperties({"jobPosition"})
    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<JobSeeker> getJobSeekers() {
        return jobSeekers;
    }

    public void setJobSeekers(List<JobSeeker> jobSeekers) {
        this.jobSeekers = jobSeekers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
