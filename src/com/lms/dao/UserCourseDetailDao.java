package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.UserCourseDetail;
import com.lms.model.UserCourseDetailPK;

public class UserCourseDetailDao {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	private UserCourseDetailDao() {
		em = emf.createEntityManager();
	}
	
	private static UserCourseDetailDao _instance = null;
	
	public static UserCourseDetailDao getInstance(){
		if(_instance == null){
			_instance = new UserCourseDetailDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public UserCourseDetail createUserCourseDetail(UserCourseDetail ucd){
		em.getTransaction().begin();
		em.persist(ucd);
		em.getTransaction().commit();
		em.refresh(ucd);
		return ucd;
	}
	
	public Boolean deleteUserCourseDetail(int courseId, int userId, String roleName){
		em.getTransaction().begin();
		em.remove(em.find(UserCourseDetail.class, new UserCourseDetailPK(courseId, userId, roleName)));
		em.getTransaction().commit();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourseDetail> findByUserId(int userId){
		em.getTransaction().begin();
		Query query = em.createQuery("select ucd from UserCourseDetail ucd where ucd.userId = :userId");
		query.setParameter("userId", userId);
		List<UserCourseDetail> result = query.getResultList();
		em.getTransaction().commit();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourseDetail> findByCourseId(int courseId){
		em.getTransaction().begin();
		Query query = em.createQuery("select ucd from UserCourseDetail ucd where ucd.courseId = :courseId");
		query.setParameter("courseId", courseId);
		List<UserCourseDetail> result = query.getResultList();
		em.getTransaction().commit();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourseDetail> findByRole(String role){
		em.getTransaction().begin();
		Query query = em.createQuery("select ucd from UserCourseDetail ucd where ucd.roleName = :role");
		query.setParameter("role", role);
		List<UserCourseDetail> result = query.getResultList();
		em.getTransaction().commit();
		return result;
	}
	
	public int deleteByUserIdAndRole(int userId, String role){
		em.getTransaction().begin();
		Query query = em.createQuery("delete from UserCourseDetail ucd where ucd.roleName = :role and ucd.userId = :userId");
		query.setParameter("role", role);
		query.setParameter("userId", userId);
		int deletedCount = query.executeUpdate();
		em.getTransaction().commit();
		System.out.println("Deleted " + deletedCount + " records");
		return deletedCount;
	}
	
}
