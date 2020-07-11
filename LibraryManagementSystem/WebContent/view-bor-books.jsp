<%@ page import="java.util.*,com.abc.librarymgmt.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Borrowed Books and student ID's</title>
<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

<!-- Compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"> 
</head>

<body>
 <div class="container">
	<div class="header center-align indigo darken-1" style="
		margin-bottom:20px;padding-bottom:10px;padding-top:10px;margin-top:20px;">
		<h2><span class="lime-text text-lighten-5">FooBar University</span></h2>
	</div>
</div>	
	<div class="container" style="
		margin-bottom:20px;padding-bottom:10px;padding-top:10px;margin-top:20px;">
		<table class="striped">
		          <tr>
					<th>Student ID</th>
					<th>Book ID</th>
					<th>Borrow Date</th>
					<th>Return Date</th>
					<th>Librarian id</th>
				  </tr>
			<c:forEach var="tempBook" items="${BorBookDetails}">											
					<tr>
						<td> ${tempBook.id} </td>
						<td> ${tempBook.bid} </td>
						<td> ${tempBook.first_name} </td>
						<td> ${tempBook.last_name} </td>
						<td> ${tempBook.lid} </td>
						<td>
					</tr>
				
			</c:forEach>
		</table>
		<div class="main-menu left-align">
				<a class="waves-effect waves-light btn-small"
					onclick="window.location.href='librarian-operations.jsp'; return false;">Back To Menu</a>
	</div>
	</div>
	
</body>
</html>