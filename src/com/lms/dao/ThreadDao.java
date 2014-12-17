package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.Thread;

public class ThreadDao {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	
	private ThreadDao() {
		em = emf.createEntityManager();
	}
	
	private static ThreadDao _instance = null;
	
	public static ThreadDao getInstance(){
		if(_instance == null){
			_instance = new ThreadDao();
		}
		
		if(em != null){
			em.close();
		}
		em = emf.createEntityManager();
		
		return _instance;
	}
	
	public Thread createThread(Thread thread){		
		em.getTransaction().begin();
		em.persist(thread);
		em.getTransaction().commit();		
		return thread;
	}
	
	
	public List<Thread> findAllThreads(){
		List<Thread> threadList = null;
		em.getTransaction().begin();
		Query query = em.createQuery("select thread from Thread thread");
		threadList = query.getResultList();
		em.getTransaction().commit();
		return threadList;
	}
	
	public Thread findAThreadByThreadId(int threadId){
		Thread thread = new Thread();
		em.getTransaction().begin();
		thread = em.find(Thread.class, threadId);
		em.getTransaction().commit();
		return thread;
	}
	
	public List<Thread> findAllThreadsByAKeyWord(int courseId, String keyword){
		List<Thread> threadList = null;
		em.getTransaction().begin();
		
		Query query = em.createQuery("select thread from Thread thread where thread.courseId =:courseId and ( thread.threadContent like :keyword or thread.threadTitle like :keyword)");
		query.setParameter("keyword", "%" + keyword + "%");
		query.setParameter("courseId", courseId);
		threadList = query.getResultList();
		em.getTransaction().commit();
		return threadList;
		
	}
	
	public Boolean deleteThread(int threadId){
		em.getTransaction().begin();
		Thread thread = em.find (Thread.class, threadId);
		System.out.println("delete:" + thread);
		em.remove(thread);
		em.getTransaction().commit();
		return true;
	}
	
	public Thread updateThread(Thread thread){
		em.getTransaction().begin();
		em.merge(thread);
		em.getTransaction().commit();
		return thread;
	}
	
	public List<Thread> findThreadByUserId(int userId){
		List<Thread> threadList = null;
		em.getTransaction().begin();
		Query query = em.createQuery("select thread from Thread thread where thread.userId = :userId");
		query.setParameter("userId", userId);
		threadList = query.getResultList();
		em.getTransaction().commit();
		return threadList;
	}
	
	public List<Thread> findThreadsByCourseId(int courseId){
		List<Thread> threadList = null;
		em.getTransaction().begin();
		Query query = em.createQuery("select thread from Thread thread where thread.courseId = :courseId");
		query.setParameter("courseId", courseId);
		threadList = query.getResultList();
		em.getTransaction().commit();
		return threadList;
	}
	
}
