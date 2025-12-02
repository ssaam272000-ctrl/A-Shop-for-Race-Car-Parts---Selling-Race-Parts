package com.inverse.servlets;

import com.inverse.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testdb")
public class TestDBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try (PrintWriter out = response.getWriter()) {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                out.println("<h2>✅ Database connection successful!</h2>");
            } else {
                out.println("<h2>❌ Database connection failed!</h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
