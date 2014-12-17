package com.lms.model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entity implementation class for Entity: Course
 *
 */

@Entity
@Table(name="course")
public class Course implements Serializable {
	   
	@Id
	@Column(name="course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;
	@Column(name="course_name")
	private String courseName;
	
	//@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="course_id", referencedColumnName = "course_id")
	private List<UserCourseDetail> userCourseDetail;
	
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="course_id", referencedColumnName="course_id")
	private List<CourseSchedule> courseScheduleList;

	private static final long serialVersionUID = 1L;

	public Course() {
		super();
	}   
	
	public Course(String courseName) {
		super();
		this.courseName = courseName;
		
	}
	
	public Course(String courseName, List<CourseSchedule> courseScheduleList) {
		super();
		this.courseName = courseName;
		
		this.courseScheduleList=courseScheduleList;
	}
	
	public Course(int courseId, String courseName,List<CourseSchedule> courseScheduleList) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		
		this.courseScheduleList=courseScheduleList;
	}

	public Course(int courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}   
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}   
	
   
	public List<UserCourseDetail> getUserCourseDetail() {
		return userCourseDetail;
	}

	public void setUserCourseDetail(List<UserCourseDetail> userCourseDetail) {
		this.userCourseDetail = userCourseDetail;
	}
	
	public List<CourseSchedule> getCourseScheduleList() {
		return courseScheduleList;
	}

	
	public void setCourseScheduleList(List<CourseSchedule> courseScheduleList) {
		this.courseScheduleList = courseScheduleList;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Course [courseId=");
		builder.append(courseId);
		builder.append(", courseName=");
		builder.append(courseName);
		builder.append(", userCourseDetail=");
		builder.append(userCourseDetail);
		builder.append(", courseScheduleList=");
		builder.append(courseScheduleList);
		builder.append("]");
		return builder.toString();
	}
	
}
