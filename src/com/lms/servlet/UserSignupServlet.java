package com.lms.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lms.dao.UserCourseDetailDao;
import com.lms.dao.UserDao;
import com.lms.model.User;
import com.lms.model.UserCourseDetail;


/**
 * Servlet implementation class UserSignupServlet
 */
@WebServlet("/UserSignupServlet")
public class UserSignupServlet extends HttpServlet {
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
		System.out.println("UserSignupServlet-->doPost");
		/*String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String userName = request.getParameter("user-name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String dateOfBirth = request.getParameter("dob");*/
		
		boolean studentcreated = false;
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		
		if(!isMultipartContent){
			System.out.println("Request doesn't have multipart data");
			return;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		byte[] image = new byte[]{};
		try {
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> fieldIterator = fields.iterator();
			
			if(!fieldIterator.hasNext())
				return;
			
			User user = new User();
			while(fieldIterator.hasNext()){
				FileItem fileItem = (FileItem) fieldIterator.next();
				if (fileItem.isFormField()){
					System.out.println("Field name: " + fileItem.getFieldName());
					System.out.println("Content: " + fileItem.getString());
					updateUser(user, fileItem.getFieldName(), fileItem.getString());
				} else {
					System.out.println("Field name: " + fileItem.getFieldName());
					//System.out.println("Content: " + fileItem.getString());
					System.out.println("File name: " + fileItem.getName());
					System.out.println("File content type: " + fileItem.getContentType());
					System.out.println("File size: " + fileItem.getSize());
					System.out.println("String: " + fileItem.get());
					image = fileItem.get();
					user.setUserImage(image);
				}
			}
			user = UserDao.getInstance().createUser(user);
			UserCourseDetailDao.getInstance().createUserCourseDetail(new UserCourseDetail(0, user.getUserId(), "STUDENT"));
			studentcreated = true;
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(studentcreated) response.sendRedirect("jsp/user/user-login.jsp");
		else {
			RequestDispatcher rd  = request.getRequestDispatcher("/jsp/user/user-signup.jsp");
			request.setAttribute("errorMsg", "Unable to create user, please try again");
			rd.forward(request, response);
		}
	}

	private User updateUser(User user, String fieldName, String value){
		
		switch (fieldName) {
			case "first-name" 	: user.setFirstName(value);
								break;
			case "last-name" 	: user.setLastName(value);
								break;
			case "user-name" 	: user.setUserName(value);
								break;
			case "password" 	: user.setPassword(value);
								break;
			case "email" 		: user.setEmail(value);
								break;
			case "dob" 			: user.setDateOfBirth(new Date());
								break;
		}
		
		return user;
	}
}
