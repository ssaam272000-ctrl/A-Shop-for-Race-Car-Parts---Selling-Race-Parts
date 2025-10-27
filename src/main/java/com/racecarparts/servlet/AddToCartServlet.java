package com.racecarparts.servlet;

import com.racecarparts.model.EngineBlock;
import com.racecarparts.model.ShoppingCart;
import com.racecarparts.util.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String partId = request.getParameter("partId");
        int quantity = 1;
        
        try {
            String qtyParam = request.getParameter("quantity");
            if (qtyParam != null && !qtyParam.isEmpty()) {
                quantity = Integer.parseInt(qtyParam);
            }
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        
        EngineBlock product = ProductCatalog.getProductById(partId);
        
        if (product != null && quantity > 0) {
            cart.addItem(product, quantity);
        }
        
        response.sendRedirect("index");
    }
}
