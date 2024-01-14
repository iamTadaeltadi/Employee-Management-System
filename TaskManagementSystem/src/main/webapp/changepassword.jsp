<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <style>
        .form-group {
            max-width: 300px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <%@ include file="navbar.jsp" %>
    <h1 class="text-center my-3">Change Password</h1>

    <%-- Check if the user is logged in --%>
    <% jakarta.servlet.http.HttpSession jakartaSession = request.getSession(false);
        if (jakartaSession == null || jakartaSession.getAttribute("name") == null) {
            response.sendRedirect("login.jsp");
        } else {
            String username = (String) jakartaSession.getAttribute("name");
    %>

    

    <form action="ChangePasswordServlet" method="post">
   		<div class="form-group">
	        <label for="previousPassword">Old Password:</label>
	        <input type="password" id="previousPassword" name="previousPassword" required><br>
		</div>
		<div class="form-group">
	        <label for="newPassword" class="block">New Password:</label>
	        <input type="password" id="newPassword" name="newPassword" required><br>
        </div>
		<div class="form-group">
        	<input type="submit" value="Change Password " class="btn-danger w-100">
        </div>
        <% Boolean passwordChanged = (Boolean) session.getAttribute("passwordChanged");
       if (passwordChanged != null && passwordChanged) { %>
        <p class="text-danger text-center">Password successfully changed!</p>
        
    <% }
       
       session.removeAttribute("passwordChanged");
    %>
    </form>

    <% } %>
</body>
</html>