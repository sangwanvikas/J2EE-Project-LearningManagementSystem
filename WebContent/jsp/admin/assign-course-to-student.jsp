<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>


<div class="container">
	<select id="student-list" class="form-control">
		<option class='form-control' value='0'>SELECT</option>
	</select>
	<br/>
	<select id="course-list" multiple class="form-control">
		<option id="course-0" value="0">NONE</option>
	</select>
	<br/>
	<button id="edit-stud" class="btn btn-success" onClick="">SAVE CHANGES</button>
</div>


<script type="text/javascript">

$("document").ready(function() {
	populateStudentList();
	populateCourseList();
	
});

function populateStudentList(){
	var studList = [];
	$.ajax({
		type : "GET",
		url :  "http://localhost:8080/LMS/api/jwsUserCourseDetailService/findUserByRole/STUDENT",
		dataType : "json",
		success : function (result) {
			userJson = result;
			$("#student-list").html("<option class='form-control' value='0'>SELECT</option>"); 
			$.each(result, function(i, val){
				if(studList.indexOf(val.userId) == -1)
					$("#student-list").append(
						"<option id='stud-" + val.userId + "' value=" + val.userId + ">" + val.lastName + ", " + val.firstName +
						"</option>");
				studList.push(val.userId);
			});
			$("#student-list").change(function(){
				selectCoursesFromJSON($(this).val());
				$("#edit-stud").attr("onClick","updateCourseForStudent(" + $(this).val() + ")");
			});
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
			$("#course-list").html("<option class='form-control' id='course-0' value='0'>NONE</option>"); 
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

function selectCoursesFromJSON(userId){
	if(userId == 0){
		clearCourseSelection();	
		return;
	}	
	$.each(userJson, function(i, val){
		if(userId == val.userId){
			clearCourseSelection();
			$.each(val.userCourseDetail, function(j, ucdVal){
				if(ucdVal.roleName.toLowerCase() == "student")
					$("#course-" + ucdVal.courseId).attr("selected", true);
			});
		}
	});
}

function clearCourseSelection(){
	$("#course-list").find("option").attr("selected", false);
}

function getUserCourseDetailJson(userId){
	var ucdList = [];
	$.each($("#course-list").find("option"), function(i, option){
		if($(option).prop("selected") && $(option).val() != 0)
			ucdList.push({
				"userId" : userId,
				"roleName" : "STUDENT",
				"courseId" : $(option).val()
			});
	});
	return ucdList;
}

function updateCourseForStudent(userId){
	var ucdDATA = getUserCourseDetailJson(userId);
	if(ucdDATA.length != 0){
		$.ajax({
			type : "PUT",
			url :  "http://localhost:8080/LMS/api/jwsUserService/updateCourseForStudent",
			data: JSON.stringify({"userId" : userId, "userCourseDetail" : ucdDATA}),
			dataType : "json",
			contentType : "application/json",
			success : function (result) {
				userJson = result;
				console.log(userJson);
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
}

</script>