<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Task</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>

<body>
	<%@ include file="navbar.jsp" %>
	<%-- Display the error message (if any) --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <p><strong><%= errorMessage %></strong></p>
    <% } %>
    <h2 class="text-center my-4">Create New Task</h2>
    <div class="container">
    <form action="TaskCreationServlet" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" class="form-control" required></textarea>
        </div>

        <div class="form-group">
            <label for="due_date">Due Date (YYYY-MM-DD):</label>
            <input type="text" id="dueDate" name="due_date" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="priority">Priority Level:</label>
            <select id="priority" name="priority" class="form-control" required>
                <option value="High">High</option>
                <option value="Medium">Medium</option>
                <option value="Low">Low</option>
            </select>
        </div>

        <div class="form-group">
            <input type="submit" value="Create Task" class="btn btn-primary">
        </div>
    </form>
	</div>
</body>
</html>