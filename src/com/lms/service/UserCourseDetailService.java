package com.lms.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.UserCourseDetailDao;
import com.lms.model.User;
import com.lms.model.UserCourseDetail;
import com.lms.model.UserCourseRole;
import com.lms.util.UserCourseDetailUtil;

// /com.lms.servic/jwsUserCourseDetailService
@Path("/jwsUserCourseDetailService")
public class UserCourseDetailService {

	// /com.lms.servic/jwsUserCourseDetailService/createUserCourseDetailForTaService
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createUserCourseDetailForTa")
	public void createUserCourseDetailForTaService(List<UserCourseDetail> ucdList) {
		for(UserCourseDetail ucd : ucdList){
		//UserCourseDetail ucd = new UserCourseDetail(courseId, userId, roleName);
		UserCourseDetailDao userCourseDetailDaoObj = UserCourseDetailDao.getInstance();
		userCourseDetailDaoObj.createUserCourseDetail(ucd);
		}
	}
	
	// /com.lms.servic/jwsUserCourseDetailService/deleteUserCourseDetail

	/*@Path("/deleteUserCourseDetail/{courseId}/{userId}/{roleName}")
	public Boolean deleteUserCourseDetailService(
			@PathParam("courseId") int courseId,
			@PathParam("userId") int userId,
			@PathParam("roleName") String roleName) {
		UserCourseDetailDao ucd = UserCourseDetailDao.getInstance();
		return ucd.deleteUserCourseDetail(courseId, userId, roleName);
	}
*/
	// /com.lms.servic/jwsUserCourseDetailService/findByUserId

	@GET
	@Path("/findByUserId/{userId}")
	public List<UserCourseDetail> findByUserIdService(@PathParam("userId") int userId) {
		UserCourseDetailDao ucd = UserCourseDetailDao.getInstance();
		return ucd.findByUserId(userId);
	}
	
	// /com.lms.servic/jwsUserCourseDetailService/findByCourseId

	/*@Path("/findByCourseId/{courseId}")
	public List<UserCourseDetail> findByCourseIdService(@PathParam("courseId") int courseId) {
		UserCourseDetailDao ucd = UserCourseDetailDao.getInstance();
		return ucd.findByUserId(courseId);
	}*/
	
	@GET
	@Path("/findUserByRole/{role}")
	public List<User> findUserByRole(@PathParam("role") String role) {
		UserCourseDetailDao ucd = UserCourseDetailDao.getInstance();
		return UserCourseDetailUtil.getUsersFromUserCourseDetail(ucd.findByRole(role));
	}
	
	@GET
	@Path("/findCourseRoleByUserId/{userId}")
	public List<UserCourseRole> findCourseRoleByUserId(@PathParam("userId") int userId) {
		UserCourseDetailDao ucd = UserCourseDetailDao.getInstance();
		return UserCourseDetailUtil.getUserCourseRole(ucd.findByUserId(userId));
	}
	
}