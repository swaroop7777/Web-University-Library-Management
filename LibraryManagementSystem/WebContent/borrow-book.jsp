<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Borrow Book</title>
<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

<!-- Compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="header center-align indigo darken-1"
			style="margin-bottom: 20px; padding-bottom: 10px; padding-top: 10px; margin-top: 20px;">
			<h2>
				<span class="lime-text text-lighten-5">FooBar University
					Library</span>
			</h2>
		</div>
	</div>

	<div class="container">
		<div class="header left-align brown darken-2"
			style="margin-bottom: 20px; margin-right: 630px; padding-bottom: 2px; padding-top: 2px; margin-top: 20px;">
			<h4>
				<span class="lime-text text-lighten-5">Borrow Book Form</span>
			</h4>
		</div>
		<form action="LibraryServlet" method="post">
			<input type="hidden" name="command" value="BOR_BK">
			<div class="row">
				<div class="input-field col s4">
					<input placeholder="Enter Book ID" name="bid" type="number"
						class="validate"> <label for="Book-id"><b><i>Book
								ID</i></b></label>
				</div>
				<div class="input-field col s4">
					<input placeholder="Enter Student ID" name="sid" type="number"
						class="validate"> <label for="student-id"><b><i>Student
								ID</i></b></label>
				</div>
				<div class="input-field col s4">
					<input placeholder="Enter Librarian ID" name="lid" type="number"
						class="validate"> <label for="lib-id"><b><i>Librarian
								ID</i></b></label>
				</div>
			</div>

			<div class="center-align">
				<button class="btn light-blue accent-3" type="submit" value="Save"
					class="save">
					Submit <i class="material-icons right">send</i>
				</button>
			</div>
		</form>
		<div class="main-menu left-align">
			<a class="waves-effect waves-light btn-small"
				onclick="window.location.href='librarian-operations.jsp'; return false;">Back
				To Menu</a>
		</div>
	</div>
</body>
</html>