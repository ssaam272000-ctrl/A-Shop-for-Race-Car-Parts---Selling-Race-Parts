package com.inverse.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.*;
import com.inverse.dao.OrderDAO;
import com.inverse.dao.OrderDAO.OrderItem;
import com.inverse.dao.ProductDAO;
import com.inverse.model.Product;

@WebServlet("/placeOrder")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Coating prices and fees (decided for the project)
    private static final double ALUMINUM_COATING = 500.0;
    private static final double STEEL_COATING = 357.0;
    private static final double TAX_RATE = 0.005; // 0.5%
    private static final double ORDER_FEE = 0.50; // flat fee in currency units

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String customerName = (String) session.getAttribute("customerName");

        // Ask for name only if not set yet
        if (customerName == null || customerName.trim().isEmpty()) {
            // Save cart temporarily in session
            session.setAttribute("cartParams", request.getParameterMap());
            response.sendRedirect("setCustomer.jsp");
            return;
        }

        processOrder(request, response, customerName);
    }

    // Common logic for processing the order
    public static void processOrder(HttpServletRequest request, HttpServletResponse response, String customerName)
            throws ServletException, IOException {

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        List<OrderItem> items = new ArrayList<>();
        double subtotal = 0.0;

        // Coating detection helpers (case-insensitive name search)
        for (Product p : products) {
            String qtyParam = request.getParameter("quantity_" + p.getProductId());
            if (qtyParam != null && !qtyParam.trim().isEmpty()) {
                try {
                    int qty = Integer.parseInt(qtyParam);
                    if (qty > 0) {
                        // Determine coating price per unit based on product name
                        String nameLower = (p.getName() == null) ? "" : p.getName().toLowerCase();
                        double coating = 0.0;
                        if (nameLower.contains("alum") || nameLower.contains("aluminum")) {
                            coating = ALUMINUM_COATING;
                        } else if (nameLower.contains("steel")) {
                            coating = STEEL_COATING;
                        }

                        // per-unit price (base + coating)
                        double perUnitPrice = p.getPrice() + coating;

                        // add to items (store per-unit price including coating)
                        items.add(new OrderItem(p.getProductId(), qty, perUnitPrice));

                        // accumulate subtotal (per-unit * qty)
                        subtotal += perUnitPrice * qty;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        try {
            if (!items.isEmpty()) {
                // compute tax and fee
                double tax = roundTwoDecimals(subtotal * TAX_RATE);
                double fee = ORDER_FEE;
                double grandTotal = roundTwoDecimals(subtotal + tax + fee);

                OrderDAO orderDAO = new OrderDAO();
                int orderId = orderDAO.createOrder(customerName, items, grandTotal);

                // Persisted, redirect to invoice with orderId
                response.sendRedirect("invoice.jsp?orderId=" + orderId);
            } else {
                request.setAttribute("error", "No items selected.");
                request.getRequestDispatcher("products").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error placing order: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private static double roundTwoDecimals(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
