package com.Jobportal.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="UserProfiles")
public class UserProfiles {
	@Id
	@Column(name = "userId")
	private int userId;
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

	@OneToMany
	@JoinColumn(name = "userId")
	private List<UserFavorites> favs;
	@OneToMany
	@JoinColumn(name = "userId")
	private List<UserJobApplications> applications;

	public List<UserJobApplications> getApplications() {
		return applications;
	}

	public void setApplications(List<UserJobApplications> applications) {
		this.applications = applications;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

}
