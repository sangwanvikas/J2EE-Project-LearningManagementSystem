package com.lms.service;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.JobApplicationDao;
import com.lms.model.JobApplication;

@Path("/jwsJobApplicationService")
public class JobApplicationService {	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createJobApplication")
	public JobApplication createJobApplicationService(JobApplication jobApplication) {
		JobApplicationDao JobApplicationDaoObj = JobApplicationDao.getInstance();
		JobApplicationDaoObj.createJobApplication(jobApplication);
		return jobApplication;
	}

}
