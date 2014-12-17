<%@ include file="../common.jsp"%>
 <%@ include file="../check-login.jsp"%>
<html>
<body>

<div class="container" style="height:600px;">
<label>select Course</label>
<select id="dd-course-list" class="form-control"> </select>

<br/>

<div class="container"> 
<label>Post new job :</label>
<table>

<tr>
<td> 
<label>Job Title</label>
</td>
<td> 
<input id="txt-job-title" name="job-title" type="text"  placeholder="JOB-TITLE" /> 
</td>
</tr>

<tr>
<td> 
<label>Job Description</label>
</td>
<td> 
<input id="txt-job-description" name="job-description" type="text"  placeholder="JOB-DESCRIPTION" /> 
</td>
</tr>

<tr>
<td> 
<label>Job Requirement</label>
</td>
<td> 
<input id="txt-job-requirement" name="job-requirement" type="text"  placeholder="JOB-REQUIREMENT" /> 
</td>
</tr>

<tr>
<td> 
<label>Job Responsibility</label>
</td>
<td> 
<input id="txt-job-responsibility" name="job-responsibility" type="text"  placeholder="JOB-TITLE" /> 
</td>
</tr>

<tr>
<td></td>
<td><button id="btn-save-job" class="btn btn-default"> SAVE JOB</button></td>
</tr>



</table>
<br/>
<br/>
<label>Jobs already posted by you is below :</label>
<br/>
<br/>
</div>


<div class="container" id="cls-job-desc-container" style="height:300px;overflow-y:scroll;">


</div>
</div>
</body>
</html>


<script type="text/javascript">

var applicaitonURL = "/LMS/api";
var userServiceURl ="";

$(document).ready(function(){	
	
	 userServiceURl = userServiceURl = applicaitonURL + "/jwsCourseService/findAllCourses";
		$.ajax({
			type : "GET",
			url :  userServiceURl,
			dataType:"JSON",
			success : function (result) {
				//console.log(result);
				$.each(result, function(i, val){
					$("#dd-course-list").append(
							"<option id='+ val.courseId' value='"+val.courseId+"'>" + val.courseName + "</option>");
				});				
				
				var courseId = $("#dd-course-list").children(":selected").attr("value");
				console.log(courseId);
				findJobsByProfessorIdAndCourseId(<%= userId %>, parseInt(courseId));
			},
			failure : function () {
				console.log("failed");
			}
		});
})


$("#btn-save-job").click(function(){
	 console.log("btn-save-job clicked");
	  userServiceURl = userServiceURl = applicaitonURL + "/jwsJobService/createAJob";
	  var jobObject = new Array();	 
      var jobObject=[];
      
      
      	var courseId = $("#dd-course-list").children(":selected").attr("value");
		var jobDesc = $("#txt-job-description").val();
		var jobRequirement = $("#txt-job-requirement").val();
		var jobResponsobility = $("#txt-job-responsibility").val();
		var jobTitle = $("#txt-job-title").val();
		var userId = <%= userId %>;
			
		jobObject.push({
				"courseId":courseId,
				"jobDesc" :jobDesc,
				"jobRequirement"  :jobRequirement,
				"jobResponsobility" :jobResponsobility,
				"jobTitle" : jobTitle,
				"userId" : userId
				});
		
		 console.log(jobObject);
		
		$.ajax({
			type : "POST",
			url :  userServiceURl,
			data :JSON.stringify(jobObject[0]),
			dataType:"JSON",
			contentType: "application/json",
			success : function (result) {
				console.log(result);
				findJobsByProfessorIdAndCourseId(userId,courseId);
				emptyAllHtmlControlFields()
			},
			failure : function () {
				console.log("failed");
			}
		});	
})


$("#dd-course-list" ).change(function() {		
	//emptyAllHtmlControlFields();	
	findJobsByProfessorIdAndCourseId(<%= userId %>, parseInt($(this).val()));	
})

