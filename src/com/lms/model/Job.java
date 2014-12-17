package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the job_description database table.
 * 
 */
@Entity
@Table(name="job_description")
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="job_id")
	private int jobId;

	@Column(name="course_id")
	private int courseId;

	@Column(name="job_desc")
	private String jobDesc;

	@Column(name="job_requirement")
	private String jobRequirement;

	@Column(name="job_responsobility")
	private String jobResponsobility;

	@Column(name="job_title")
	private String jobTitle;

	@Column(name="user_id")
	private int userId;
    
	@JsonIgnore
	//bi-directional many-to-one association to JobApplication
	@OneToMany(mappedBy="job", fetch=FetchType.EAGER)
	private List<JobApplication> jobApplications;
	
	@JsonIgnore
	@ManyToOne(optional=true, cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;

	public Job() {
	}

	
	
	public Job(int jobId, int courseId, String jobDesc, String jobRequirement,
			String jobResponsobility, String jobTitle, int userId,
			List<JobApplication> jobApplications) {
		super();
		this.jobId = jobId;
		this.courseId = courseId;
		this.jobDesc = jobDesc;
		this.jobRequirement = jobRequirement;
		this.jobResponsobility = jobResponsobility;
		this.jobTitle = jobTitle;
		this.userId = userId;
		this.jobApplications = jobApplications;
	}



	public Job(int courseId, String jobDesc, String jobRequirement,
			String jobResponsobility, String jobTitle, int userId,
			List<JobApplication> jobApplications) {
		super();
		this.courseId = courseId;
		this.jobDesc = jobDesc;
		this.jobRequirement = jobRequirement;
		this.jobResponsobility = jobResponsobility;
		this.jobTitle = jobTitle;
		this.userId = userId;
		this.jobApplications = jobApplications;
	}



	public int getJobId() {
		return this.jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Object getJobDesc() {
		return this.jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public Object getJobRequirement() {
		return this.jobRequirement;
	}

	public void setJobRequirement(String jobRequirement) {
		this.jobRequirement = jobRequirement;
	}

	public Object getJobResponsobility() {
		return this.jobResponsobility;
	}

	public void setJobResponsobility(String jobResponsobility) {
		this.jobResponsobility = jobResponsobility;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<JobApplication> getJobApplications() {
		return this.jobApplications;
	}

	public void setJobApplications(List<JobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}

	public JobApplication addJobApplication(JobApplication jobApplication) {
		getJobApplications().add(jobApplication);
		jobApplication.setJob(this);

		return jobApplication;
	}

	public JobApplication removeJobApplication(JobApplication jobApplication) {
		getJobApplications().remove(jobApplication);
		jobApplication.setJob(null);

		return jobApplication;
	}

}