<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
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
<th>Add</th>
</tr>
<m:forEach items="${vendorItem}" var="i">
<tr>
<th>${i.getItemName()}</th>
<th>${i.getPrice()}</th>
<th>
<form action="/addToCart" method="post"><input type="submit" value="AddToCart"/>
<input type="hidden" name="vendorItemId" value="${i.getVendorItemId()}">
<input type="hidden" name="vendorId" value="${vendor.getUserId()}">
</form>
</th>
</tr>
</m:forEach>

</table>
<form action="/viewOrder" method="post">
<input type="submit" value="viewOrder">
<input type="hidden" name="vendorId" value="${vendor.getUserId()}">
<input type="hidden" name="cart" value="${cart.getVendorItemId()}">

</form>

</body>
</html>