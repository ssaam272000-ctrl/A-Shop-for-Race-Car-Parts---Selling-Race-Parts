package com.inverse.servlets;

import com.inverse.dao.OrderDAO;
import com.inverse.dao.OrderDAO.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewHistory")
public class OrderHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String customerName = (session != null) ? (String) session.getAttribute("customerName") : null;

        if (customerName == null || customerName.trim().isEmpty()) {
            // No user logged in → ask for name first
            response.sendRedirect("setCustomer.jsp");
            return;
        }

        try {
            OrderDAO dao = new OrderDAO();
            List<Order> orders = dao.getOrdersByCustomer(customerName);

            request.setAttribute("orders", orders);
            request.setAttribute("customerName", customerName);

            RequestDispatcher rd = request.getRequestDispatcher("orderHistory.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load order history: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }
    }
}
