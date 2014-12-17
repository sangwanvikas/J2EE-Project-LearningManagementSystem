package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the job_application database table.
 * 
 */
@Entity
@Table(name="job_application")
@NamedQuery(name="JobApplication.findAll", query="SELECT j FROM JobApplication j")
public class JobApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int job_App_Id;

	private String gpa;

	@Column(name="user_id")
	private int userId;
	
	@Column(name="job_id")
	private int jobId;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	@JsonIgnore
	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="job_id",insertable=false,updatable=false)
	private Job job	;
	
	@ManyToOne
	@JoinColumn(name="user_id",insertable=false,updatable=false)
	private User user;

	public JobApplication(String gpa, int userId, int jobId, Job job) {
		super();
		this.gpa = gpa;
		this.userId = userId;
		this.jobId=jobId;
		this.job = job;
	}

	public JobApplication(int job_App_Id, String gpa, int userId,
			Job job) {
		super();
		this.job_App_Id = job_App_Id;
		this.gpa = gpa;
		this.userId = userId;
		this.job = job;
	}

	public JobApplication() {
	}

	public int getJob_App_Id() {
		return this.job_App_Id;
	}

	public void setJob_App_Id(int job_App_Id) {
		this.job_App_Id = job_App_Id;
	}

	public String getGpa() {
		return this.gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobApplication [job_App_Id=");
		builder.append(job_App_Id);
		builder.append(", gpa=");
		builder.append(gpa);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", jobId=");
		builder.append(jobId);
		builder.append(", job=");
		builder.append(job.getJobTitle());
		builder.append(", user=");
		builder.append(user.getUserName());
		builder.append("]");
		return builder.toString();
	}

}