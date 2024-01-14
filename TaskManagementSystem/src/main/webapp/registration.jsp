<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
    	.form-group {
            max-width: 300px;
            margin: 0 auto;
        }
    </style>
</head>
<body body="container">
	
    <h2 class="text-center my-4">User Registration</h2>
    <form action="RegistrationServlet" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Register" class="btn btn-primary my-3 w-100">
        </div>
        <% if (request.getAttribute("errorMessage") != null) { %>
	    <div class="error-message text-center text-danger">
	        <%= request.getAttribute("errorMessage") %>
	    </div>
	<% } %>
    </form>
</body>
</html>