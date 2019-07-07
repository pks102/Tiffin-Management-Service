<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/updatePassword" method="post">
<input type="hidden" name="userId" value="${user.getUserId()}">

New Password:
<input type="password" name="newPassword"/><br>
Confirm Password:
<input type="password" name="confirmPassword"/><br>
<input type="submit" value="update"/>

</form>
</body>
</html>