package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.Job;
import com.lms.model.Thread;
import com.lms.model.User;

public class JobDao {
	
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	private JobDao() {
		em = emf.createEntityManager();
	}
	
	private static JobDao _instance = null;
	
	public static JobDao getInstance(){
		if(_instance == null){
			_instance = new JobDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public Job createJob(Job job){
		
		em.getTransaction().begin();
		em.persist(job);
		em.getTransaction().commit();
		
		return job;
	}
	
	
	public List<Job> findAllJobs() {
		Query query = em.createQuery("select job from Job job");
		List<Job> jobsList = query.getResultList();
		return jobsList;
	}

	
	public List<Job> findJobsByProfessorId(int userId) {
		Query query = em.createQuery("select job from Job job where userId =:userId");
		query.setParameter("userId", userId);
		List<Job> result = query.getResultList();
		return result;	
	}
	
	public List<Job> findJobsByProfessorIdAndCourseId(int userId,int courseId) {
		Query query = em.createQuery("select job from Job job where job.userId =:userId and job.courseId =:courseId");
		query.setParameter("userId", userId);
		query.setParameter("courseId", courseId);
		List<Job> result = query.getResultList();
		System.out.println(result);
		return result;	
	}
	
	public List<Job> findJobsByCourseId(int courseId) {
		Query query = em.createQuery("select job from Job job where job.courseId =:courseId");
		query.setParameter("courseId", courseId);
		List<Job> result = query.getResultList();
		System.out.println(result);
		return result;	
	}
	
	public Job findAJobByJobId(int jobId){
		Job job = new Job();
		em.getTransaction().begin();
		job = em.find(Job.class, jobId);
		em.getTransaction().commit();
		return job;
	}
	
	//
	
	public Boolean deletejOB(int jobId){
		em.getTransaction().begin();
		Job job = em.find (Job.class, jobId);
		em.remove(job);
		em.getTransaction().commit();
		return true;
	}
	
	public Job updateJob(Job job){
		em.getTransaction().begin();
		em.merge(job);
		em.getTransaction().commit();
		return job;
	}
	
	

}
