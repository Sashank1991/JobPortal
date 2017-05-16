package com.Jobportal.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="Company")
public class Company extends User{

    @Column
    private String name;

    @Column
    private String website;

    @Column(name="logo_image_url")
    private String logoImageURL;

    @Column(name="address_hq")
    private String addressHQ;

    @Column
    private String contact;

    @Column
    private String description;

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

    @JsonIgnore
	@JsonIgnoreProperties({ "jobSeeker","company" })
    public List<JobPosition> getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(List<JobPosition> jobPositions) {
        this.jobPositions = jobPositions;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
