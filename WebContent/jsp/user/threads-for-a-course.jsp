<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>
<link href="/LMS/css/jquery-te-1.4.0.css" rel="stylesheet">
 <% 
String path = (String) request.getAttribute("path");
//String threadId =console.log(path.split('=')[1]);
%> 
<script src="/LMS/js/jquery-te-1.4.0.min.js" type="text/javascript"></script>
<html>
<input id="hdn-Path" type="hidden" name="hdn-Path" value="<%= path %>" />
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
	<div class="container">
		<div>
			<select id="course-list" class="form-control">
				
			</select> <br />			
		</div> <br />		
	</div>
	<table  class="table table-striped">
		<tr><th style="width:350px"> Threads Preview</th><th> Thread and Response</th></tr>
		
		<tr style="height:650px;">
		
		<td><table  id="tbl-all-threads" class="table table-striped">
			
		</table></td>
		<td><table id="tbl-thread-desc" class="table table-striped"></table>
			<div class="container" id="create-thread-form" style="display:none;">
				<h3>START YOUR THREAD</h3>
				<form action="" method="post" class="well">
					<div id="tag-container" class="container">
						<div id="tags"></div>
						<div id="create-tag-container" style="display:none;">
							<input type="text" value="" id="tag-text" class="form-control" style="width: 15%;"/>
							<input type="button" value="ADD" class="btn btn-success" onClick="createTag()" style="float:left;" />
							<input type="button" value="CANCEL" class="btn btn-warning" onClick="hideCreateTagContainer()" style="float:left;" />
						</div>
						<% if(!role.equalsIgnoreCase("STUDENT")) { %>
							<a href="javascript:void(0)" id="add-tag-btn" onClick="showCreateTagContainer()">ADD TAG</a>
						<% } %>
					</div>
					<input type="text" value="" class="form-control" id="thread-title" placeholder="THREAD TITLE"/>
					<textarea name="textarea" id="thread-content" style="height: 100px;"></textarea>
					<input class="btn btn-success" type="button" value="SUBMIT THREAD" style="" onClick="createThread()"/>
					<input class="btn btn-warning" type="button" value="CANCEL" style="" onClick="hideNewPostContainer()"/>
				</form>
			</div>
		</td>
		</tr>

		</table>
</body>
</html>

<script type="text/javascript">

var applicaitonURL = "/LMS/api";
var userServiceURl ;
var threadService = applicaitonURL + "/jwsThreadService";
var tagService = applicaitonURL + "/jwsTagService";

var threadIdFromPath = getUrlParams($("#hdn-Path").val()).threadId;
var courseIdFromPath = getUrlParams($("#hdn-Path").val()).courseId;
$(document).ready(function(){
	
	refreshCourseList();
	$('#thread-content').jqte();
	loadTags();
});


function refreshCourseList(){
	userServiceURl = applicaitonURL + "/jwsCourseService/findAllCoursesForAUserId/"+<%= userId %>;
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data: "<%= userId %>",
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			$.each(result, function(i, val){
					$("#course-list").append(
							"<option id='course-" + val.courseId + "' value='"+val.courseId+"'>" + val.courseName + "</option>");
				});
			
			if(threadIdFromPath != undefined && courseIdFromPath != undefined)
				{				
					$("#course-list").val(parseInt(courseIdFromPath));
					loadAllThreadsByCourseId(parseInt(courseIdFromPath));
				}
			else
				{
					
					var courseId = $("#course-list").children(":selected").attr("value");
					loadAllThreadsByCourseId(courseId);
				}
		},
		failure : function () {
			console.log("failed");
		}
	});
}


function loadAllThreadsByCourseId(courseId){
userServiceURl =  applicaitonURL + "/jwsThreadService/findThreadsByCourseId/"+courseId;
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data: JSON.stringify(courseId),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			$("#tbl-all-threads").children().remove();
			$("#tbl-all-threads").html('');
			$("#tbl-all-threads").append(getNewPostBtn());
			$("#tbl-thread-desc").children().remove();
				if(result.length > 0){	
					$.each(result, function(i, val){				
						$("#tbl-all-threads").append(
								"<tr style='height:120px;'> <td  onClick='clickOnThread("+val.threadId+")'> <span class='spanClass'  id='"+ val.threadId +"'>"+ val.threadTitle+ "</span></td></tr>");
					});
				
					if(threadIdFromPath != undefined && courseIdFromPath != undefined)
					{			
						loadAThreadAndItsAllPosts(parseInt(threadIdFromPath));
						threadIdFromPath = undefined;
						courseIdFromPath = undefined;
					}
					else
					{
						var firstThreadIdInList = $("#tbl-all-threads span:first").attr("id");
						loadAThreadAndItsAllPosts(firstThreadIdInList);
						
					}
				
				}
		},
		failure : function () {
			console.log("failed");
		}
	});
}
	


