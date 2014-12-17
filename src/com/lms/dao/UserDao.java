package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.User;
import com.lms.model.UserCourseDetail;

public class UserDao {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	private UserDao() {
		em = emf.createEntityManager();
	}
	
	private static UserDao _instance = null;
	
	public static UserDao getInstance(){
		if(_instance == null){
			_instance = new UserDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public User createUser(User user){
		
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		
		return user;
	}

	public User findUserByUserId(int userId){
		User user = new User();
		em.getTransaction().begin();
		user = em.find(User.class, userId);
		em.getTransaction().commit();
		return user;
	}
		
	
	public Boolean deleteUser(int userId){
		em.getTransaction().begin();
		User user = em.find (User.class, userId);
		System.out.println("delete:" + user);
		em.remove(user);
		em.getTransaction().commit();
		return true;
	}
	
	public User updateUser(User user){
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		return user;
	}
	
	public List<User> findAllUsers() {
		Query query = em.createQuery("select user from User user");
		List<User> result = query.getResultList();
		return result;
	}

	public List<User> findAllUsersNotTAForACourse(int courseId) {
		em.getTransaction().begin();
        Query query = em.createQuery(" select u from User u where u.userId IN "
        		+ " (select ucd.userId from UserCourseDetail ucd where ucd.courseId != :courseId AND  ucd.roleName='STUDENT')"
        		+ " AND "
        		+ " u.userId NOT IN (select ucd.userId from UserCourseDetail ucd where ucd.courseId = :courseId AND  ucd.roleName ='TA')");
		query.setParameter("courseId", courseId);
		List<User> result = query.getResultList();
		em.getTransaction().commit();
		return result;
	}
	
}
