package com.lms.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;
;


/**
 * The persistent class for the ta_hours database table.
 * 
 */
@Entity
@Table(name="ta_hours")
@NamedQuery(name="TaHour.findAll", query="SELECT t FROM TaHour t")
public class TaHour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ta_hour_id")
	private int taHourId;

	@Column(name="course_id")
	private int courseId;

	@Column(name="ta_hour_day")
	private String taHourDay;

	@Temporal(TemporalType.DATE)
	@Column(name="ta_hour_end_time")
	private Date taHourEndTime;

	@Column(name="ta_hour_location")
	private String taHourLocation;

	@Temporal(TemporalType.DATE)
	@Column(name="ta_hour_start_time")
	private Date taHourStartTime;

	@Column(name="ta_id")
	private int taId;
	
	@JsonIgnore
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="ta_id", referencedColumnName= "user_id", updatable=false, insertable=false)
	private User user;
	
	
	@JsonIgnore
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="course_id", referencedColumnName= "course_id", updatable=false, insertable=false)
	private Course course;


	public TaHour() {
	}

	
	
	public TaHour(int courseId, String taHourDay, Date taHourEndTime,
			String taHourLocation, Date taHourStartTime, int taId, User user) {
		super();
		this.courseId = courseId;
		this.taHourDay = taHourDay;
		this.taHourEndTime = taHourEndTime;
		this.taHourLocation = taHourLocation;
		this.taHourStartTime = taHourStartTime;
		this.taId = taId;
		this.user = user;
	}



	public TaHour(int courseId, String taHourDay, Date taHourEndTime,
			String taHourLocation, Date taHourStartTime, int taId) {
		super();
		this.courseId = courseId;
		this.taHourDay = taHourDay;
		this.taHourEndTime = taHourEndTime;
		this.taHourLocation = taHourLocation;
		this.taHourStartTime = taHourStartTime;
		this.taId = taId;
	}



	public TaHour(int taHourId, int courseId, String taHourDay,
			Date taHourEndTime, String taHourLocation, Date taHourStartTime,
			int taId, User user) {
		super();
		this.taHourId = taHourId;
		this.courseId = courseId;
		this.taHourDay = taHourDay;
		this.taHourEndTime = taHourEndTime;
		this.taHourLocation = taHourLocation;
		this.taHourStartTime = taHourStartTime;
		this.taId = taId;
		this.user = user;
	}



	public int getTaHourId() {
		return this.taHourId;
	}

	public void setTaHourId(int taHourId) {
		this.taHourId = taHourId;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getTaHourDay() {
		return this.taHourDay;
	}

	public void setTaHourDay(String taHourDay) {
		this.taHourDay = taHourDay;
	}

	public Date getTaHourEndTime() {
		return this.taHourEndTime;
	}

	public void setTaHourEndTime(Date taHourEndTime) {
		this.taHourEndTime = taHourEndTime;
	}

	public String getTaHourLocation() {
		return this.taHourLocation;
	}

	public void setTaHourLocation(String taHourLocation) {
		this.taHourLocation = taHourLocation;
	}

	public Date getTaHourStartTime() {
		return this.taHourStartTime;
	}

	public void setTaHourStartTime(Date taHourStartTime) {
		this.taHourStartTime = taHourStartTime;
	}

	public int getTaId() {
		return this.taId;
	}

	public void setTaId(int taId) {
		this.taId = taId;
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

	@Override
	public String toString() {
		return "TaHour [taHourId=" + taHourId + ", courseId=" + courseId
				+ ", taHourDay=" + taHourDay + ", taHourEndTime="
				+ taHourEndTime + ", taHourLocation=" + taHourLocation
				+ ", taHourStartTime=" + taHourStartTime + ", taId=" + taId
				+ ", user=" + user + ", course=" + course + "]";
	}

	
}