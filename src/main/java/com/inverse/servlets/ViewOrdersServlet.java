package com.inverse.servlets;

import com.inverse.dao.OrderDAO;
import com.inverse.dao.OrderDAO.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/viewOrders")
public class ViewOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String customerName = (String) session.getAttribute("customerName");

        if (customerName == null || customerName.trim().isEmpty()) {
            resp.sendRedirect("products");
            return;
        }

        OrderDAO dao = new OrderDAO();
        try {
            List<Order> orders = dao.getOrdersByCustomer(customerName);
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("viewOrders.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
