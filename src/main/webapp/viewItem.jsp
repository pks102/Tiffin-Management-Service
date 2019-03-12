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
<th>Edit</th>
<th>Delete</th>
</tr>
<m:forEach items="${list}" var="i">
<tr>
<th>${i.getItemName()}</th>
<th>${i.getPrice()}</th>
<th><form action="/editItem" method="post"><input type="submit" value="Edit"/><input type="hidden" name="vendorItemId" value="${i.getVendorItemId()}"></form></th>
<th><form action="/deleteItem" method="post"><input type="submit" value="Delete"/><input type="hidden" name="vendorItemId" value="${i.getVendorItemId()}"></form></th>
</tr>
</m:forEach>
</table>
</body>
</html>