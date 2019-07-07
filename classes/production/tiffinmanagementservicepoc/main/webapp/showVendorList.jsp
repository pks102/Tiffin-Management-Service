<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="m" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border="1">
<tr>
<th>Vendors List</th>

</tr>

<m:forEach items="${vendorList}" var="i">
<tr>
<th><form action="/showVendorItems" method="post"><input type="submit" value="${i.getName()}"/><input type="hidden" name="userId" value="${i.getUserId()}"></form></th>
</tr>
</m:forEach>

</table>
</body>
</html>