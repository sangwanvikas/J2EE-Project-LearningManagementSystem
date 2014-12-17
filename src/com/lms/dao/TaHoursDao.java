package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.Course;
import com.lms.model.JobApplication;
import com.lms.model.TaHour;

public class TaHoursDao {
	
	
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	
	private TaHoursDao() {
		em = emf.createEntityManager();
	}	
	
	private static TaHoursDao _instance = null;
	
	public static TaHoursDao getInstance(){
		if(_instance == null){
			_instance = new TaHoursDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public TaHour createTaHours(TaHour taHour){		
		em.getTransaction().begin();
		em.persist(taHour);
		em.getTransaction().commit();		
		return taHour;
	}
	
	public List<TaHour> findTaHoursByCourseId(int courseId){		
		em.getTransaction().begin();
		Query query = em.createQuery("select tahour from TaHour tahour where tahour.courseId =:courseId");
		query.setParameter("courseId", courseId);
		List<TaHour> taHourList = query.getResultList();
		em.getTransaction().commit();
		return taHourList;
	}
	
	public Boolean deleteTaHoursByTaHourId(int taHourId){		
		em.getTransaction().begin();
		TaHour taHour = em.find (TaHour.class, taHourId);
		em.remove(taHour);
		em.getTransaction().commit();
		return true;
	}
	
	public TaHour updateExistingTaHour(int taHourId,TaHour taHour){
			em.getTransaction().begin();
			System.out.println(taHour);
			taHour.setTaHourId(taHourId);
			em.merge(taHour);
		
			em.getTransaction().commit();
			//System.out.println(thHour);
			return taHour;
	}
	
	
	

}
