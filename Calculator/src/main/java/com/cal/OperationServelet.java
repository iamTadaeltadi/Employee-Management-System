package com.cal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/operation")
public class OperationServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OperationServelet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the operation parameter from the request
        String operation = request.getParameter("operation");

        // Check the operation type and dispatch to the appropriate servlet
        if ("addition".equals(operation)) {
            jakarta.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("/addition");
            dispatcher.forward(request, response);
        } else if ("multiplication".equals(operation)) {
            jakarta.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("/multiplication");
            dispatcher.forward(request, response);
        } else {
            // Handle invalid operation
            response.getWriter().println("Invalid operation");
        }
    }
}
