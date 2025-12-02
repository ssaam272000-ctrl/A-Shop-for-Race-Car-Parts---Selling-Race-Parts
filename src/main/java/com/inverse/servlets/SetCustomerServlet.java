package com.inverse.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Map;
import com.inverse.util.LogUtil;


@WebServlet("/setCustomer")
public class SetCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerName = request.getParameter("customerName");
        if (customerName == null || customerName.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a name.");
            request.getRequestDispatcher("setCustomer.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("customerName", customerName.trim());

        // If cart exists, process order directly
        Map<String, String[]> cartParams = (Map<String, String[]>) session.getAttribute("cartParams");
        if (cartParams != null) {
            session.removeAttribute("cartParams");

            // Create a request wrapper that returns saved parameters
            HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getParameter(String name) {
                    String[] values = cartParams.get(name);
                    return (values != null && values.length > 0) ? values[0] : super.getParameter(name);
                }
                @Override
                public Map<String, String[]> getParameterMap() {
                    return cartParams;
                }
            };

            // Now place order directly
            OrderServlet.processOrder(wrappedRequest, response, customerName.trim());
        } else {
            response.sendRedirect("products");
        }
    }
}
