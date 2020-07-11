<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Portal</title>
<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

<!-- Compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	
</head>
<body>
	<div class="container"
		style="background-color: gray; margin-bottom: 100px; height: 530px;">
		
			<div class="header center-align indigo darken-1"
				style="margin-bottom: 100px; padding-bottom: 10px; padding-top: 10px; margin-top: 20px;">
				<h2>
					<span class="lime-text text-lighten-5">FooBar University
						Library</span>
				</h2>
			</div>
	    
		<div class="row">
		  <div class="admin col m3">
			<div class="add-admin center-align pt-20" style="margin-bottom: 100px;">
				<a class="waves-effect waves-light btn-large"
					onclick="window.location.href='add-admin.jsp'; return false;">Add Librarian
					</a>
			</div>
		  </div>
		
		    <div class="admin col m3">
			    <div class="view-admin center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_LIB">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					View Librarians
				</button>
				 </form>
			   </div>
		     </div>
		
		<div class="admin col m3">
			    <div class="view-admin center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_LIB">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					Delete Librarians
				</button>
				 </form>
			   </div>
		     </div>
		  <div class="admin col m3">
			<div class="main-menu center-align">
				<a class="waves-effect waves-light btn-large"
					onclick="window.location.href='index.jsp'; return false;">Back To Menu</a>
			</div>
		 </div>
	   </div>
  </div>
</body>
</html>