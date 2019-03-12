<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form:form method="post" action="/updateUser" modelAttribute="user">

		<table border="1" align="center" width="400" bgcolor="#CCCCCC">


			<tr>

				<th>first name</th>

				<td><form:input path="name" placeholder="enter your name" /></td>

			</tr>

			<tr>

				<th>UserName</th>

				<td><form:input path="userName" /></td>

			</tr>
			<tr>

				<th>Enter your email</th>

				<td><form:input path="emailId" /></td>

			</tr>

			<tr>

				<th>Enter your mobile</th>

				<td><form:input path="contactNo" /></td>

			</tr>

			<tr>

				<th>Enter your address</th>

				<td><form:textarea rows="8" cols="20" path="address"></form:textarea></td>

			</tr>


			<tr>

			</tr>
			<form:hidden path="userId" />
			<tr>
			</tr>

			<tr>

				<td colspan="2" align="center"><input type="submit"
					value="Save My Data" />
			</tr>
		</table>

	</form:form>
	
	<form action="/changePassword" method="Post">
<input type="submit" value="editPassword">
<input type="hidden" name="userId" value="${user.getUserId()}">
</form>
</body>
</html>