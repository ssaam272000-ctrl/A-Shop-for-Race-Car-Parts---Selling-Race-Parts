package com.inverse.servlets;

import com.inverse.dao.ProductDAO;
import com.inverse.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String customerName = (session != null) ? (String) session.getAttribute("customerName") : null;

        // 👇 If user exists but hasn’t confirmed choice yet → show "Continue or New User" page
        String fromChoice = request.getParameter("fromChoice");
        if (customerName != null && (fromChoice == null || fromChoice.isEmpty())) {
            RequestDispatcher rd = request.getRequestDispatcher("userChoice.jsp");
            rd.forward(request, response);
            return;
        }

        // ✅ Always load product list
        ProductDAO dao = new ProductDAO();
        List<Product> products = dao.getAllProducts();
        request.setAttribute("products", products);
        request.setAttribute("customerName", customerName);

        RequestDispatcher rd = request.getRequestDispatcher("products.jsp");
        rd.forward(request, response);
    }
}
