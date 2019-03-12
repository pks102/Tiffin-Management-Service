<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/signup">

		<table border="1" align="center" width="400" bgcolor="#CCCCCC">

			<caption>Registration form</caption>

			<tr>

				<th>Enter your first name</th>

				<td><input type="text" name="name" title="enter your  name"
					placeholder="enter your name" required /></td>

			</tr>

			<tr>

				<th>Enter your UserName</th>

				<td><input type="text" name="userName" /></td>

			</tr>

			<tr>

				<th>Enter your password</th>

				<td><input type="password" name="password" /></td>

			</tr>


			<tr>

				<th>Enter your email</th>

				<td><input type="email" name="emailId" /></td>

			</tr>

			<tr>

				<th>Enter your mobile</th>

				<td><input type="text" name="contactNo" /></td>

			</tr>

			<tr>

				<th>Enter your address</th>

				<td><textarea rows="8" cols="20" name="address"></textarea></td>

			</tr>

			<tr>

				<td><input type="radio" name="utype" value="customer">
					Customer <input type="radio" name="utype" value="vendor">
					Vendor</td>
			</tr>


			<tr>


			</tr>

			<tr>

				<td colspan="2" align="center"><input type="submit"
					value="Save My Data" /> <input type="reset" value="Reset Data" /></td>
			</tr>
		</table>

	</form>

</body>
</html>