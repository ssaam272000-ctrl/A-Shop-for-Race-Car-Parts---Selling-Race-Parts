package com.racecarparts.servlet;

import com.racecarparts.model.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null) {
            response.sendRedirect("cart");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            String[] partIds = request.getParameterValues("partId");
            String[] quantities = request.getParameterValues("quantity");
            
            if (partIds != null && quantities != null) {
                for (int i = 0; i < partIds.length; i++) {
                    try {
                        int quantity = Integer.parseInt(quantities[i]);
                        cart.updateQuantity(partIds[i], quantity);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } else if ("remove".equals(action)) {
            String partId = request.getParameter("partId");
            if (partId != null) {
                cart.removeItem(partId);
            }
        } else if ("clear".equals(action)) {
            cart.clear();
        }
        
        response.sendRedirect("cart");
    }
}
