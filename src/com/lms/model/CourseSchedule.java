package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the course_schedule database table.
 * 
 */
@Entity
@Table(name="course_schedule")
//@NamedQuery(name="CourseSchedule.findAll", query="SELECT c FROM CourseSchedule c")
public class CourseSchedule implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="course_end_date")
	private Date courseEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="course_end_time")
	private Date courseEndTime;

	@Column(name="course_id")
	private int courseId;

	@Column(name="course_location")
	private String courseLocation;

	@Temporal(TemporalType.DATE)
	@Column(name="course_start_date")
	private Date courseStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name="course_start_time")
	private Date courseStartTime;

	private String day;

	public CourseSchedule() {
	}
	
	public CourseSchedule(Date courseEndDate, Date courseEndTime, int courseId,
			String courseLocation, Date courseStartDate, Date courseStartTime,
			String day) {
		super();
		this.courseEndDate = courseEndDate;
		this.courseEndTime = courseEndTime;
		this.courseId = courseId;
		this.courseLocation = courseLocation;
		this.courseStartDate = courseStartDate;
		this.courseStartTime = courseStartTime;
		this.day = day;
	}
	
	public CourseSchedule(Date courseEndDate, Date courseEndTime,
			String courseLocation, Date courseStartDate, Date courseStartTime,
			String day) {
		super();
		this.courseEndDate = courseEndDate;
		this.courseEndTime = courseEndTime;
		
		this.courseLocation = courseLocation;
		this.courseStartDate = courseStartDate;
		this.courseStartTime = courseStartTime;
		this.day = day;
	}

	public CourseSchedule(int id, Date courseEndDate, Date courseEndTime,
			int courseId, String courseLocation, Date courseStartDate,
			Date courseStartTime, String day) {
		super();
		this.id = id;
		this.courseEndDate = courseEndDate;
		this.courseEndTime = courseEndTime;
		this.courseId = courseId;
		this.courseLocation = courseLocation;
		this.courseStartDate = courseStartDate;
		this.courseStartTime = courseStartTime;
		this.day = day;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCourseEndDate() {
		return this.courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public Date getCourseEndTime() {
		return this.courseEndTime;
	}

	public void setCourseEndTime(Date courseEndTime) {
		this.courseEndTime = courseEndTime;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseLocation() {
		return this.courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	public Date getCourseStartDate() {
		return this.courseStartDate;
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Date getCourseStartTime() {
		return this.courseStartTime;
	}

	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "CourseSchedule [id=" + id + ", courseEndDate=" + courseEndDate
				+ ", courseEndTime=" + courseEndTime + ", courseId=" + courseId
				+ ", courseLocation=" + courseLocation + ", courseStartDate="
				+ courseStartDate + ", courseStartTime=" + courseStartTime
				+ ", day=" + day + "]";
	}

}