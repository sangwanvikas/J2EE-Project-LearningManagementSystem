package com.lms.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lms.dao.ThreadDao;
import com.lms.dao.UserDao;
import com.lms.model.Thread;
import com.lms.model.User;
import com.mysql.fabric.xmlrpc.base.Array;

@Path("/jwsThreadService")
public class ThreadService {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findThreadsByCourseId/{courseId}")
	public List<Thread> findThreadsByCourseIdService(@PathParam("courseId") int courseId) {		
		ThreadDao courseDaoObj = ThreadDao.getInstance();
		return courseDaoObj.findThreadsByCourseId(courseId);
	}
	
	
/*	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAllThreads")
	public List<Thread> findAllThreads() {		
		ThreadDao courseDaoObj = ThreadDao.getInstance();
		return courseDaoObj.findAllThreads();
	}*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAllThreadsByAKeyWord/{courseId}/{keyword}")
	public List<Thread> findAllThreadsByAKeyWordService (
			@PathParam("courseId") int courseId,
			@PathParam("keyword") String keyword) {				
		ThreadDao courseDaoObj = ThreadDao.getInstance();
		return courseDaoObj.findAllThreadsByAKeyWord(courseId,keyword);
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAThreadByThreadId/{threadId}")
	public Thread findAThreadByThreadIdService(@PathParam ("threadId") int threadId) {		
		ThreadDao courseDaoObj = ThreadDao.getInstance();
		System.out.println( courseDaoObj.findAThreadByThreadId(threadId));
		return courseDaoObj.findAThreadByThreadId(threadId);
	}
	
	@POST
	@Path("/createThread") 
	public Thread createThread(Thread thread){
		thread.setThreadTime(new Time(new Date().getTime()));
		thread.setThreadDate(new Date());
		ThreadDao.getInstance().createThread(thread);
		return thread;
	}
	
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/setFavThreadForUser")
	 public Thread setFavThreadForUser(Thread thread){		 
		 System.out.println(thread.getUserId() + "_" + thread.getThreadId());
		 ThreadDao threadDao = ThreadDao.getInstance(); 
		 User user = UserDao.getInstance().findUserByUserId(thread.getUserId());
		 Thread threadNew = threadDao.findAThreadByThreadId(thread.getThreadId());	
		 List<User> userList=null;
		 if(threadNew.getFavUsers() == null)
			 userList = new ArrayList<User>();
		 else
			 userList=threadNew.getFavUsers();
		 userList.add(user);
		 threadNew.setFavUsers(userList);
		 threadNew = ThreadDao.getInstance().updateThread(threadNew);
		 return threadNew;
	 }
	 
	 @DELETE
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Path("/undoFavThreadForUser")
	 public Thread undoFavThreadForUser(Thread thread){
		 ThreadDao threadDao = ThreadDao.getInstance(); 
		 Thread threadNew = threadDao.findAThreadByThreadId(thread.getThreadId());
		 List<User> userList = new CopyOnWriteArrayList<User>(threadNew.getFavUsers());
		 for(User user : userList){
			if(user.getUserId() == thread.getUserId())
				userList.remove(user);
		 }
		 threadNew.setFavUsers(userList);
		 threadNew = ThreadDao.getInstance().updateThread(threadNew);
		 
		 return threadNew;
	 }
	 
}
