<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>
<%@ page import="com.lms.model.Thread" %>
<%@ page import="com.lms.model.Post" %>
<%@page import="com.lms.model.Job"%>
<%@page import="com.lms.model.JobApplication"%>
<%@page import="com.lms.model.TaHour"%>

<%
	User displayUser = (User) request.getAttribute("displayUser");
	int courseId =  0;
	if(request.getAttribute("courseId") != null)
		courseId =  (Integer) request.getAttribute("courseId");
	boolean viewer = displayUser != null;
	if(viewer)
		user = displayUser;
	
	List<Job> jobList = null;
	if((role.equalsIgnoreCase("student") || role.equalsIgnoreCase("ta")) && !viewer)
		jobList = (List<Job>) request.getAttribute("jobs");
%>

</html>
<body>
	<!-- <a href="javascript:void(0)" onClick="navigate('/jsp/TA/add-schedule.jsp')">TA adds his schedule</a-->
	<!--a href="javascript:void(0)" onClick="navigate('/jsp/user/threads-for-a-course.jsp')">VIEW ALL THREADS</a>
	<a href="javascript:void(0)" onClick="navigate('/jsp/admin/add-prof.jsp')">ADD PROFESSOR</a>
	<a href="javascript:void(0)" onClick="navigate('/jsp/user/threads-for-a-course.jsp')">ADD/VIEW THREAD</a>
	<a href="javascript:void(0)" onClick="navigate('/jsp/professor/add-ta.jsp')">ADD TA</a><br/>
	<a href="javascript:void(0)" onClick="navigate('/jsp/professor/add-job.jsp')">ADD Job</a>
	<a href="javascript:void(0)" onClick="navigate('/jsp/user/user-view-job-app.jsp')">View Latest Jobs</a>
	<a href="javascript:void(0)" onClick="navigate('/jsp/TA/add-schedule.jsp')">TA adds his schedule</a-->
	
