<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
	%>
	<c:forEach items="${list}" var="name">
		<div style="width: 666px;height: 460px;float: left;">
			<img style="width: 666px;height: 460px;" src='<%=request.getContextPath()%>/user/getFile.do?fileUrl=${name}' />
		</div>
	</c:forEach>
</body>
</html>