package com.lms.model;

public class UserCourseRole {

	private User user;
	private Course course;
	private String roleName;
	
	public UserCourseRole() {}
	
	public UserCourseRole(User user, Course course, String roleName) {
		super();
		this.user = user;
		this.course = course;
		this.roleName = roleName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
