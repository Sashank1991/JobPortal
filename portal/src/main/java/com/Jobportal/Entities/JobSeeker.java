package com.Jobportal.Entities;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "JobSeeker")
public class JobSeeker extends User {

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "picKey")
	private String picKey;

	@Column(name = "workExperience")
	private String workExperience;

	@Column(name = "education")
	private String education;

	@Column(name = "skills")
	private String skills;

	@OneToMany(mappedBy = "jobSeeker", fetch = FetchType.EAGER)
	private Set<Application> applications;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "favoriteJob", joinColumns = {
			@JoinColumn(name = "jobSeekerId", referencedColumnName = "userId") }, inverseJoinColumns = {
					@JoinColumn(name = "jobPositionId", referencedColumnName = "jobId") })
	private Set<JobPosition> favoriteJobs;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPicKey() {
		return picKey;
	}

	public void setPicKey(String picKey) {
		this.picKey = picKey;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	@JsonIgnoreProperties({ "jobSeeker" })
	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

	@JsonIgnoreProperties({ "jobSeeker", "applications" })
	public Set<JobPosition> getFavoriteJobs() {
		return favoriteJobs;
	}

	public void setFavoriteJobs(Set<JobPosition> favoriteJobs) {
		this.favoriteJobs = favoriteJobs;
	}

}
