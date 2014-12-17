<%@include file="../common.jsp" %>
<%@ include file="../check-login.jsp"%>

<body>
	<div class="container">
		<div>
			<select id="prof-list" class="form-control">
				<option class='form-control' id='add-new' value='ADD NEW'>ADD NEW</option>
			</select>
			<br/>
		</div>
		
		<div>
			<!-- label for="first-name">FIRST NAME</label-->
			<input id="first-name" name="first-name" type=text class="form-control" placeholder="FIRST NAME"/>
			<!-- label for="last-name">LAST NAME</label-->
			<input id="last-name" name="last-name" type=text class="form-control" placeholder="LAST NAME"/>
			<!-- label for="user-name">USER NAME</label-->
			<input id="user-name" name="user-name" type=text class="form-control" placeholder="USER NAME"/>
			<!-- label for="password">PASSWORD</label-->
			<input id="password" name="password" type=text class="form-control" placeholder="PASSWORD"/>
			<!-- label for="email">E-MAIL ADDRESS</label-->
			<input id="email" name="email" type=text class="form-control" placeholder="E-MAIL ADDRESS"/>
			<!-- label for="dob">DATE OF BIRTH</label-->
			<input id="dob" name="dob" type=text class="form-control" style="display:none;" value="09-14-2014" placeholder="DATE OF BIRTH"/>
			
			<select id="course-list" multiple class="form-control">
				<option id="course-0" value="0">NONE</option>
			</select>
		</div>
		<br/>
		<div>
			<button id="edit-prof" class="btn btn-default" onClick="updateUserDetails()">SAVE CHANGES</button>
			<button id="delete-prof" class="btn btn-default" onClick="deleteUser()">DELETE</button>
			<button id="add-prof" class="btn btn-default" onClick="createProfessor()">ADD</button>
		</div>
	</div>
</body>

