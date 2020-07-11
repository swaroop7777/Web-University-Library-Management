<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Home Page</title>
<!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</head>
<body>
	
<div class="container" style="background-color:gray;margin-bottom:100px;height:530px;">
	<div class="header center-align indigo darken-1" style="
		margin-bottom:100px;padding-bottom:10px;padding-top:10px;margin-top:20px;">
		<h2><span class="lime-text text-lighten-5">FooBar University Library</span></h2>
	</div>
	<div class="admin center-align pt-20" style="margin-bottom:100px;">
	<a class="waves-effect waves-light btn-large" onclick="window.location.href='admin-operations.jsp'; return false;">Admin Panel</a>
	</div>	
	<div class="Librarian center-align">
	<a class="waves-effect waves-light btn-large" onclick="window.location.href='librarian-operations.jsp'; return false;">Librarian Panel</a>
	</div>	
</div>

</body>
</html>