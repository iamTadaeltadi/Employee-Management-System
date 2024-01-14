<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.itcs.Task" %> 
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Task</title>
    <link rel="stylesheet" href="css/bootstrap.css">
</head>

<body>
    <%@ include file="navbar.jsp" %>
    <h2 class="text-center my-4">Edit Task</h2>
    <div class="container">
        <%-- Retrieve the task object from the request attribute --%>
        <% Task task = (Task) request.getAttribute("task"); %>
        <% if (task != null) { %>
            <form action="TaskUpdateServlet" method="post">
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" class="form-control" value="<%= task.getTitle() %>" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" class="form-control" required><%= task.getDescription() %></textarea>
                </div>

                <div class="form-group">
                    <label for="due_date">Due Date (YYYY-MM-DD):</label>
                    <input type="text" id="dueDate" name="due_date" class="form-control" value="<%= task.getDueDate() %>" required>
                </div>

                <div class="form-group">
                    <label for="priority">Priority Level:</label>
                    <select id="priority" name="priority" class="form-control" required>
                        <option value="High" <%= task.getPriority().equals("High") ? "selected" : "" %>>High</option>
                        <option value="Medium" <%= task.getPriority().equals("Medium") ? "selected" : "" %>>Medium</option>
                        <option value="Low" <%= task.getPriority().equals("Low") ? "selected" : "" %>>Low</option>
                    </select>
                </div>

                <div class="form-group">
                    <input type="hidden" name="taskId" value="<%= task.getId() %>">
                    <input type="submit" value="Update Task" class="btn btn-primary">
                </div>
            </form>
        <% } else { %>
            <p>Task not found.</p>
        <% } %>
    </div>
</body>
</html>