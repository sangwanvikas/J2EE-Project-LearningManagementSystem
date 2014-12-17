package com.lms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.JobApplicationDao;
import com.lms.dao.TaHoursDao;
import com.lms.model.JobApplication;
import com.lms.model.TaHour;
@Path("/jwsTaHours")
public class TaHoursService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createTaHours")
	public TaHour createTaHoursService(TaHour taHour) {
		TaHoursDao taHourDaoObj = TaHoursDao.getInstance();
		taHourDaoObj.createTaHours(taHour);
		return taHour;
	}
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findTaHoursByCourseId/{courseId}")
	public List<TaHour> findTaHoursByCourseIdService(@PathParam("courseId") int courseId){
		TaHoursDao taHourDaoObj = TaHoursDao.getInstance();
		List<TaHour> taHourList = taHourDaoObj.findTaHoursByCourseId(courseId);
		return taHourList;
	}
	
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteTaHoursByTaHourId")
	public Boolean deleteTaHoursByTaHourIdService(int taHourId){
		TaHoursDao taHourDaoObj = TaHoursDao.getInstance();
		return taHourDaoObj.deleteTaHoursByTaHourId(taHourId);
		
	}
	

	@PUT 
	@Path("/updateExistingTaHour/{taHourId}") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public TaHour updateExistingTaHourService(@PathParam("taHourId") int taHourId, TaHour taHour) {
		TaHoursDao taHourDaoObj = TaHoursDao.getInstance();
		return taHourDaoObj.updateExistingTaHour(taHourId,taHour);
	}
	

}
