package com.lms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.lms.model.Tag;
import com.lms.model.User;

public class TagDao {
	
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LMS");
	static EntityManager em = null;
	
	private TagDao() {
		em = emf.createEntityManager();
	}
	
	private static TagDao _instance = null;
	
	public static TagDao getInstance(){
		if(_instance == null){
			_instance = new TagDao();
		}
		if(em != null)
			em.close();
		em = emf.createEntityManager();
		return _instance;
	}
	
	public List<Tag> findAllTags(){
		List<Tag> tags = null;
		em.getTransaction().begin();
		Query query = em.createNamedQuery("Tag.findAll", Tag.class);
		tags = query.getResultList();
		em.getTransaction().commit();
		return tags;
	}

	public Tag findTagByTagId(int tagId){
		Tag tag = new Tag();
		em.getTransaction().begin();
		tag = em.find(Tag.class, tagId);
		em.getTransaction().commit();
		return tag;
	}

	public Tag createTag(Tag tag) {
		em.getTransaction().begin();
		em.persist(tag);
		em.getTransaction().commit();
		return tag;
	}
	
}