function findJobsByProfessorIdAndCourseId(userId,courseId){
	userServiceURl =applicaitonURL + "/jwsJobService/findJobsByProfessorIdAndCourseId/"+userId +"/"+courseId;
	var jobData = {"userId" : userId, "courseId" :courseId };
	$.ajax({
		type : "POST",
		url : userServiceURl,
		//data : jobData,
		dataType:"JSON",
		contentType: "application/json",		
		success : function (jobsList) {
			$("#cls-job-desc-container").children().remove();
			$.each(jobsList, function(i, job){
			
			$("#cls-job-desc-container").append(
					
					"<table>"+
					"<tr>"+
					"<td> "+
					"<label>Job Title</label>"+
					"</td>"+
					"<td>"+ 
					"<input value='"+job.jobTitle+ "'  id='txt-job-title"+job.jobId +"' type='text'  placeholder='JOB-TITLE' />"+ 
					"</td>"+
					"</tr>"+

					"<tr>"+
					"<td> "+
					"<label>Job Description</label>"+
					"</td>"+
					"<td> "+
					"<input value='"+job.jobDesc+ "'  id='txt-job-jobDesc"+job.jobId +"' type='text'   placeholder='JOB-DESCRIPTION' />"+ 
					"</td>"+
					"</tr>"+

					"<tr>"+
					"<td>"+ 
					"<label>Job Requirement</label>"+
					"</td>"+
					"<td> "+
					"<input value='"+job.jobRequirement+ "'  id='txt-job-Requirement"+job.jobId +"' type='text'   placeholder='JOB-REQUIREMENT' />"+ 
					"</td>"+
					"</tr>"+
					
					"<tr>"+
					"<td> "+
					"<label>Job Responsibility</label>"+
					"</td>"+
					"<td> "+
					"<input value='"+job.jobResponsobility+ "'  id='txt-job-Responsobility"+job.jobId +"' type='text'   placeholder='JOB-RESPONSIBILITY' />"+ 
					"</td>"+
					"</tr>"+

					"<tr>"+
					"<td></td>"+
					"<td><button id='btn-save-job"+job.jobId +"' onClick='saveAfterEdit("+job.jobId+","+courseId+")'  class='btn btn-default'> Edit and Save </button></td>"+
					"<td><button id='btn-delete-job"+job.jobId +"' onClick='deleteJob("+job.jobId+","+courseId+")'  class='btn btn-default'> Delete </button></td>"+
					"</tr>"+

					"</table><br/><br/>"
			);
			});
		},
		failure : function () {
			console.log("failed");
		}
	});
}

function emptyAllHtmlControlFields(){
$("#txt-job-description").val('');
$("#txt-job-requirement").val('');
$("#txt-job-responsibility").val('');
$("#txt-job-title").val('');
}

$("#btn-add-new-job").click(function(){
	emptyAllHtmlControlFields();
})


function deleteJob(jobId,courseId){
	//console.log(postId);
	userServiceURl =applicaitonURL + "/jwsJobService/deleteJobByJobId";
	$.ajax({
		type : "POST",
		url :  userServiceURl,
		data : {"jobId" : jobId},
		dataType:"JSON",
		contentType: "application/x-www-form-urlencoded",
		success : function (result) {
			console.log(result);
			findJobsByProfessorIdAndCourseId(<%= userId %>,courseId);
			emptyAllHtmlControlFields()
		},
		failure : function () {
			console.log("failed");
		}
	});		
}

function saveAfterEdit(jobId,courseId){
		userServiceURl =applicaitonURL + "/jwsJobService/updateJobByJobId";
		  var jobObject = new Array();	 
	      var jobObject=[];	      
	      	var courseId1 = $("#txt-job-title"+jobId).children(":selected").attr("value");
			var jobDesc = $("#txt-job-jobDesc"+jobId).val();
			var jobRequirement = $("#txt-job-Requirement"+jobId).val();
			var jobResponsobility = $("#txt-job-Responsibility"+jobId).val();
			var jobTitle = $("#txt-job-Responsobility"+jobId).val();
			var userId = <%= userId %>;
				
			jobObject.push({
					"courseId": parseInt(courseId1),
					"jobDesc" :jobDesc,
					"jobRequirement"  :jobRequirement,
					"jobResponsobility" :jobResponsobility,
					"jobTitle" : jobTitle,
					"userId" : userId
					});
			
			console.log(jobObject);
			
	$.ajax({
		type : "POST",
		url :  userServiceURl,
		data : JSON.stringify(jobObject[0]),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			console.log(result);
			findJobsByProfessorIdAndCourseId(<%= userId %>,courseId);
			emptyAllHtmlControlFields()
		},
		failure : function () {
			console.log("failed");
		}
	});		
}
	
	
	



</script>