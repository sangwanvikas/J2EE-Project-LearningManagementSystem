package com.lms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.UserCourseDetailDao;
import com.lms.dao.UserDao;
import com.lms.enumeration.Role;
import com.lms.model.User;
import com.lms.model.UserCourseDetail;

// /com.lms.servic/JWSUserService
@Path("/jwsUserService")
public class UserService {

	// /com.lms.servic/jwsUserService/createUser
/*
	@Path("/createUser/{userName}/{password}/{firstName}/{lastName}/{email}/{dateOfBirth}")
	public User createUserService(@PathParam("userName") String userName,
			@PathParam("password") String password,
			@PathParam("firstName") String firstName,
			@PathParam("lastName") String lastName,
			@PathParam("email") String email,
			@PathParam("dateOfBirth") String dateOfBirth) {
		User user = new User(userName, password, firstName, lastName, email,
				new Date(), null);
		UserDao userDaoObj = UserDao.getInstance();
		userDaoObj.createUser(user);
		return user;
	}*/

	@POST
	@Path("/createUser")
	public User createUserServiceWithRole(User user) {
		user.setDateOfBirth(new Date());
		List<UserCourseDetail> ucdList = user.getUserCourseDetail();
		user.setUserCourseDetail(new ArrayList<UserCourseDetail>());
		user = UserDao.getInstance().createUser(user);
		System.out.println(user);
		List<UserCourseDetail> ucdNewList = new ArrayList<UserCourseDetail>();
		for (UserCourseDetail ucd : ucdList) {
			UserCourseDetail ucdNew = new UserCourseDetail(ucd.getCourseId(),
					user.getUserId(), ucd.getRoleName());
			ucdNewList.add(UserCourseDetailDao.getInstance().createUserCourseDetail(ucdNew));
		}
		user.setUserCourseDetail(ucdList);
		return user;
	}

	// /com.lms.servic/jwsUserService/findUserByUserId

	/*@Path("/findUserByUserId/{id}")
	public User findUserByUserIdService(@PathParam("id") int id) {
		User user = new User();
		UserDao userDaoObj = UserDao.getInstance();
		user = userDaoObj.findUserByUserId(id);
		return user;
	}*/

	// /com.lms.servic/jwsUserService/deleteUser

	@DELETE
	@Path("/deleteUser/{id}")
	public Boolean deleteUserService(@PathParam("id") int id) {
		System.out.println("id" + id);
		UserDao userDaoObj = UserDao.getInstance();
		return userDaoObj.deleteUser(id);
	}

	@PUT
	@Path("/updateUser/{userId}/{userName}/{password}/{firstName}/{lastName}/{email}/{dateOfBirth}/{role}/{courses}")
	public User updateUserService(@PathParam("userId") int userId,
			@PathParam("userName") String userName,
			@PathParam("password") String password,
			@PathParam("firstName") String firstName,
			@PathParam("lastName") String lastName,
			@PathParam("email") String email,
			@PathParam("dateOfBirth") String dateOfBirth,
			@PathParam("role") String role, @PathParam("courses") String courses) {
		UserDao userDaoObj = UserDao.getInstance();
		User user = userDaoObj.findUserByUserId(userId);
		System.out.println(user);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setUserName(userName);
		user.setEmail(email);
		userDaoObj.updateUser(user);
		System.out.println(user);
		UserCourseDetailDao ucdDao = UserCourseDetailDao.getInstance();
		ucdDao.deleteByUserIdAndRole(userId, role);
		for (String id : courses.split(",")) {
			UserCourseDetail ucd = new UserCourseDetail(Integer.parseInt(id),
					user.getUserId(), role);
			UserCourseDetailDao.getInstance().createUserCourseDetail(ucd);
		}
		return user;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAllUsers")
	public List<User> findAllUsersService() {
		UserDao userDaoObj = UserDao.getInstance();
		return userDaoObj.findAllUsers();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAllUsersNotTAForACourse")
	public List<User> findAllUsersNotTAForACourseService(int courseId) {
		UserDao userDaoObj = UserDao.getInstance();
		return userDaoObj.findAllUsersNotTAForACourse(courseId);
	}

	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/UserFollowsAnotherUser")
	public User UserFollowsAnotherUserService(
			@FormParam("followerUserId") int followerUserId,
			@FormParam("followedUserId") int followedUserId) {
		UserDao userDaoObj = UserDao.getInstance();
		User followerUserObj = userDaoObj.findUserByUserId(followerUserId);
		System.out.println("service before dao call1" + followerUserObj);
		List<User> existingFollowedUserList = followerUserObj
				.getFollowedUsersList();
		User followedUserObj = userDaoObj.findUserByUserId(followedUserId);
		System.out.println("service before dao call2" + followedUserObj);
		existingFollowedUserList.add(followedUserObj);
		followerUserObj.setFollowedUsersList(existingFollowedUserList);
		return userDaoObj.updateUser(followerUserObj);
		// return followerUserObj;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/UserUnFollowsAnotherUser")
	public User UserUnFollowsAnotherUserService(
			@FormParam("followerUserId") int followerUserId,
			@FormParam("followedUserId") int followedUserIdToBeRemoved) {

		System.out.println(followerUserId + "_" + followedUserIdToBeRemoved);

		UserDao userDaoObj = UserDao.getInstance();
		User followerUserObj = userDaoObj.findUserByUserId(followerUserId);
		System.out.println("service before dao call1" + followerUserObj);

		List<User> usersWhoHaveAlreadyFollowedUsers = new CopyOnWriteArrayList<User>(
				followerUserObj.getFollowedUsersList());

		for (User u : usersWhoHaveAlreadyFollowedUsers) {
			if (u.getUserId() == followedUserIdToBeRemoved) {
				usersWhoHaveAlreadyFollowedUsers.remove(u);
			}
		}

		followerUserObj.setFollowedUsersList(usersWhoHaveAlreadyFollowedUsers);
		return userDaoObj.updateUser(followerUserObj);
	}

	@PUT	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateCourseForStudent")
	public List<User> updateCourseForStudent(User user){
		UserCourseDetailDao ucdDao = UserCourseDetailDao.getInstance();
		ucdDao.deleteByUserIdAndRole(user.getUserId(), Role.STUDENT.toString());

		for (UserCourseDetail ucd : user.getUserCourseDetail()) {
			UserCourseDetailDao.getInstance().createUserCourseDetail(ucd);
		}
		
		return UserDao.getInstance().findAllUsers();
	}
}
