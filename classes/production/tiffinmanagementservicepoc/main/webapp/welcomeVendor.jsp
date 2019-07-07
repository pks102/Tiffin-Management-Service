<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
welcome hello vendor:
 ${user.getUserName()} ${user.getEmailId()}
<form action="/editUser" method="post">
<input type="submit" value="edit">
<input type="hidden" name="userId" value="${user.getUserId()}">
</form>

<form action="/item" method="post">
<input type="hidden" name="userId" value="${user.getUserId()}">
<input type="submit" value="AddItem">
</form>
<form action="/viewItems" method="post">
<input type="hidden" name="userId" value="${user.getUserId()}">
<input type="submit" value="ViewItem">

</form>

</body>
</html>