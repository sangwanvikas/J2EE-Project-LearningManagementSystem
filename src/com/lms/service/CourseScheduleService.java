package com.lms.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.CourseDao;
import com.lms.dao.CourseScheduleDao;
import com.lms.model.Course;
import com.lms.model.CourseSchedule;


// /com.lms.service/jwsCourseScheduleService
@Path("/jwsCourseScheduleService")
public class CourseScheduleService {
	
	
	
	// /com.lms.service/jwsCourseScheduleService/createCourseSchedule
 /*   @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/createCourseSchedule")
	public List<CourseSchedule> createCourseScheduleService(List<CourseSchedule> courseScheduleList) {
    	System.out.println("createCourseScheduleService-- \n" + courseScheduleList);
    	  for (CourseSchedule cs  : courseScheduleList) {
    		  CourseScheduleDao courseScheduleDaoObj = CourseScheduleDao.getInstance();
    			courseScheduleDaoObj.createCourseSchedule(cs);
    	      }
		return courseScheduleList;
	}*/
    
	
/*	
	// /com.lms.service/jwsCourseScheduleService/createCourseSchedule
    @GET
	@Path("/createCourseSchedule/{courseId}/{courseLocation}/{day}/{courseStartDate}/{courseEndDate}/{CourseStartTime}/{CourseEndTime}")
	public CourseSchedule createCourseSchedule(@PathParam("courseId") int courseId,
			@PathParam("courseLocation") String courseLocation,
			@PathParam("day") String day,
			@PathParam("courseStartDate") String courseStartDate,
			@PathParam("courseEndDate") String courseEndDate,
			@PathParam("CourseStartTime") String CourseStartTime,
			@PathParam("CourseEndTime") String CourseEndTime) {
		CourseSchedule courseSchedule =new CourseSchedule(new Date(), new Date(), courseId, courseLocation, new Date(), new Date(), day);
		CourseScheduleDao courseScheduleDaoObj = new CourseScheduleDao();
		courseScheduleDaoObj.createCourseSchedule(courseSchedule);
		return courseSchedule;
	}
    */
    
    
/*	@Path("/findCourseScheduleBycourseId/{id}")
	public CourseSchedule findCourseScheduleBycourseId(@PathParam("id") int id) {
		CourseSchedule course =new CourseSchedule();
		CourseScheduleDao courseScheduleDaoObj = new CourseScheduleDao();
		course=courseScheduleDaoObj.findCourseScheduleBycourseId(id);
		return course;
	}*/
	
	
	// /com.lms.service/jwsCourseService/deleteCourse

/*	@Path("/deleteCourseSchedule/{id}")
	public Boolean deleteCourseScheduleService(@PathParam("id") int id) {
		CourseScheduleDao courseScheduleDaoObj = CourseScheduleDao.getInstance();
		return courseScheduleDaoObj.deleteCourseSchedule(id);
	}*/

	// /com.lms.service/jwsCourseService/findAllCourses
/*@GET
	@Path("/findAllCourseSchedule")
	public List<CourseSchedule> findAlleCoursesService() {
		CourseScheduleDao courseScheduleDaoObj = CourseScheduleDao.getInstance();
		 return courseScheduleDaoObj.findAllCourseSchedule();
	}*/

}
