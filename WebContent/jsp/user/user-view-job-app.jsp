
<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>
<link href="/LMS/css/jquery-te-1.4.0.css" rel="stylesheet">

<script src="/LMS/js/jquery-te-1.4.0.min.js" type="text/javascript"></script>
<html>
<style>

table, td, th {
    border: .1px solid black;
    
}
.spanClass hover {
  text-decoration: underline;
  color: blue;
  cursor: hand;
}

.table-spacing{
   border-spacing:5px;
}

</style>

<body>
	<div class="container" style="height:600px;">
		<div>
			<select id="course-list" class="form-control">
				
			</select> <br />			
		</div> <br />		
	
	<table  class="table table-striped">
		<tr><th style="width:350px"> Job Titles</th><th> Job Description</th></tr>
		
		<tr style="height:650px;">
		
		<td><table  id="tbl-all-jobs" class="table table-striped"></table></td>
		
		<td><table id="tbl-job-desc" class="table table-striped"></table>
			</div>
		</td>
		</tr>

		</table>
</div>
</body>
</html>

<script type="text/javascript">

var applicaitonURL = "/LMS/api";
var jobServiceUrl ;

$(document).ready(function(){	
	refreshCourseList();
	
});


function refreshCourseList(){
	userServiceURl = applicaitonURL + "/jwsCourseService/findAllCourses/";
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data: "<%= userId %>",
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			//console.log(result);
			$.each(result, function(i, val){
				$("#course-list").append(
						"<option id='course-" + val.courseId + "' value='"+val.courseId+"'>" + val.courseName + "</option>");
			});		
			var courseId = $("#course-list").children(":selected").attr("value");
			loadAllJobsByCourseId(courseId);
		},
		failure : function () {
			console.log("failed");
		}
	});
}

function loadAllJobsByCourseId(courseId){
	userServiceURl =  applicaitonURL + "/jwsJobService/findAllJobsByCourseId/"+courseId;
		$.ajax({
			type : "GET",
			url :  userServiceURl,
			//data: {"courseId" : courseId},
			dataType:"JSON",
			contentType: "application/x-www-form-urlencoded",
			success : function (allJobs) {
				$("#tbl-all-jobs").children().remove();
				$("#tbl-job-desc").children().remove();
					if(allJobs.length > 0){	
					$.each(allJobs, function(i, job){				
						$("#tbl-all-jobs").append(
								"<tr style='height:120px;'> <td  onClick='loadAJobByJobId("+job.jobId+")'><label> (Job Id:"+job.jobId+") </label> <span class='spanClass'  id='"+ job.jobId +"'>"+ job.jobTitle+ "</span></td></tr>");
					});
					var firstJobIdInList = $("#tbl-all-jobs span:first").attr("id");
					loadAJobByJobId(firstJobIdInList);
				}				
			},
			failure : function () {
				console.log("failed");
			}
		});
	}
	
$("#course-list").change(function() {
	var courseId = $(this).children(":selected").attr("value");
	//console.log(courseId);
	loadAllJobsByCourseId(courseId);
});


function loadAJobByJobId(jobId){
	console.log(jobId);
	userServiceURl = applicaitonURL + "/jwsJobService/findAJobByJobId/"+jobId;
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data : JSON.stringify(jobId),
		dataType:"JSON",
		contentType: "application/json",
		success : function (job) {
			console.log(job);
			$("#tbl-job-desc").children().remove();	
				 $("#tbl-job-desc").html(
						"<tr style='height:20px;'> <td><span  id='job-title" + job.jobId + "'>"+ job.jobTitle+ "</span><label> (Job Id:"+job.jobId+") </label></td></tr>"+
						"<tr style='height:250px;'> <td> <span  id='job-desc" + job.jobId+ "'>"+ job.jobDesc+ "</span></td></tr>"+
						"<tr style='height:100px;'> <td> <span   id='job-Requirement" + job.jobId+ "'>"+ job.jobRequirement+ "</span></td></tr>"+
						"<tr style='height:100px;'> <td> <span   id='job-Responsobility" + job.jobId+ "'>"+ job.jobResponsobility+ "</span></td></tr>"+
						"<tr><td><input id='txt-gpa' type='text' placeholder='GPA'></td></tr>"+
						"<tr><td><input id='btn-submit-post' onClick='applyForJob("+job.jobId+")' class='btn btn-primary' type='submit' value='Post' style='' /></td></tr>"
				 );				
		},
		failure : function () {
			console.log("failed");
		}
	});
	
}

function applyForJob(jobId){
	userServiceURl = applicaitonURL + "/jwsJobApplicationService/createJobApplication";
	
	var jobId1 = jobId;
	var courseId = parseInt($("#course-list").children(":selected").attr("value"));
	var jobDesc = $("#job-desc"+jobId).text();
	var jobRequirement = $("#job-Requirement"+jobId).text();
	var jobResponsobility = $("#job-Responsobility"+jobId).text();
	var jobTitle = $("#job-title"+jobId).text();
	var userId = <%= userId %>;
	
	
	
	var gpa = $("#txt-gpa").val();
	
	 var job={"jobId" : jobId1,  "courseId" : courseId, "jobDesc" : jobDesc,"jobRequirement" : jobRequirement,
			 "jobResponsobility" : jobResponsobility,"jobTitle" : jobTitle, "userId" : userId};
	 
	 console.log(job);
	 
	 var postApplication = {"gpa" : gpa, "userId" : userId, "jobId":jobId1 ,"job": job }
	 console.log(postApplication);
		$.ajax({
			type : "POST",
			url :  userServiceURl,
			data : JSON.stringify(postApplication),
			dataType:"JSON",
			contentType: "application/json",
			success : function (job) {
				
				alert("Your application for job id :"+job.jobId+" has been successfully submitted. To view job home page click ok.");
				location.reload(true);
			},
			failure : function () {
				console.log("failed");
			}
		});
		
	 
}
	
	
	
	


</script>