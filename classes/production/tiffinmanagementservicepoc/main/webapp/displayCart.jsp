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
<th>ItemName:</th>
<th>Price</th>

</tr>
<m:forEach items="${vendorItem}" var="i">
<tr>
<th>${i.getItemName()}</th>
<th>${i.getPrice()}</th>

</tr>
</m:forEach>

</table>

</body>
</html>