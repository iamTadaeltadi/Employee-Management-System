<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        .form-group {
            max-width: 300px;
            margin: 0 auto;
        }
    </style>
</head>
<body class="container">
	
    <h2 class="text-center my-5">Login page</h2>
    <form action="LoginServlet" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Login" class="btn btn-primary my-4 w-100">
        </div>
        <% Boolean passwordChanged = (Boolean) session.getAttribute("logincheck");
       if (passwordChanged != null && passwordChanged) { %>
        <p class="text-danger text-center">Wrong password or username try again!</p>
        
    <% }
       
       session.removeAttribute("logincheck");
    %>
    </form>
</body>
</html>