function loadAThreadAndItsAllPosts(threadId){
	console.log(threadId);
	userServiceURl = applicaitonURL + "/jwsThreadService/findAThreadByThreadId/"+threadId;
	$.ajax({
		type : "GET",
		url :  userServiceURl,
		//data : JSON.stringify(threadId),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			console.log("loadAThreadAndItsAllPosts");
			console.log(result);
			$("#tbl-thread-desc").children().remove();	
			appendTagsOnLoadingAThread(result.tags);
			
				 $("#tbl-thread-desc").append(
						"<tr style='height:120px;'> <td> <a href='#' id='thread-" + result.threadId + "'>"+ result.threadContent+ "</a></td></tr>"+
						"<tr style='height:5px;'> <td> <span id='span-" + result.threadId + "'>Created on :"+ result.threadDate+ "</span>&nbsp;"
						 +  getFavBtnForThread(result.favUsers, result.threadId) + "</td></tr>"		
				 );
				 console.log()
				$.each(result.posts, function(i, val){
					console.log(val.usersWhoHaveLikedPosts);
						if(doesLoggedInUserIExistInThisUser (<%= userId %>,val.usersWhoHaveLikedPosts) == 1)
						{								
							getLikedButton(val,result.threadId);				
						}
					else
						{
						getUnLikedButton(val,result.threadId);
						}				 
			});
				
				 $("#tbl-thread-desc").append(
							"<tr style='height:120px;'><td>"+
							"<textarea name='textarea' id='txt-replyToThread' style='height: 100%;width:100%'></textarea>"+
							"<input id='btn-submit-post' onClick='submitpost("+threadId+")' class='btn btn-primary' type='submit' value='Post' style='' />"+							
							"</td></tr>");
				 $('#txt-replyToThread').jqte();
		},
		failure : function () {
			console.log("failed");
		}
	});
	
}

function appendTagsOnLoadingAThread(tags){
	$("#tbl-thread-desc").children().remove();
	$.each(tags, function(i, tag){
		$("#tbl-thread-desc").append("<label class='tagClass'>&nbsp" + tag.tagText + "&nbsp</label> ||")
	})
	

}


function doesLoggedInUserIExistInThisUser (userId,allUsers){
	var isLiked=0;
	
	$.each(allUsers, function(i, aUser){
		if(userId==aUser.userId)
			isLiked=1;
			return isLiked;			
	});
	return isLiked;
}


function getLikedButton(val,threadId){
$("#tbl-thread-desc").append(
		"<tr style='height:120px;'> <td> <a href='#' id='post-" + val.postId + "'>"+ val.postContent +"</a></td>"+
		"<td> " + getUserDisplayLink(val) +  
		"<button class='btn btn-success' onClick='unlikePost("+ val.postId +","+ threadId +")' id='like-post-" + val.postId + "'>Click to Unlike</button></td>"+
		"</tr><br/>");
}

function getUnLikedButton(val,threadId){
	$("#tbl-thread-desc").append(
			"<tr style='height:120px;'> <td> <a href='#' id='post-" + val.postId + "'>"+ val.postContent +"</a></td>"+
			"<td> " + getUserDisplayLink(val) +
			"<button class='btn btn-warning' onClick='likePost("+ val.postId +","+ threadId +")' id='unlike-post-" + val.postId + "'>Click to Like</button></td>"+
			"</tr><br/>");
}

function getUserDisplayLink(post){
	return "<a href='javascript:void(0)' onClick='viewUser(" + post.userId + ")'> View poster profile </a><br/>";
}

function clickOnThread(threadId) {
	loadAThreadAndItsAllPosts(threadId);
}


$("#course-list").change(function() {
	var courseId = $(this).children(":selected").attr("value");
	loadAllThreadsByCourseId(courseId);
});


