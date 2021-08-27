<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> upload with Ajax</h1>

<div class="uploadDiv">
	<input type='file' name='uploadFile' multiple>
</div>
<!-- jQuery 라이브러리의 경로를 추가하고 <script>를 이용해서 첨부파일을 처리 jquery cdn 참조 -->
<button id="uploadBtn">Upload</button>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>


<script>
	$(document).ready(function(){
		
		$("#uploadBtn").on("click", function(e){
			
			var formData = new FormData();
			
			var inputFile = $("input[name='uploadFile']");

			var files = inputFile[0].files;
			
			console.log(files);
		//jquery를 이용하는 경우 파일 업로드는 FormData 라는 객체를 이용( 브라우저 제약이 있음 ) = <form> 느낌
		//확인 후 다음 줄 추가
		
		for(var i = 0 ; i< files.length; i++){
			formData.append("uploadFile", files[i]);
		}
		
			$.ajax({
				url: '/uploadAjaxAction',
				processData:false,
				contentType:false,
				data: formData,
				type: 'POST',
				success: function(result){
					alert("올라갔다");
					//uploadcontroller
				}
			//파일은 잘 전송되지만 개발자도구에서 에러 표시가 뜬다.
				
			});
		
		});
	});
</script>
</body>
</html>