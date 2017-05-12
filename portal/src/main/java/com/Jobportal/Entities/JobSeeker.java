package com.Jobportal.Entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="JobSeeker")
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

	@OneToMany(mappedBy = "jobSeeker")
	private List<Application> applications;

	@ManyToMany
	@JoinTable(name = "favoriteJob",
			joinColumns = {@JoinColumn(name = "jobSeekerId", referencedColumnName = "userId")},
			inverseJoinColumns = {@JoinColumn(name = "jobPositionId", referencedColumnName = "jobId")})
	private List<JobPosition> favoriteJobs;

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

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<JobPosition> getFavoriteJobs() {
		return favoriteJobs;
	}

	public void setFavoriteJobs(List<JobPosition> favoriteJobs) {
		this.favoriteJobs = favoriteJobs;
	}


}