function submitpost(threadId){
	userServiceURl = applicaitonURL + "/jwsPostService/createPost";	 
	var postContent=$("#txt-replyToThread").val();
    var userId = <%= userId %>;
    var threadId=threadId;
    var post={"postContent" : postContent,  "userId" : userId,"threadId" : threadId};
	$.ajax({
		type : "POST",
		url :  userServiceURl,
		data : JSON.stringify(post),
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) {
			
			console.log("submitted");
			loadAThreadAndItsAllPosts(threadId);
				
		},
		failure : function () {
			console.log("failed");
		}
	});
	
}

function showNewPostContainer(){
	$("#tbl-thread-desc").hide();
	$("#create-thread-form").show();
}

function hideNewPostContainer(){
	$("#tbl-thread-desc").show();
	$("#create-thread-form").hide();
}

function createThread(){
	$.ajax({
		type : "post",
		url : threadService + "/createThread",
		data : JSON.stringify(getThreadData()),
		dataType:"JSON",
		contentType: "application/json",
		success : function(result){
			hideNewPostContainer();
			loadAllThreadsByCourseId(result.courseId);
		  	$("#thread-title").val('');
		   	$(".jqte_editor").html('');
		},
		failure : function(){
			console.log("Error while saving Thread...");
		}
	});
}

function createTag(){
	$.ajax({
		type : "post",
		url : tagService + "/createTag",
		data : JSON.stringify(getTagData()),
		dataType:"JSON",
		contentType: "application/json",
		success : function(tag){
			appendTag(tag.tagId, tag.tagText);
			hideCreateTagContainer();
		},
		failure : function(){
			console.log("Error while saving Tag...");
		}
	});
}

function loadTags(){
	$.ajax({
		type : "get",
		url : tagService + "/findAllTags",
		dataType:"JSON",
		success : function(tags){
			$("#tags").html("");
			$.each(tags, function(i, tag){
				appendTag(tag.tagId, tag.tagText);
			});
		},
		failure : function(){
			console.log("Error while loading Tag...");
		}
	});
}


function appendTag(tagId, tagText){
	$("#tags").append("<span class='tagClass'>&nbsp" + 
						"<input type='checkbox' value='" +tagId + "_" + tagText + "' class='form-field' />" + tagText  
						+ "&nbsp</span> |")
	if($("#tags").find(".tagClass").length % 10 == 0)
		$("#tags").append("<br/>");
}


function getTagData(){
	return {"tagText" : $("#tag-text").val()}
}

function getThreadData(){
	return {
		"userId" : "<%= userId %>",
		"courseId" : $("#course-list").children(":selected").attr("value"),
		"threadTitle" : $('#thread-title').val(),
		"threadContent" : $('#thread-content').val(),
		"tags" : getTags()
		}
}

function showCreateTagContainer(){
	$("#create-tag-container").show();
	$("#add-tag-btn").hide();
}

function hideCreateTagContainer(){
	$("#create-tag-container").hide();
	$("#add-tag-btn").show();
}

function getTags(){
	var tags = [];
	$.each($("#tags :checked"), function (i, tag){
		var s = tag.value.split("_");
		tags.push({ "tagId" : s[0], "tagText" : s[1]});
		s = [];
	});
	return tags;
}

function getNewPostBtn(){
	return '<tr> <td> <input type="button" value="NEW THREAD" class="btn btn-success" onClick="showNewPostContainer()" />'+
	'<input id="txt-search" type="text"  style="margin-left:4px;width:55%;" placeholder="Search:Enter keywords" />'+
	'<button id="btn-txt-search" onClick="searchThread()">search</button></td>'+
	'</tr>';	
}


function likePost(postId,threadId){
	userServiceURl = applicaitonURL + "/jwsPostService/likeAPost";	
	 $.ajax({
		type : "POST",
		url : userServiceURl,
		data: JSON.stringify({"postId" : postId, "userId" : <%= userId %>}),
		dataType:"JSON",
		contentType: "application/json",
		success : function(post){
		
			loadAThreadAndItsAllPosts(threadId);
			
		},
		failure : function(){
			console.log("Error while liking a Post...");
		}
	});	
}

function unlikePost(postId,threadId){
	userServiceURl = applicaitonURL + "/jwsPostService/unLikeAPost";	
	 $.ajax({
		type : "POST",
		url : userServiceURl,
		data: JSON.stringify({"postId" : postId, "userId" : <%= userId %>}),
		dataType:"JSON",
		contentType: "application/json",
		success : function(post){
		
			loadAThreadAndItsAllPosts(threadId);
		},
		failure : function(){
			console.log("Error while liking a Post...");
		}
	}); 	
}

