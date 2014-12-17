<%@ page import="com.lms.model.User" %>
<%@ page import="com.lms.util.ImageUtil" %>
<%@ page import="java.util.List" %>

<% 
	User user = (User) request.getAttribute("user");
	int userId = 0; 
	String role = null;
	if(request.getAttribute("role") != null)
		role = (String) request.getAttribute("role");
	List<User> followingList = null;
	if(user == null) {
		response.sendRedirect("../user/user-login.jsp");
		return;
	} else {
		userId = user.getUserId();
		followingList = user.getFollowedUsersList();
		System.out.println("following:" + followingList);
	}
%>

<% if(user != null) { %>
<div style="width: 100%; height: 50px;" class="well well-sm">
	
	<span style="float: right;"><b>You are logged in as: <%= user.getUserName() %></b></span>
	<a href="/LMS/jsp/user/user-login.jsp">LOGOUT</a>
</div>

<hr/>

<% } %>

<script type="text/javascript">
	
	function navigate(path){
		console.log("Navigating to: " + path);
		var form = $('<form>', {
		    "html":  '<input type="hidden" name="userId" value="<%= userId %>" />'
		    +		'<input type="hidden" name="path" value="' + path + '" />'
		    + 		'<input type="hidden" name="role" value="<%= role %>" />',
		    "action": '<%= applicationContext %>/Navigator',
		    "method": 'post'
		});
		form.appendTo("body").submit();
	}
	
	function viewUser(displayUserId){
		console.log("Displaying userId: " + displayUserId);
		var form = $('<form>', {
		    "html":  '<input type="hidden" name="userId" value="<%= userId %>" />'
		    + 		'<input type="hidden" name="displayUserId" value="' + displayUserId + '" />'
		    + 		'<input type="hidden" name="role" value="<%= role %>" />',
		    "action": '<%= applicationContext %>/ViewProfile',
		    "method": 'post'
		});
		form.appendTo("body").submit();
	}
	
</script>