<script type="text/javascript">
	var applicaitonURL = "/LMS/api";
	var userCreateServiceURL = applicaitonURL + "/jwsUserService/createUser";
	var userUpdateServiceURl = applicaitonURL + "/jwsUserService/updateUser";
	var userUpdatDeleteServiceURl = applicaitonURL + "/jwsUserService/deleteUser";
	var userJson = null;

	$(document).ready(function() {
		populateProfessorList();
		populateCourseList()
		profListChange();
		$("#prof-list").change(profListChange);
	});
	
	function profListChange(){
		if($("#prof-list").val() == "ADD NEW"){
			$("#edit-prof").hide();
			$("#delete-prof").hide();
			$("#add-prof").show();
			populateuserDetail("", "", "", "", "", "");
			clearCourseSelection();
		}else{
			$("#edit-prof").show();
			$("#delete-prof").show();
			$("#add-prof").hide();
			populateUserDetailsFromJSON(extractUserIdFromOptionId($("#prof-list")));
		}
	}
	
	function extractUserIdFromOptionId(selectElement){
		return selectElement.children(":selected").attr("id").split("prof-")[1];
	}
	
	function populateUserDetailsFromJSON(userId){
		$.each(userJson, function(i, val){
			if(val != null){
				if(userId == val.userId){
					populateuserDetail(val.firstName, val.lastName, val.userName, val.password, val.email, val.dateOfBirth);
					clearCourseSelection();
					$.each(val.userCourseDetail, function(j, ucdVal){
						if(ucdVal.roleName.toLowerCase() == "professor")
							$("#course-" + ucdVal.courseId).attr("selected", true);
					});
				}
			}
		});
	}
	
	function clearCourseSelection(){
		$("#course-list").find("option").attr("selected", false);
	}
	
	function populateuserDetail(firstName, lastName, userName, password, email, dob){
		$("#first-name").val(firstName);
		$("#last-name").val(lastName);
		$("#user-name").val(userName);
		$("#password").val(password);
		$("#email").val(email);
		//$("#dob").val(dateOfBirth);  // NEED TO CHANGE THE DATE FORMAT
	}
	
	function createProfessor(){
		$.ajax({
			type : "POST",
			dataType : "json",
			url :  userCreateServiceURL,
			data : JSON.stringify(getUserJson("PROFESSOR")),
			contentType : "application/json",
			success : function (result) {
				console.log(result);
				location.reload();
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
	function getUserJson(role){
		return {
			"userName" : $("#user-name").val(),
			"password" : $("#password").val(),
			"firstName" : $("#first-name").val(),
			"lastName" : $("#last-name").val(),
			"email" : $("#email").val(),
			/*"dateOfBirth" : new Date(),*/
			"userCourseDetail" : getuserCourseDetailJSON(role)
		}
	}
	
	function getProfessorQueryString(){
		return "/" + 
		$("#user-name").val() + 
		"/" + 
		$("#password").val() + 
		"/"+
		$("#first-name").val() + 
		"/" + 
		$("#last-name").val() +
		"/" + 
		$("#email").val() + 
		"/" + 
		$("#dob").val() + 
		"/" +
		"PROFESSOR" + 
		"/" + 
		getCourseIds();
	}
	
	
	function getCourseIds(){
		var courseList = $("#course-list").val();
		var toReturn = "";
		if(courseList != null)
			$.each(courseList, function (i, val){
				toReturn += val;
				if(i < courseList.length - 1) toReturn += ",";
			});
		return toReturn;
	}
	
	function getuserCourseDetailJSON(role){
		var courseList = $("#course-list").val();
		var toReturn = [];
		if(courseList != null)
			$.each(courseList, function (i, val){
				toReturn.push({
					"courseId" : val,
					"roleName" : role,
				});
			});
		return toReturn;
	}
	
	function populateProfessorList(){
		var profList = [];
		$.ajax({
			type : "GET",
			url :  "http://localhost:8080/LMS/api/jwsUserCourseDetailService/findUserByRole/PROFESSOR",
			dataType : "json",
			success : function (result) {
				userJson = result;
				$("#prof-list").html(
				"<option class='form-control' id='add-new' value='ADD NEW'>ADD NEW</option>"); 
				
				$.each(result, function(i, val){
					if(val != null){
						if(profList.indexOf(val.userId) == -1)
							$("#prof-list").append(
								"<option id='prof-" + val.userId + "'>" + val.lastName + ", " + val.firstName + "</option>");
						profList.push(val.userId);
					}
				});
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
	function updateUserDetails(){
		$.ajax({
			type : "PUT",
			url :  userUpdateServiceURl + "/" + extractUserIdFromOptionId($("#prof-list")) + getProfessorQueryString(),
			dataType : "json",
			contentType : "application/json",
			success : function (result) {
				populateProfessorList();
				$("#prof-list option:first").attr('selected','selected');
				populateuserDetail("", "", "", "", "", "");
				clearCourseSelection();
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
	function deleteUser(){
		$.ajax({
			type : "delete",
			url :  userUpdatDeleteServiceURl + "/" + extractUserIdFromOptionId($("#prof-list")),
			dataType : "json",
			success : function (result) {
				//userJson = result;
				console.log("delete success");
				populateProfessorList();
				$("#prof-list option:first").attr('selected','selected');
				populateuserDetail("", "", "", "", "", "");
				
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
	function populateCourseList(){
		$.ajax({
			type : "GET",
			url :  "http://localhost:8080/LMS/api/jwsCourseService/findAllCourses",
			dataType : "json",
			success : function (result) {
				$("#course-list").html(
				"<option class='form-control' id='course-0' value='0'>NONE</option>"); 
				$.each(result, function(i, val){
					$("#course-list").append(
						"<option class='form-control' value='" + val.courseId + "' id='course-" + val.courseId + "'>" + 
						val.courseName + "</option>");
				});
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
</script>
</html>