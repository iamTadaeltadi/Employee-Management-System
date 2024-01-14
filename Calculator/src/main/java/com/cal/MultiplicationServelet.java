// MultiplicationServlet.java
package com.cal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/multiplication")
public class MultiplicationServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MultiplicationServelet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle multiplication logic here
        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        int result = num1 * num2;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Calculator</title></head><body>");
        out.println("<h2>Multiplication result: </h2>" + result);
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
