package com.Jobportal.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Application")
public class Application {

	@Id
	@GeneratedValue
	private int applicationId;

	@Column(name = "resumeKey")
	private String resumeKey;

	@Column(name = "status")
	private String status;

	// for ManyToOne mapping between JobPosition entity and Application;
	// where JopPosition is the owner

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "jobId")
	private JobPosition jobPosition;

	// for ManyToOne mapping between JobSeeker entity and Application;
	// where JobSeeker is the owner
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "jobSeekerId")
	private JobSeeker jobSeeker;

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getResumeKey() {
		return resumeKey;
	}

	public void setResumeKey(String resumeKey) {
		this.resumeKey = resumeKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnoreProperties({ "applications", "jobSeeker" })
	public JobPosition getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(JobPosition jobPosition) {
		this.jobPosition = jobPosition;
	}

	@JsonIgnoreProperties({ "applications", "favoriteJobs" })
	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

}
