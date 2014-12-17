package com.lms.model;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entity implementation class for Entity: UserCourseDetail
 *
 */
@Entity
@Table(name="user_course_detail")
@Cacheable(false)
@IdClass(UserCourseDetailPK.class)
public class UserCourseDetail implements Serializable {

	@Id
	@Column(name="course_id")
	private int courseId;   
	@Id
	@Column(name="user_id")
	private int userId;   
	@Id
	@Column(name="role")
	private String roleName;
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="course_id", referencedColumnName= "course_id", updatable=false, insertable=false)
	private Course course;
	
	@JsonIgnore
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName= "user_id", updatable=false, insertable=false)
	private User user;
	
	public UserCourseDetail() {
		super();
	}   
	
	public UserCourseDetail(int courseId, int userid, String roleName) {
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
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userid) {
		this.userId = userid;
	}   
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserCourseDetail [courseId=");
		builder.append(courseId);
		builder.append(", userid=");
		builder.append(userId);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", course=");
		builder.append(course == null? null : course.getCourseName());
		builder.append(", user first name=");
		builder.append(user == null? null : user.getFirstName());
		builder.append(", user last name=");
		builder.append(user == null? null : user.getLastName());
		builder.append("]");
		return builder.toString();
	}   
   
}
