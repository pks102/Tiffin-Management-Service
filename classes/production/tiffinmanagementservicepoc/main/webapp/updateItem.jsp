<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form method="post" action="/updateItem" modelAttribute="vendorItem">

		<table border="1" align="center" width="400" bgcolor="#CCCCCC">


			<tr>

				<th>Item name</th>

				<td><form:input  path="itemName" 
					placeholder="enter your name"  /></td>

			</tr>
			<form:hidden path="vendorItemId" />
			<tr>

				<th>Item Price</th>

				<td><form:input path="price" /></td>

			</tr>
			<tr>

				<td colspan="2" align="center"><input  type="submit" 
					value="Update data" /> 
			</tr>
		</table>

	</form:form>
			
</body>
</html>