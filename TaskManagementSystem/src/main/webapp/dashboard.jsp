<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.itcs.Task" %> 
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <style>
    .form-group {
        display: flex;
        align-items: center;
        max-width: 300px;
        margin: 0 auto;
    }

    .form-group input[type="search"] {
        flex: 1;
    }
</style>
</head>
<body>
    <%@ include file="navbar.jsp" %>
	<span class="navbar-text ml-auto p-5">
            Hello, <%= session.getAttribute("name") %>!
        </span>
      <form action="SearchTaskServlet" method="post">
	    <div class="form-group">
	        <label for="search"></label>
	        <input type="search" id="search" name="search" class="form-control" >
	        <input type="submit" value="search" class="btn btn-success my-4 mx-3">
	    </div>
	</form>
    <div class="container mt-4">
        
        	<%
		    List<Task> tasks = (List<Task>) session.getAttribute("tasks");
		    if (tasks != null && !tasks.isEmpty()) {
		        for (Task task : tasks) { 
			%>
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title"><%= task.getTitle() %></h5>
                    <p class="card-text"><%= task.getDescription() %></p>
                    <p class="card-text"><strong>Due Date:</strong> <%= task.getDueDate() %></p>
                    <p class="card-text"><strong>Priority:</strong> <%= task.getPriority() %></p>
                    
                    <%-- Add the task completion, editing, and deletion functionality here --%>
                    <form action="TaskCompletionServlet" method="post" class="d-inline">
					    <input type="hidden" name="taskId" value="<%= task.getId() %>">
					    <% if (!task.isCompleted()) { %>
					        <button type="submit" class="btn btn-primary">Complete</button>
					    <% } else { %>
					        <button type="submit" class="btn btn-success" disabled>Completed</button>
					    <% } %>
					</form>

                    <form action="TaskEditingServlet" method="post" class="d-inline">
                        <input type="hidden" name="taskId" value="<%= task.getId() %>">
                        <button type="submit" class="btn btn-primary">Edit</button>
                    </form>

                    <form action="TaskDeletionServlet" method="post" class="d-inline">
                        <input type="hidden" name="taskId" value="<%= task.getId() %>">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        <% 
            }
        } else {
        %>
            <p>No tasks found.</p>
        <% } %>
    </div>
</body>
</html>