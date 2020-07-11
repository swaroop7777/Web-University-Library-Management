<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error Page</title>
</head>
<body>
<% if(response.getStatus() == 200){ %>
<h4><font color="red">Error: Entered BookID/StudentID/Both is already present in the table . Try filling again with different ID's!</font></h4><br>
<%} %>
<button type="button" name="back" onclick="window.history.back()">Go back</button>
</body>
</html>