<div class="container">
	<div class="well">	
	<hr/>
	<%-- <% if(displayUser != null)  {%>
		<a href="javascript:void(0)" onClick="navigate('/jsp/user/user-profile.jsp')">BACK TO MY PROFILE</a> <br/>
	<% }  %> --%>
	<img style="width: 40px; height: 50px;" alt="No image" src="<%= ImageUtil.getBase64ImageFromByte(user.getUserImage()) %>">
	
	<span id="name"><span id="first-name"><%= user.getFirstName() %></span>&nbsp;
					<span id="last-name"><%= user.getLastName() %></span></span>
	
	<button id="btn-Un-set-follower" class="btn btn-warning" style="display:none;">UNFOLLOW</button>
	<button id="btn-set-follower" class="btn btn-success" style="display:none;">FOLLOW</button>
	<% if(viewer) {
		boolean isFollower = false;
		for(User u : followingList){
			System.out.println(u.getUserId()  + "==" +  user.getUserId());
			if(u.getUserId() == user.getUserId())
				isFollower = true;
		}
		System.out.println("isFollower:" + isFollower);
		if(isFollower){
	%>
		<script type="text/javascript">
			$("#btn-Un-set-follower").show();
		</script>
	<% } else { %>
		<script type="text/javascript">
			$("#btn-set-follower").show();
		</script>
	<% }
	} %>
	
	<!-- a href="javascript:void(0)" onClick="navigate('/jsp/user/threads-for-a-course.jsp')">ADD/VIEW THREAD</a -->
	<br/><br/>
		<% if ((role != null && !role.equalsIgnoreCase("PROFESSOR")) && !viewer) { %>
	<hr/>
	<div id="job-listing" class="well well-sm">
		<% for(Job job : jobList){ %>
			<div>
				<span class="job-title"><a><%= job.getJobTitle() %></a></span>
				<span><a href="javascript:void(0)" onClick="navigate('/jsp/user/user-view-job-app.jsp')">&nbsp;&nbsp;APPLY</a></span><br/>
				<div class="job-child" style="display:none;">
					<span>DESCRIPTION: <%= job.getJobDesc() %></span><br/>
					<span>RESPONSIBILITIES: <%= job.getJobResponsobility() %></span><br/>
					<span>REQUIREMENT: <%= job.getJobRequirement() %></span><br/>
				</div>
			</div>
		<% } %>
	</div>
	<% } %>
	
	
	<% if (role != null && role.equalsIgnoreCase("TA")) { %>
	<div class="well well-sm">
		Office Hours: <br/>
		<% for(TaHour taHr : user.getTaAllHoursForAllCourses()) {%>
			<b>COURSE:</b><%= taHr.getCourse().getCourseName() %>,
			<b>LOCATION:</b><%= taHr.getTaHourLocation() %>,
			<b>DAY:</b><%= taHr.getTaHourDay() %>,
			<b>TIMING:</b><%= taHr.getTaHourStartTime() %> - <%= taHr.getTaHourEndTime() %><br/>
		<% } %>
		<% if(!viewer) { %>
			<a href="javascript:void(0)" onClick="navigate('/jsp/TA/add-schedule.jsp')">ADD SCHEDULE</a>
		<% } %>
	</div>
	<% } %>
	<br/>
	
	<% if (role != null && role.equalsIgnoreCase("PROFESSOR")) { %>
		<a href="javascript:void(0)" onClick="navigate('/jsp/professor/add-ta.jsp')">ADD/REMOVE TA</a><br/>
		<span><a href="javascript:void(0)" onClick="navigate('/jsp/professor/add-job.jsp')">ADD JOB</a></span><br/>
	<% } %>
	
	<% if(!viewer) { %>
		<a href="javascript:void(0)" onClick="navigate('/jsp/user/user-list.jsp')">VIEW ALL USERS</a> <br/>
	<% } %>
	
	<select id="course-role" class="form-control"></select>
	
	<hr/>
	THREAD:
	<div id="posted-thread-container" class="well well-sm">
		<% for(Thread thread : user.getThreads()) {%>
			<%= thread.getThreadTitle() %>
		
			<a href="javascript:void(0)" onClick="browseThreadByThreadId(<%= thread.getThreadId() %>,<%= thread.getCourseId() %>)"><%= thread.getThreadContent() %></a>
			<hr/>
		<% } %>
		<% if(!viewer) { %>
				<a href="javascript:void(0)" onClick="navigate('/jsp/user/threads-for-a-course.jsp')">ADD/VIEW THREAD</a>
		<% } %>
	</div>
	<hr/>
	FAVORITE THREAD:
	<div id="fav-thread-container" class="well well-sm">
		<% for(Thread thread : user.getFavThreads()) {%>
			<%= thread.getThreadTitle() %>
	<a href="javascript:void(0)" onClick="browseThreadByThreadId(<%= thread.getThreadId() %>,<%= thread.getCourseId() %>)"><%= thread.getThreadContent() %></a>
			<hr/>
		<% } %>
	</div>
	<hr/>
	POSTS:
	<div id="user-post-container" class="well well-sm">
		<% for(Post post : user.getPosts()) {%>
			<%= post.getPostContent() %>
			<hr/>
		<% } %>
	</div>
	<hr/>
	LIKED POSTS:
	<div id="liked-post-container" class="well well-sm">
		<% for(Post post : user.getLikedPosts()) {%>
			<%= post.getPostContent() %>
			<hr/>
		<% } %>
	</div>
	
	<hr/>
	FOLLOWING:
	<div id="following-container" class="well well-sm">
		<% for(User u: user.getFollowedUsersList()) {%>
			<a href="javascript:void(0)" onClick="viewUser(<%= u.getUserId() %>)"> <%= u.getFirstName() %> <%= u.getLastName() %> </a>
			<hr/>
		<% } %>
	</div>
	
	<hr/>
	FOLLOWED BY:
	<div id="followed-by-container" class="well well-sm">
		<% for(User u: user.getFollowersUsersList()) {%>
			<a href="javascript:void(0)" onClick="viewUser(<%= u.getUserId() %>)"> <%= u.getFirstName() %> <%= u.getLastName() %> </a>
			<hr/>
		<% } %>
	</div>
	
	<% if ((role != null && role.equalsIgnoreCase("PROFESSOR")) && !viewer) { %>
	<hr/>
		<div id="jobs" class="well well-sm">
			<% for(Job job : user.getJobList()) { %>
				<div>
					<span class="job-title"><a><%= job.getJobTitle() %></a></span><br/>
					<div class="job-child" style="display:none;">
						<span>DESCRIPTION: <%= job.getJobDesc() %></span><br/>
						<span>RESPONSIBILITIES: <%= job.getJobResponsobility() %></span><br/>
						<span>REQUIREMENT: <%= job.getJobRequirement() %></span><br/>
					</div>
					<div id="job-application">
						Applications:
						<% for(JobApplication jobApp : job.getJobApplications()) { %>
							<span>Username: <%= jobApp.getUser().getUserName() %></span>
							<span>Email: <%= jobApp.getUser().getEmail() %></span>
							<span>GPA: <%= jobApp.getGpa() %></span>
						<% } %>
					</div>
				</div>
			<% } %>
			
		</div>
	<% } %>
	

	</div>
