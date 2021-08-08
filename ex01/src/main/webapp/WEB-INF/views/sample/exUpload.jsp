<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
<form action="/sample/exUploadPost" method="post" enctype="multipart/form-data">
	<div>
		<input type='file' name="files">
	</div>
	<div>
		<input type='file' name="files">
	</div>
	<div>
		<input type='file' name="files">
	</div>
	<div>
		<input type='file' name="files">
	</div>
	<div>
		<input type='file' name="files">
	</div>
	<div>
		<input type='submit' >
	</div>
	<!-- controller 처리메소드 작성 -->
</form>



</body>
</html>