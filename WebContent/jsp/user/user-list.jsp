<%@ include file="../common.jsp"%>
<%@ include file="../check-login.jsp"%>

<div id="user-list" class='well well-lg'>

</div>

<script type="text/javascript">
	var defaultImage = "iVBORw0KGgoAAAANSUhEUgAAAH8AAAB/CAMAAADxY+0hAAAAMFBMVEXFxcX////CwsK/v7/6+vrj4+PLy8vy8vLv7+/s7OzS0tLa2trV1dXp6enm5ubd3d21m/gPAAACzElEQVRoge2ayZKDMAxEsQxmy/L/fzshVCoMA1hqWknVlN8h13ZkbZaoqkKhUCgUCoVCoaBBRGKMj9+vaKdhbK5d1/XN/ZKqj55BqtS0dVjQ3oaPHUFkbMNf6uYjJxBpNsRn+uR+AhnrXfkHjbN66o7UJ0fwNIEMh39+ZnQ7gFzy6tMdRCf5USUfwtXFAsp//7SAwwFkUMuHcHc4gML13iS2umylvAPY8lrfe0H2QTHKk2/gIOfv0TENkMzyIVx4BwD+/qMYEg1gir0XA03eknre0LKg3CD9mlWHBDI/LwQR758gdQKGwvebG0n/Duq3HAeQK6hPcsCY6zl3IekbSy9dHwy/f6P/ZftL/2V9pPpOsOLf2vu9YPWAWPklvgLAAGA1IGACrEnyaAHqae/gBF3AhSWPZQCa+SssAqhvYCAFUx9g9haQOwIw90A1ewJh1B/I+sYicKPPwEyPoNZjAKV3AWboL9CWgZo+fTIdwE1el4dbN3lVFJAefXsHSMeZuCZOfbYPEI8WEI33Lkyq5sgAre8OKKa8A/bJafxfyaCLv46d+2f1pM9/V/4SKLP3WlGTl0BiboBapiNCEyheJgAfoKz5J/z+Jk2g4fFT6Bjy8PQjTF54Gnj48+R0J3Tm35+3gOB3/+LMHiiik58l+DoaHjz/Bn2JmTa+R6D18Jzrv8GCANz6bIH0pDTrTyA3wLL+hP0G4KHrNuZaiO68drC+ySI8897BOAxkOt+MaRiLr5x26SwGIN/+E4MHwBu/IwzbAI+/bzAAqe6t0dfBs03PNupWiB98M8oQxPdtGbTvAWblWaKsQujnBnlUGxFi37FG1Yfg6+Ysqg8SfJLPjCIFwdtWDYqxiOP1qxzA+qGjCc1ewFFe81mmp/spHBD+2ElHdihF7rvXZPtwl9bnTb4J4neeS/ITKc/w0/Qgftl/IluCsU8N9PrrAPwBbzMhCytB1DwAAAAASUVORK5CYII=";
	$.ajax({
		url : "http://localhost:8080/LMS/api/jwsUserService/findAllUsers",
		type : "post",
		contentType : "application/json",
		success : function(userList){
			$.each(userList, function(i, user){
				var image= "";
				if(user.userImage == null)
					image = defaultImage;
				else
					image = user.userImage;
				$("#user-list").append(
					"<span class='well well-sm'>" + 
					"<img style='width: 80px; height: 80px;' src='data:image/jpeg;base64," + image + "' alt='No image'/>" + 
					"<a href='javascript:void(0)' onClick='viewUser(" + user.userId + ")'> " + user.userName + "</a>" + 
					"</br></br></span>"
				);
			});
		},
		failure : function(){
			console.log("findAllUser service failed...");
		}
	});
	
</script>