</div>
	
	
</body>
</html>


<script type="text/javascript">

var applicaitonURL = "/LMS/api";
var userCourseDetailURL = applicaitonURL + "/jwsUserCourseDetailService";
var userServiceURl ;

$("document").ready(function(){
	loadCourseRoleForUser(<%= userId %>);
	$(".job-title").click(function (){
	  var myUL = $(this).siblings(".job-child").toggle();
	});
});

$("#btn-set-follower").click(function(){
	console.log({"followerUserId" : <%= userId %>, "followedUserId" : <%= user.getUserId() %>});
	userServiceURl =  applicaitonURL + "/jwsUserService/UserFollowsAnotherUser";
	$.ajax({
		type : "POST",
		url : userServiceURl,
		data : {"followerUserId" : <%= userId %>, "followedUserId" : <%= user.getUserId() %>},
		dataType:"JSON",
		contentType: "application/x-www-form-urlencoded",
		success : function(user){
			console.log(user);
			$("#btn-set-follower").hide();
			$("#btn-Un-set-follower").show();
		},
		failure : function(){
			console.log("Error while Following a User...");
		}
	});

})


$("#btn-Un-set-follower").click(function(){
	console.log({"followerUserId" : <%= userId %>,  "followedUserId" : <%= user.getUserId() %>});
	userServiceURl =  applicaitonURL + "/jwsUserService/UserUnFollowsAnotherUser";
	$.ajax({
		type : "POST",
		url : userServiceURl,
		data : {"followerUserId" : <%= userId %>, "followedUserId" : <%= user.getUserId() %>},
		dataType:"JSON",
		contentType: "application/x-www-form-urlencoded",
		success : function(user){
			console.log(user);
			$("#btn-Un-set-follower").hide();
			$("#btn-set-follower").show();
		},
		failure : function(){
			console.log("Error while Following a User...");
		}
	});

});

function loadCourseRoleForUser(userId){
	$.ajax({
		type : "GET",
		url : userCourseDetailURL + "/findCourseRoleByUserId/" + userId,
		dataType:"JSON",
		success : function(userCourseRoles){
			console.log("user role:");
			console.log(userCourseRoles);
			$("#course-role").html("<option class='form-control' id='course-role-none' role='' value='0'>SELECT A COURSE</option>");
			$.each(userCourseRoles, function(i, ucr){
				console.log(ucr.roleName + " IN " + ucr.course.courseName);
				$("#course-role").append(
						"<option class='form-control' " +  
						"') value='" + ucr.course.courseId + "' role='" + ucr.roleName + "'>" + 
						ucr.roleName + " IN " + ucr.course.courseName + "</option>");
				$("#course-role").change(function(){
					loadViewForCourse($("#course-role").find(":selected").val(),
										$("#course-role").find(":selected").attr("role"));
				});
				<% if (courseId != 0) { %>
					$("#course-role").val(<%=courseId%>);
				<% } %> 
			});
		},
		failure : function(){
			console.log("Error while Following a User...");
		}
	});	
}

function loadViewForCourse(course, role){

	var form = $('<form>', {
	    "html":  '<input type="hidden" name="userId" value="<%= userId %>" />'
	    +		'<input type="hidden" name="courseId" value="' + course + '" />'
	    +		'<input type="hidden" name="role" value="' + role + '" />',
	    "action": '<%= applicationContext %>/ProfilePageForCourse',
	    "method": 'post'
	});
	form.appendTo("body").submit();
}

function browseThreadByThreadId(threadId,courseId){
	navigate('/jsp/user/threads-for-a-course.jsp?threadId='+threadId+'&courseId='+courseId);
	
}

</script>