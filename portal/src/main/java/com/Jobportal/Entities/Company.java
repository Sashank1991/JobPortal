package com.Jobportal.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="Company")
public class Company extends User{

    @Column
    private String name;

    @Column
    private String website;

    @Column
    private String logoImageURL;

    @Column
    private String addressHQ;

    
    @OneToMany(mappedBy = "company")
    private List<JobPosition> jobPositions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoImageURL() {
        return logoImageURL;
    }

    public void setLogoImageURL(String logoImageURL) {
        this.logoImageURL = logoImageURL;
    }

    public String getAddressHQ() {
        return addressHQ;
    }

    public void setAddressHQ(String addressHQ) {
        this.addressHQ = addressHQ;
    }
	@JsonIgnoreProperties({ "jobSeeker","company" })
    public List<JobPosition> getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(List<JobPosition> jobPositions) {
        this.jobPositions = jobPositions;
    }

}