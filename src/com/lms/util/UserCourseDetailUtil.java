package com.lms.util;

import java.util.ArrayList;
import java.util.List;

import com.lms.dao.CourseDao;
import com.lms.dao.UserDao;
import com.lms.model.User;
import com.lms.model.UserCourseDetail;
import com.lms.model.UserCourseRole;

public final class UserCourseDetailUtil {

	private UserCourseDetailUtil(){}
	
	public static List<User> getUsersFromUserCourseDetail(List<UserCourseDetail> ucdList){
		List<User> userList = new ArrayList<User>();
		for(UserCourseDetail ucd : ucdList){
			userList.add(ucd.getUser());
		}
		return userList;
	}

	public static List<UserCourseRole> getUserCourseRole(List<UserCourseDetail> ucdList) {
		List<UserCourseRole> ucrList = new ArrayList<UserCourseRole>();
		for(UserCourseDetail ucd : ucdList){
			ucrList.add(new UserCourseRole(
							ucd.getUser(),
							ucd.getCourse(),
							ucd.getRoleName()));
		}
		return ucrList;
	}

}
