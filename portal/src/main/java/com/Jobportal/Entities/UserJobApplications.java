package com.Jobportal.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserJobApplications")
public class UserJobApplications {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "jobId")
	private int jobId;
	@Column(name = "resumeKey")
	private String resumeKey;
	@Column(name = "status")
	private String status;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getKey() {
		return resumeKey;
	}

	public void setKey(String key) {
		this.resumeKey = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
