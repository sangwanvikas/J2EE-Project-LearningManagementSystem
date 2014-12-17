package com.lms.model;

import java.io.Serializable;
import java.lang.String;

/**
 * ID class for entity: UserCourseDetail
 *
 */ 

public class UserCourseDetailPK  implements Serializable {   
	         
	private int courseId;         
	private int userId;         
	private String roleName;
	private static final long serialVersionUID = 1L;

	public UserCourseDetailPK() {}

	public UserCourseDetailPK(int courseId, int userid, String roleName) {
		super();
		this.courseId = courseId;
		this.userId = userid;
		this.roleName = roleName;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	

	public int getUserid() {
		return this.userId;
	}

	public void setUserid(int userid) {
		this.userId = userid;
	}
	
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UserCourseDetailPK)) {
			return false;
		}
		UserCourseDetailPK other = (UserCourseDetailPK) o;
		return true
			&& getCourseId() == other.getCourseId()
			&& getUserid() == other.getUserid()
			&& (getRoleName() == null ? other.getRoleName() == null : getRoleName().equals(other.getRoleName()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getCourseId();
		result = prime * result + getUserid();
		result = prime * result + (getRoleName() == null ? 0 : getRoleName().hashCode());
		return result;
	}
   
   
}
