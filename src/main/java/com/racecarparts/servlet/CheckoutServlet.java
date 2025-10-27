package com.racecarparts.servlet;

import com.racecarparts.model.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }
        
        String customerName = request.getParameter("customerName");
        String billingAddress = request.getParameter("billingAddress");
        
        request.setAttribute("orderTotal", cart.getTotal());
        request.setAttribute("customerName", customerName);
        
        cart.clear();
        
        request.getRequestDispatcher("/WEB-INF/jsp/confirmation.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("cart.jsp");
    }
}
