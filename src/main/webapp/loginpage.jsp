<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/signin">

		<table border="1" align="center" width="400" bgcolor="#CCCCCC">

			<caption>Login Page</caption>

			<tr>

				<th>Enter your User name</th>

				<td><input type="text" name="username" title="enter your Username"
					placeholder="enter your Username" required /></td>

			</tr>
					
			<tr>
			
				<th>Enter your Password</th>

				<td><input type="password" name="password" /></td>

			</tr>
			<tr>
			<td><input type="submit" value="submit"> </td>
			</tr>
			</table>
			</form>
</body>
</html>