<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Logout</title>
</head>
<body>
<%
    // Obtain the HttpSession object from the request
    jakarta.servlet.http.HttpSession jakartaSession = request.getSession(false);

    if (jakartaSession != null) {
        jakartaSession.invalidate();
    }
    
    response.sendRedirect("login.jsp");
%>
</body>
</html>