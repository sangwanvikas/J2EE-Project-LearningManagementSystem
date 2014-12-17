package com.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dao.JobDao;
import com.lms.dao.UserDao;
import com.lms.model.User;
import com.lms.model.UserCourseDetail;

/**
 * Servlet implementation class UserLoginServlet
 */
//@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	resp.sendRedirect("/LMS/jsp/user/user-login.jsp");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isUserFound=false;
		Boolean hasUserSelectedCorrectRole=false; 
		System.out.println("prf:"+ request.getParameter("user-type"));
		String userName = request.getParameter("user-name").toLowerCase();
		String userPassword = request.getParameter("password").toLowerCase();
		String userType = request.getParameter("user-type").toLowerCase();
		System.out.println(userName);
		System.out.println(userPassword);
		System.out.println(userType);
		UserDao userDaoObj= UserDao.getInstance();
		List<User> allUsersList=userDaoObj.findAllUsers();
		RequestDispatcher rd = null;
		for(User user :  allUsersList){
			if((userName.equals(user.getUserName().toLowerCase()) && (userPassword.equals(user.getPassword().toLowerCase())))){
				isUserFound=true;
				System.out.println("User found with username:" + user.getUserName());
				if(userType.equalsIgnoreCase("ADMIN")){
					request.setAttribute("role", "ADMIN");
					request.setAttribute("user", user);
					rd = request.getRequestDispatcher("/jsp/admin/admin-home.jsp");
					rd.forward(request, response);
					return;
				}
				if(userType.equalsIgnoreCase("STUDENT") && user.getUserCourseDetail().size() == 1 &&
						user.getUserCourseDetail().get(0).getCourseId() == 0){
					response.sendRedirect("/LMS/jsp/user/not-added-to-course.jsp");
					return;
				}
				for(UserCourseDetail ucd:user.getUserCourseDetail()){
					System.out.println("course and role:" + ucd.getCourse().getCourseName() + "->" + ucd.getRoleName());
					if(userType.equals(ucd.getRoleName().toLowerCase())){
						System.out.println("Log in success for:" + user.getUserName() + "->" + ucd.getCourse().getCourseName() 
								+ "->" + ucd.getRoleName());					
						hasUserSelectedCorrectRole=true;
						if(userType.equalsIgnoreCase("student") || userType.equalsIgnoreCase("ta"))
							request.setAttribute("jobs", JobDao.getInstance().findAllJobs());
						request.setAttribute("role", ucd.getRoleName());
						request.setAttribute("user", user);
						rd = request.getRequestDispatcher("/jsp/user/user-profile.jsp");
						rd.forward(request, response);
						return;
					}/*else
						break;*/
				}
			}
		}
		
		if (isUserFound == true && hasUserSelectedCorrectRole == false) {
			request.setAttribute("errorMessage", "This role does not exist for the current user.");
			rd = request.getRequestDispatcher("/jsp/user/user-login.jsp");
			rd.forward(request, response);		
			return;
		}
		else if (isUserFound == false && hasUserSelectedCorrectRole == false) {
				request.setAttribute("errorMessage", "User does not exist,Please try again.");
				rd = request.getRequestDispatcher("/jsp/user/user-login.jsp");
				rd.forward(request, response);		
				return;
			}		
	}

}
