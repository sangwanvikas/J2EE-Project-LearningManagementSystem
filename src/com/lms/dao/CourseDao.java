package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.Course;
import com.lms.model.CourseSchedule;
import com.lms.model.User;
/*import com.lms.model.User;
import com.lms.model.UserCourseDetail;*/

public class CourseDao {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	
	private CourseDao() {
		em = emf.createEntityManager();
	}
	
	private static CourseDao _instance = null;
	
	public static CourseDao getInstance(){
		if(_instance == null){
			_instance = new CourseDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public Course createCourse(Course course){
		
		em.getTransaction().begin();
		em.persist(course);
		em.getTransaction().commit();
		
		return course;
	}

	public Course findCourseById(int courseId){
		em.getTransaction().begin();
		Course course = em.find(Course.class, courseId);
		em.getTransaction().commit();
		return course;
	}
	
	public Boolean deleteCourse(int courseId){
		em.getTransaction().begin();
		Course course = em.find (Course.class, courseId);
		em.remove(course);
		em.getTransaction().commit();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> findAllCourses() {
		Query query = em.createQuery("select course from Course course");
		List<Course> result = query.getResultList();
		return result;
	}
	
	public List<Course> findAllCoursesForAProf(int userId) {
		Query query = em.createQuery("select course from Course course where course.courseId IN ( select distinct ucd.courseId from UserCourseDetail ucd where ucd.roleName like 'PROFESSOR' and ucd.userId =:userId)");
		query.setParameter("userId", userId);
		List<Course> result = query.getResultList();
		return result;
	}
	
	
	
	
	
	public void updateCourse(Course course){
		System.out.println("updateCourse*******************"+course);
		em.getTransaction().begin();
		em.merge(course);
		em.getTransaction().commit();
		
		
		System.out.println(course.getCourseName());
		System.out.println(course.getCourseId());
		System.out.println(course); 
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Course> findAllCoursesForAUserId(int userId) {
		Query query = em.createQuery("select course from Course course where course.courseId IN ( select distinct ucd.courseId from UserCourseDetail ucd where ucd.userId =:userId)");
		query.setParameter("userId", userId);
		List<Course> result = query.getResultList();
		return result;
	}
	
	
	
	public List<Course> findAllCoursesForATa(int userId) {
		Query query = em.createQuery("select course from Course course where course.courseId IN ( select distinct ucd.courseId from UserCourseDetail ucd where ucd.roleName like 'TA' and ucd.userId =:userId)");
		query.setParameter("userId", userId);
		List<Course> result = query.getResultList();
		return result;
	}
	
	
	/*CourseSchedule courseScheduleObj=em.find(CourseSchedule.class, courseObj.getCourseId());
	courseScheduleObj.setCourseEndDate(course);
	courseScheduleObj.setCourseEndTime(courseEndTime);
	courseScheduleObj.setCourseLocation(courseLocation);
	courseScheduleObj.setCourseStartDate(courseStartDate);
	courseScheduleObj.setCourseStartTime(courseStartTime);
	courseScheduleObj.setDay(day);*/
	
	
	
}
