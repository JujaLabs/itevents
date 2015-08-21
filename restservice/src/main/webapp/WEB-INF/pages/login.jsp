<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="resources/css/home.css" rel="stylesheet"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>


</head>

<body>
<form method="POST" action="login" class="box login">
	<fieldset class="boxBody">
		<label> Username </label> <input type='text' name='user_login' value=''>
		<label> Password </label> <input type='password' name='password_login'/>
	</fieldset>
	<footer>
		<input type="submit" class="btnLogin" value="Submit">
		<c:if test="${not empty error}">
			<span class="error">${error}</span>
		</c:if>
		<c:if test="${not empty denied}">
			<span class="error">${denied}</span>
		</c:if>
	</footer>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>