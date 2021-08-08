<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" import ="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h4><c:out value="${exception.getMessage()}"></c:out></h4>

<ul>
	<c:forEach items="${exception.getStackTrace() }" var="stack">
		<li><c:out value="${stack }"></c:out></li>
	</c:forEach>
	<!-- 이후 모든 요청이 dispatcher에서 일어나니  404에러도 같이 처리할 수 있도록 web.xml 수정-->
</ul>
</body>
</html>