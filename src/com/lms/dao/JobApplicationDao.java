package com.lms.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.lms.model.JobApplication;

public class JobApplicationDao {
	
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	
	private JobApplicationDao() {
		em = emf.createEntityManager();
	}
	
	private static JobApplicationDao _instance = null;
	
	public static JobApplicationDao getInstance(){
		if(_instance == null){
			_instance = new JobApplicationDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public JobApplication createJobApplication(JobApplication jobApplicationObj){		
		em.getTransaction().begin();
		em.persist(jobApplicationObj);
		em.getTransaction().commit();		
		return jobApplicationObj;
	}
	
	

}
