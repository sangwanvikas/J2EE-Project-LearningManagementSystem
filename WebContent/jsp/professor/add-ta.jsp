<%@ include file="../common.jsp" %>
<%@ include file="../check-login.jsp"%>


<html>
<body>
<div>
<select id="dd-course-list" class="form-control"></select>
<br />
</div>
<table id="tb-users-list" class="table table-striped">
 <tr>
   <th>select</th>
   <th>first name</th>
   <th>last name</th>
   <th>user name</th>
   <th>email</th>
 </tr>
 </table>
 <button id="btn-add-ta" class="btn btn-default">Save</button>
 
</body>
</html>


<script type="text/javascript">

var applicaitonURL = "/LMS/api";
var userServiceURl ="";

$(document).ready(function(){	
	// userServiceURl = userServiceURl = applicaitonURL + "/jwsCourseService/findAllCourses";
	 userServiceURl =  applicaitonURL + "/jwsCourseService/findAllCoursesForAProf/"+<%= userId %>;
		$.ajax({
			type : "GET",
			url :  userServiceURl,
			dataType:"JSON",
			success : function (result) {
				//console.log(result);
				$.each(result, function(i, val){
					$("#dd-course-list").append(
							"<option id='course-" + val.courseId + "' value='"+val.courseId+"'>" + val.courseName + "</option>");
				});
				
				var id=id=$("#dd-course-list").children(":selected").attr("value");
				findAllStudentsToBeElligibleToBecomeTa(id);
				
			},
			failure : function () {
				console.log("failed");
			}
		});
	
})


$("#dd-course-list").change(function(){	
	var id=id=$("#dd-course-list").children(":selected").attr("value");
	findAllStudentsToBeElligibleToBecomeTa(id);
	
})

function findAllStudentsToBeElligibleToBecomeTa(courseId){
	userServiceURl = applicaitonURL + "/jwsUserService/findAllUsersNotTAForACourse";
	$.ajax({
		type : "POST",
		url :  userServiceURl,
		data :courseId,
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			console.log(result);
			//console.log("success"+id);
			$("#tb-users-list").children().remove();
			$.each(result,function(i,userVal){
				console.log("success"+userVal);		
					$("#tb-users-list").append(								
					"<tr>"+
					     "<td><input id='chb-"+userVal.userId+"' type='checkbox' name='abc' value='"+userVal.userId+"'> </td>"+
					     "<td>"+userVal.firstName+"</td>"+
					     "<td>"+userVal.lastName+"</td>"+
					     "<td>"+userVal.userName+"</td>"+
					     "<td>"+userVal.email+"</td>"+
					"</tr>"
					)					
		})									
		},
		failure : function () {
			console.log("failed");
		}
	});
	
	
}




$("#btn-add-ta").click(function(){	
	 userServiceURl = userServiceURl = applicaitonURL + "/jwsUserCourseDetailService/createUserCourseDetailForTa";
	 var courseId =parseInt($("#dd-course-list").children(":selected").attr("value"));
	 var roleName ="TA";
	 var arrayobj=new Array();	 
	 var arrayobj1=[];	 
	 var ucdList = new Array();
	  $('input[name="abc"]:checked').each(function() {
		  arrayobj1 = { "courseId" : courseId, "userId" : parseInt($(this).attr("value")) , "roleName" : roleName};
		  ucdList.push(arrayobj1);
	 });
	  console.log(ucdList);
		$.ajax({
			type : "POST",
			url :  userServiceURl,
			data : JSON.stringify(ucdList),
			dataType:"JSON",
			contentType: "application/json",
			success : function (result) {
				console.log(result);		
				var id=id=$("#dd-course-list").children(":selected").attr("value");
				findAllStudentsToBeElligibleToBecomeTa(id);
			},
			failure : function () {
				console.log("failed");
			}
		});	
})







</script>