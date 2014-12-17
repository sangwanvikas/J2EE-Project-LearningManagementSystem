package com.lms.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lms.dao.UserDao;

/**
 * Servlet implementation class Navigator
 */
@WebServlet("/Navigator")
public class Navigator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	resp.sendRedirect("/LMS/jsp/user/user-login.jsp");
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String path = request.getParameter("path");
		String role = request.getParameter("role");
		System.out.println("Navigating userId:" + userId + " to " + path + " ...");
		request.setAttribute("user", UserDao.getInstance().findUserByUserId(userId));
		request.setAttribute("role", role);
		request.setAttribute("path", path);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