function addToFav(threadId){
	$.ajax({
		type : "post",
		url : threadService + "/setFavThreadForUser",
		data : JSON.stringify({"threadId" : threadId, "userId" : <%= userId %>}),
		dataType:"JSON",
		contentType: "application/json",
		success : function(thread){
			$("#thread-fav-btn").attr("class", "btn btn-warning");
			$("#thread-fav-btn").attr("value", "UNDO FAVORITE");
			$("#thread-fav-btn").attr("onClick", "undoFav(" + thread.threadId + ")");
		},
		failure : function(){
			console.log("Error while saving Tag...");
		}
	});
}

function undoFav(threadId){	
	$.ajax({
		type : "DELETE",
		url : threadService + "/undoFavThreadForUser",
		data : JSON.stringify({"threadId" : threadId, "userId" : <%= userId %>}),
		dataType:"JSON",
		contentType: "application/json",
		success : function(thread){
			console.log("SUCCESS!");
			console.log(thread);
			$("#thread-fav-btn").attr("class", "btn btn-success");
			$("#thread-fav-btn").attr("value", "ADD TO FAVORITE");
			$("#thread-fav-btn").attr("onClick", "addToFav(" + thread.threadId + ")");
		},
		failure : function(){
			console.log("Error while saving Tag...");
		}
	});
}

function getFavBtnForThread(userJson, threadId){
	if(userExistsInJson(userJson))
		return getUndoFavoriteBtn(threadId);
	else
		return getFavoriteBtn(threadId);
}

function userExistsInJson(userJson){
	var isFav = false;
	$.each(userJson, function (i, user) {
		if(user.userId == <%= userId %>){
			isFav = true;
			return isFav;
		}	
	});
	return isFav;
}


function getFavoriteBtn(threadId){
	return '<input type="button" value="ADD TO FAVORITE" id="thread-fav-btn" class="btn btn-success" onClick="addToFav(' + threadId + ')" />';
}

function getUndoFavoriteBtn(threadId){
	return '<input type="button" value="UNDO FAVORITE" id="thread-fav-btn" class="btn btn-warning" onClick="undoFav(' + threadId + ')" />';
}

function getUrlParams(url) {
    var params = {};
    url.substring(1).replace(/[?&]+([^=&]+)=([^&]*)/gi,
            function (str, key, value) {
                 params[key] = value;
            });
    return params;
}

$("#txt-search").keyup(function(){
	  $("#txt-searcht").css("background-color","pink");
	  
	  var keyword = $("#txt-search").val();
	  
		
		
	}); 

function searchThread() {
	var userId= parseInt(<%= userId %>);
	var courseId = parseInt($("#course-list").children(":selected").attr("value"));
	var keyword = $("#txt-search").val();
	searchThreadHelper(courseId, userId, keyword)
}



	function searchThreadHelper(courseId, userId, keyword){
	userServiceURl = applicaitonURL + "/jwsThreadService/findAllThreadsByAKeyWord/"+courseId+"/"+keyword;
	console.log({"courseId":courseId, "keyword" : keyword, "userId" :userId});
	$.ajax({
		type : "GET",
		url :  userServiceURl,
	//	data: {"courseId":courseId, "keyword" : keyword, "userId" :userId},
		dataType:"JSON",
		contentType: "application/json",
		success : function (result) { 
			$("#tbl-all-threads").children().remove();
			$("#tbl-all-threads").html('');
			$("#tbl-all-threads").append(getNewPostBtn());
			$("#tbl-thread-desc").children().remove();
				if(result.length > 0){	
					$.each(result, function(i, val){				
						$("#tbl-all-threads").append(
								"<tr style='height:120px;'> <td  onClick='clickOnThread("+val.threadId+")'> <span class='spanClass'  id='"+ val.threadId +"'>"+ val.threadContent+ "</span></td></tr>");
					});
					
				
					if(threadIdFromPath != undefined && courseIdFromPath != undefined)
					{			
						loadAThreadAndItsAllPosts(parseInt(threadIdFromPath));
						threadIdFromPath = undefined;
						courseIdFromPath = undefined;
					}
					else
					{
						var firstThreadIdInList = $("#tbl-all-threads span:first").attr("id");
						loadAThreadAndItsAllPosts(firstThreadIdInList);
						
					}
				
				}
		},
		failure : function () {
			console.log("failed");
		}
	});
	}

</script>