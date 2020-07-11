<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Librarian Portal</title>
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
			<div class="bor-book center-align pt-20" style="margin-bottom: 100px;">
				<a class="waves-effect waves-light btn-large"
					onclick="window.location.href='borrow-book.jsp'; return false;">Borrow Book
					</a>
			</div>
		  </div>
		
		    <div class="admin col m3">
			    <div class="view-bor-books center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_BOR_BK">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					View Borr. Books
				</button>
				 </form>
			   </div>
		     </div>
		
		 <div class="admin col m3">
			    <div class="bor-studs center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_BOR_STD">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					View Borr. Stud's
				</button>
				 </form>
			   </div>
		     </div>
		     
		  <div class="admin col m3">
			<div class="return-book center-align">
				<a class="waves-effect waves-light btn-large"
					onclick="window.location.href='return-book.jsp'; return false;">Return Book</a>
			</div>
		 </div>
	   </div>
	   
	   
	<div class="row">
		 <div class="admin col m3">
			    <div class="view-books center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_ALL_BK">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					View All Books
				</button>
				 </form>
			   </div>
		     </div>
		
		  <div class="admin col m3">
			<div class="add-book center-align pt-20" style="margin-bottom: 100px;">
				<a class="waves-effect waves-light btn-large"
					onclick="window.location.href='add-book.jsp'; return false;">Add Book
				</a>
			</div>
		  </div>
		
		  <div class="admin col m3">
			    <div class="delete-book center-align">
			    <form action="LibraryServlet" method="get">
			       <input type="hidden" name="command" value="VIEW_ALL_BK">
					 <button class="btn-large waves-effect waves-light" type="submit"
					value="Save" class="save">
					Delete Books
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