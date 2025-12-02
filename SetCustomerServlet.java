package com.inverse.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/setCustomer")
public class SetCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        if (customerName == null || customerName.trim().isEmpty()) {
            response.sendRedirect("setCustomer.jsp");
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("customerName", customerName.trim());

        // If cart exists, process order directly
        Map<String, String[]> cartParams = (Map<String, String[]>) session.getAttribute("cartParams");
        if (cartParams != null) {
            session.removeAttribute("cartParams");

            // Create a fake request wrapper with saved cart
            HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getParameter(String name) {
                    String[] values = cartParams.get(name);
                    return (values != null && values.length > 0) ? values[0] : null;
                }
            };

            // Now place order directly
            OrderServlet.processOrder(wrappedRequest, response, customerName.trim());
        } else {
            // If no cart → go to products
            response.sendRedirect("products");
        }
    }
}
