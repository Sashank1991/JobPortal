package com.Jobportal.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserFavorites")
public class UserFavorites {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "jobId")
	private int jobId;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

}
