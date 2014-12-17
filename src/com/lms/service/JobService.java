package com.lms.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.JobDao;
import com.lms.dao.ThreadDao;
import com.lms.dao.UserDao;
import com.lms.model.Job;
import com.lms.model.Thread;
import com.lms.model.User;

@Path("/jwsJobService")
public class JobService {

	
	
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/createAJob")
	public Job createJobService(Job jobObject) {	
		 System.out.println(jobObject);
		JobDao jobDaoObj = JobDao.getInstance();
		jobDaoObj.createJob(jobObject);
		return jobObject;
	}
	 
	 
	 
	 
	/* @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/findJobsByProfessorId/{userId}")
	 public List<Job> findJobsByProfessorIdService(@PathParam("userId") int userId){
	  JobDao jobDaoObj = JobDao.getInstance();
	  return jobDaoObj.findJobsByProfessorId(userId);
	 }*/
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/findJobsByProfessorIdAndCourseId/{userId}/{courseId}")
	 public List<Job> findJobsByProfessorIdAndCourseIdService(@PathParam("userId") int userId, @PathParam("courseId") int courseId){
	  JobDao jobDaoObj = JobDao.getInstance();
	  return jobDaoObj.findJobsByProfessorIdAndCourseId(userId,courseId);
	 }
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Path("/findAllJobsByCourseId/{courseId}")
	 public List<Job> findJobsByCourseIdService(@PathParam("courseId") int courseId){
	  JobDao jobDaoObj = JobDao.getInstance();
	  return jobDaoObj.findJobsByCourseId(courseId);
	 }
	 
	    @GET
		@Produces(MediaType.APPLICATION_JSON)
		//@Consumes(MediaType.APPLICATION_JSON)
		@Path("/findAJobByJobId/{jobId}")
		public Job findAJobByJobIdService(@PathParam("jobId") int jobId) {		
			JobDao courseDaoObj = JobDao.getInstance();
			return courseDaoObj.findAJobByJobId(jobId);
		}
	 
	 
	 
	 
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Path("/deleteJobByJobId")
		public Boolean deleteJobByJobIdService(@FormParam("jobId") int jobId) {
			JobDao jobDaoObj = JobDao.getInstance();
			return jobDaoObj.deletejOB(jobId);
		}
	 
	 
	 @PUT
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/updateJobByJobId")
		public Job updateJobByJobIdService(Job job) {
			JobDao jobDaoObj = JobDao.getInstance();
			return jobDaoObj.updateJob(job);
		}
	 
	
}
