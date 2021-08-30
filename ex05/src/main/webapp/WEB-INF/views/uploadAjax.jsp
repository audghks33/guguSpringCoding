<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">

.uploadResult{
	width:100%;
	background-color : gray;
}

.uploadResult ul{
	display: flex;
	flex-flow :row;
	justify-content:center;
	align-items:center;
}

.uploadResult ul li {
	list-style : none;
	padding: 10px;
}

.uploadResult ul li img{
	width: 20px;
}

</style>
</head>
<body>
<h1> upload with Ajax</h1>

<div class="uploadDiv">
	<input type='file' name='uploadFile' multiple>
</div>

<div class='uploadResult'>
	<ul>
	
	</ul>
</div>

<!-- jQuery 라이브러리의 경로를 추가하고 <script>를 이용해서 첨부파일을 처리 jquery cdn 참조 -->
<button id="uploadBtn">Upload</button>



<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>


<script>
	$(document).ready(function(){
		
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|mp4)$");
		var maxSize = 5242880; //5MB
		
		function checkExtension (fileName, fileSize){
			
			console.log( ""+ "파일 이름");
			
			if(fileSize >= maxSize) {
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			
			return true;
		}
	//input type = "file" 이 readonly 안쪽이라 수정 할 수 없기 때문에 별도의 방법으로 초기화 시켜서 다른 첨부파일을 추가 할 수 있도록 해야함.
	var cloneObj = $(".uploadDiv").clone();
		
	$("#uploadBtn").on("click", function(e){
			
			var formData = new FormData();
			
			var inputFile = $("input[name='uploadFile']");

			var files = inputFile[0].files;
			
			console.log(files);
		//jquery를 이용하는 경우 파일 업로드는 FormData 라는 객체를 이용( 브라우저 제약이 있음 ) = <form> 느낌
		//확인 후 다음 줄 추가
		
	var uploadResult = $(".uploadResult ul");
		
		function showUploadedFile(uploadResultArr){
			
			var str ="";
			
			$(uploadResultArr).each(function(i, obj){
				
				if(!obj.image){
					//이미지가 아니면
				str += "<li><img src='/resources/img/attach.png'>" + obj.fileName + "</li>";
				}else {
					//이미지이면 후에 섬네일을 붙여줌.
					/* str += "<li>"+ obj.fileName +"</li>"; */
					var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
					
					str+= "<li><img src='/display?fileName="+fileCallPath+"'></li>" ;
				}
			});
			
			uploadResult.append(str);
		}
		
	if(!files.length == 0){
		for(var i = 0 ; i< files.length; i++){
			
			if(!checkExtension(files[i].name, files[i].size) ){
				return false;
			}
			
			formData.append("uploadFile", files[i]);
		}
	}else {
		alert("파일을 추가해주세요");
		return false;
	}
		
		
			$.ajax({
				url: '/uploadAjaxAction',
				processData:false,
				contentType:false,
				data: formData,
				type: 'POST',
				dataType:'json',
				success: function(result){
					alert("올라갔다");
					console.log(result);
					//uploadcontroller
					
					showUploadedFile(result);
					
					$(".uploadDiv").html(cloneObj.html());
				}
			//파일은 잘 전송되지만 
			//개발자도구에서 에러 표시가 뜬다. -- 단순히 jsp가 없어서 안된 것
				
			});
			
		});
		
	
	});
</script>
</body>
</html>