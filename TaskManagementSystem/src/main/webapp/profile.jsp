<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
        .center-content {
            display: flex;
            flex-direction: column;
            align-items: center;
        
            height: 100vh;
        }
        
    </style>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <h1 class="text-center py-5">User Profile</h1>

    <%-- Check if the user is logged in --%>
    <% jakarta.servlet.http.HttpSession jakartaSession = request.getSession(false);
        if (jakartaSession == null || jakartaSession.getAttribute("name") == null) {
            response.sendRedirect("login.jsp");
        } else {
            String username = (String) jakartaSession.getAttribute("name");
    %>
    
    <div class="center-content">
        <p>Welcome, <%= username %>!</p>

        <%-- Display user profile information here --%>
        <p>Profile information goes here...</p>

        <form action="changepassword.jsp" method="post">
            <input type="submit" value="Change Password" >
        </form>

        <a href="logout.jsp" class="text-danger py-4">Logout</a>
    </div>

    <% } %>
</body>
</html>