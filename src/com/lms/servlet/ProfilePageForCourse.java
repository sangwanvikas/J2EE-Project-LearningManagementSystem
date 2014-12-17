package com.lms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dao.JobDao;
import com.lms.dao.UserDao;
import com.lms.model.Post;
import com.lms.model.Thread;
import com.lms.model.User;

/**
 * Servlet implementation class ProfilePageForCourse
 */
@WebServlet("/ProfilePageForCourse")
public class ProfilePageForCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	resp.sendRedirect("/LMS/jsp/user/user-login.jsp");
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		String role = request.getParameter("role");

		User user = UserDao.getInstance().findUserByUserId(userId);
		
		if(courseId != 0){
			List<Thread> threads = new ArrayList<Thread>();
			List<Thread> favThreads = new ArrayList<Thread>();
			List<Post> posts = new ArrayList<Post>();
			List<Post> likedPosts = new ArrayList<Post>();
			
			for(Thread thread : user.getThreads()){
				if(thread.getCourseId() == courseId)
					threads.add(thread);
			}
			
			for(Thread thread : user.getFavThreads()){
				if(thread.getCourseId() == courseId)
					favThreads.add(thread);
			}
			
			for(Post post : user.getPosts()){
				if(post.getThread().getCourseId() == courseId)
					posts.add(post);
			}
			
			for(Post post : user.getLikedPosts()){
				if(post.getThread().getCourseId() == courseId)
					likedPosts.add(post);
			}
			
			user.setThreads(threads);
			user.setFavThreads(favThreads);
			user.setPosts(posts);
			user.setLikedPosts(likedPosts);
		}
		request.setAttribute("user", user);
		request.setAttribute("courseId", courseId);
		request.setAttribute("role", role);
		if(role.equalsIgnoreCase("student") || role.equalsIgnoreCase("ta"))
			request.setAttribute("jobs", JobDao.getInstance().findAllJobs());
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/user/user-profile.jsp");
		rd.forward(request, response);
	}

}
