<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>
<link href="/LMS/css/jquery-te-1.4.0.css" rel="stylesheet">

<script src="/LMS/js/jquery-te-1.4.0.min.js" type="text/javascript"></script>

<html>
<body>

<div class="container">

<select id="dd-course-list" class="form-control"> </select> <br/> <br/>
<button id="btn-add-new-ta-schedule"  class="btn btn-success">New Schedule</button>
<button id="btn-edit-ta-schedule"  class="btn btn-success">Edit existing Schedule</button>
<button id="btn-delete-ta-schedule"  class="btn btn-success">Delete Existing Schedule</button>

<br/> <br/>


<table class="table table-striped">

<tr>
<td> 
<label>TA hours - Start Time</label>
</td>
<td> 
<input id="txt-ta-start-time" name="start-time" type="text"  placeholder="START-TIME" /> 
</td>
</tr>

<tr>
<td> 
<label>TA hours - End Time</label>
</td>
<td> 
<input id="txt-ta-end-time"  type="text"  placeholder="END-TIME" /> 
</td>
</tr>

<tr>
<td> 
<label>TA hours - Location</label>
</td>
<td> 
<input id="txt-ta-location" name="ta-location" type="text"  placeholder="LOCATION" /> 
</td>
</tr>

<tr>
<td> 
<label>TA Day</label>
</td>
<td> 
<select id="ta-hour-day"  class='form-control'>
	<option value='monday'>Monday</option>
	<option value='tuesday'>Tuesday</option>
	<option value='wednesday'>Wednesday</option>
	<option value='thursday'>Thursday</option>
	<option value='friday'>Friday</option>
	<option value='saturday'>Saturday</option>
	<option value='sunday'>Sunday</option>		  	
</select>
</td>
</tr>

<tr>
<td></td>
<td><button id="btn-save-ta-schedule"  class="btn btn-success"></button></td>
</tr>



</table>

</div>


</body>
</html>

<script type="text/javascript">

var taHourId=0;

var applicaitonURL = "/LMS/api";
var userServiceURl ;

$(document).ready(function(){
	$("#btn-add-new-ta-schedule").hide();
	$("#btn-edit-ta-schedule").hide();
	$("#btn-delete-ta-schedule").hide();
	
	refreshCourseList();
	$("#btn-save-ta-schedule").hide();
})


$("#btn-save-ta-schedule").click(function(){
	console.log("btn-save-job clicked");
	
	if($(this).val()=="CreateAndSave"){
		console.log("createNewTaHour");
		createNewTaHour();
	}
	else
	{
		console.log("updateNewTaHour");
		updateExistingTaHour(taHourId);
	}
	
})

function createNewTaHour(){
	
	userServiceURl = applicaitonURL + "/jwsTaHours/createTaHours";
	  
	var courseId = parseInt($("#dd-course-list").children(":selected").attr("value"));
	var taStarTime= $("#txt-ta-start-time").val();
	var taEndTime  = $("#txt-ta-end-time").val();		
	var taHourLocation = $("#txt-ta-location").val();
	var taHourDay = $("#ta-hour-day").children(":selected").attr("value");
	var userId = <%= userId %>;
	
	var taHour = new Array();	 
	var taHour=[];
     
	taHour.push({
			"courseId":courseId,
			"taHourDay" :taHourDay,
			"taHourEndTime"  :taEndTime,
			"taHourLocation" :taHourLocation,
			"taHourStartTime" : taStarTime,
			"taId" : userId
			});
	
	 console.log(taHour);
	
	$.ajax({
		type : "POST",
		url :  userServiceURl,
		data :JSON.stringify(taHour[0]),
		dataType:"JSON",
		contentType: "application/json",
			success : function (result) {
				console.log(result);

				var courseId = $("#dd-course-list").children(":selected").attr("value");			
				findTaHoursByCourseId(parseInt(courseId));
				disableAllFields();
				
				$("#btn-add-new-ta-schedule").hide();
				$("#btn-edit-ta-schedule").show();
				

				$("#btn-save-ta-schedule").hide();
			},
			failure : function () {
				console.log("failed");
			}
	});	
	
}


function refreshCourseList(){
	userServiceURl =  applicaitonURL + "/jwsCourseService/findAllCoursesForATa/"+ <%= userId %>;	
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data : JSON.stringify(<%= userId %>),
		dataType:"JSON",
		contentType: "application/json",
			success : function (result) {
				console.log(result);
				$.each(result, function(i, val){
				$("#dd-course-list").append(
					"<option id='course-" + val.courseId + "' value='"+val.courseId+"'>" + val.courseName + "</option>");
				});
				
				var courseId = $("#dd-course-list").children(":selected").attr("value");			
				findTaHoursByCourseId(parseInt(courseId));
				disableAllFields();
			},
			failure : function () {
				console.log("failed");
			}
	});

}

