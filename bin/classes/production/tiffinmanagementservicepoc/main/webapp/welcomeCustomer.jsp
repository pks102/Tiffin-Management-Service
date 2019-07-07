<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Welcome Customer ${user.getName()} ${user.getEmailId()}
<form action="/editUser" method="post">
<input type="submit" value="edit">
<input type="hidden" name="userId" value="${user.getUserId()}">
</form>
<form action="/showVendors" method="post">
<input type="submit" value="Buy Food">
<input type="hidden" name="userId" value="${user.getUserId()}">
</form>
</body>
</html>