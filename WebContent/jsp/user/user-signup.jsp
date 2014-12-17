<%@ include file="../common.jsp"%>

<% String errorMsg = (String) request.getParameter("errorMsg"); %>

<body>
	<% if(errorMsg != null) { %>
		<%=  errorMsg %>
	<% } %>
	<div class="container">
		<form action="/LMS/UserSignupServlet" method="post" enctype="multipart/form-data">
			<input class="form-control" name="first-name" id="first-name" value="" placeholder="First Name" />
			<input class="form-control" name="last-name" id="last-name" value="" placeholder="Last Name" />
			<input class="form-control" name="user-name" id="user-name" value="" placeholder="Username" />
			<input class="form-control" name="password" id="password" value="" placeholder="Password" />
			<input class="form-control" name="password-again" id="password-again" value="" placeholder="Re-enter Password" />
			<input class="form-control" name="email" id="email" value="" placeholder="E-mail" />
			<input class="form-control" name="dob" id="dob" value="" placeholder="Date Of Birth" />
			<input class="form-control" type="file" name="userImage" accept="image/*">
			<input class="form-control" type="submit" value="SIGN UP!"/>
		</form>
	</div>

</body>
</html>