function findTaHoursByCourseId(courseId){

	
	console.log(courseId);
	userServiceURl =  applicaitonURL +  "/jwsTaHours/findTaHoursByCourseId/"+courseId;
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data : JSON.stringify(courseId),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			
			
				console.log( result.length);
				if(result.length > 0){
				$("#txt-ta-start-time").val(result[0].taHourStartTime);
			    $("#txt-ta-end-time").val(result[0].taHourEndTime);		
				$("#txt-ta-location").val(result[0].taHourLocation);
				$("#ta-hour-day").val(result[0].taHourDay); 
				$("#btn-add-new-ta-schedule").hide();
				$("#btn-edit-ta-schedule").show();
				$("#btn-delete-ta-schedule").show();
				taHourId=result[0].taHourId;
			}
			else
				{
				$("#btn-add-new-ta-schedule").show();
				$("#btn-edit-ta-schedule").hide();
				$("#btn-delete-ta-schedule").hide();
				
				emptyAllHTMLCOntrolOnThisPage();
				}
					
			
		},
		failure : function () {
			console.log("failed");
		}
	});
	
}

$("#dd-course-list" ).change(function() {	
	var courseId = $("#dd-course-list").children(":selected").attr("value");			
	findTaHoursByCourseId(parseInt(courseId));
	$("#btn-save-ta-schedule").hide();
	disableAllFields();
})

function emptyAllHTMLCOntrolOnThisPage(){
	
	$("#txt-ta-start-time").val('');
    $("#txt-ta-end-time").val('');		
	$("#txt-ta-location").val('');
	$("#ta-hour-day").val(''); 
}

$("#btn-add-new-ta-schedule").click(function(){
	
	emptyAllHTMLCOntrolOnThisPage();
	
	$("#btn-save-ta-schedule").show();
	$("#btn-save-ta-schedule").html("Save New");
	$("#btn-save-ta-schedule").val("CreateAndSave");
	enableAllFields();
	
})

$("#btn-edit-ta-schedule").click(function(){
	var courseId = $("#dd-course-list").children(":selected").attr("value");			
	findTaHoursByCourseId(parseInt(courseId));
	enableAllFields();
	$("#btn-save-ta-schedule").show();
	$("#btn-save-ta-schedule").html("Press to sync.");
	$("#btn-save-ta-schedule").val("EditAndSave");
	
	
})

$("#btn-delete-ta-schedule").click(function(){
	
/* 	$("#btn-add-new-ta-schedule").show();
	$("#btn-edit-ta-schedule").hide();
	$("#btn-delete-ta-schedule").hide(); */
	
	deleteTaHoursByTaHourId(taHourId);
	
	
})

function deleteTaHoursByTaHourId(taHourIdArg){
userServiceURl =  applicaitonURL +  "/jwsTaHours/deleteTaHoursByTaHourId";
	$.ajax({
		type : "DELETE",
		url :  userServiceURl,
		data : JSON.stringify(taHourIdArg),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			
				$("#btn-add-new-ta-schedule").show();
				$("#btn-edit-ta-schedule").hide();
				$("#btn-delete-ta-schedule").hide();
				refreshCourseList();
				$("#btn-save-ta-schedule").hide();
		},
		failure : function () {
			console.log("failed");
		}
	});
}


function updateExistingTaHour(taHourIdArg){

	console.log("updateExistingTaHour-fn-clicked");
	userServiceURl =  applicaitonURL +  "/jwsTaHours/updateExistingTaHour/"+taHourIdArg;
	
	var courseId = parseInt($("#dd-course-list").children(":selected").attr("value"));
	var taStarTime= $("#txt-ta-start-time").val();
	var taEndTime  = $("#txt-ta-end-time").val();		
	var taHourLocation = $("#txt-ta-location").val();
	var taHourDay = $("#ta-hour-day").children(":selected").attr("value");
	var userId = <%= userId %>;
	
	var taHour = new Array();	 
	var taHour=[];
     
	taHour.push({
		"taHourId" :taHourIdArg,
			"courseId":courseId,
			"taHourDay" :taHourDay,
			"taHourEndTime"  :taEndTime,
			"taHourLocation" :taHourLocation,
			"taHourStartTime" : taStarTime,
			"taId" : userId
			});
	
	console.log(taHour);
	
		$.ajax({
			type : "PUT",
			url :  userServiceURl,
			data : JSON.stringify(taHour[0]),
			dataType:"JSON",
			contentType: "application/json",
			success : function (result) {				
				var courseId = $("#dd-course-list").children(":selected").attr("value");			
				findTaHoursByCourseId(parseInt(courseId));
				$("#btn-add-new-ta-schedule").hide();
				$("#btn-edit-ta-schedule").show();
				$("#btn-delete-ta-schedule").show();
				$("#btn-save-ta-schedule").hide();
				disableAllFields();				
			},
			failure : function () {
				console.log("failed");
			}
		});
	}

function disableAllFields(){	
    $("#txt-ta-start-time").attr("disabled",true);
    $("#txt-ta-end-time").attr("disabled",true);		
	$("#txt-ta-location").attr("disabled",true);
	$("#ta-hour-day").attr("disabled",true);  
	
}

function enableAllFields(){
        $("#txt-ta-start-time").attr("disabled",false);
	    $("#txt-ta-end-time").attr("disabled",false);
		$("#txt-ta-location").attr("disabled",false);
		$("#ta-hour-day").attr("disabled",false); 	
}
	




</script>


