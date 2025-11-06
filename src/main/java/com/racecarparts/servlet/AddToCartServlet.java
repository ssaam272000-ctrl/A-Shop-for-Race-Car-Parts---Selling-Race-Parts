package com.racecarparts.servlet;

import java.io.IOException;

import com.racecarparts.shop.EngineBlock;
import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.util.ProductCatalog;
import com.racecarparts.Factory.RaceCarPartFactory;
import com.racecarparts.shop.RaceCarPart;
import com.racecarparts.decorator.PerformanceTuningDecorator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddToCartServlet extends HttpServlet { // handle all the Post requests for when someone wants to add a part to our cart.
        
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Get the part from the request parameter
                String partId = request.getParameter("partId");
                System.out.println("AddToCart - Received partId: " + partId);
                int quantity = 1;
                
                try {
                        String qtyParam = request.getParameter("quantity");
                        if (qtyParam != null && !qtyParam.isEmpty()) {
                                quantity = Integer.parseInt(qtyParam);
                        }
                } catch (NumberFormatException e) {
                        quantity = 1;
                }
                
                HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
                
                if (cart == null) { // SINGLETON METHOD HERE; In order to avoid creating duplicate shopping carts, if a shopping cart (DOES NOT EXIST), then it creates a new one.
                        cart = new ShoppingCart();
                        session.setAttribute("cart", cart); // This stores the shopping cart we just created into our session, so you do not have to recreate this again.
                }
                
                RaceCarPart product = RaceCarPartFactory.createPart(partId);
                System.out.println("AddToCart - Product from factory: " + (product != null ? product.getEngineName() : "NULL"));
                
                if (product != null && quantity > 0) {
                        RaceCarPart tunedPart = new PerformanceTuningDecorator(product);
                        cart.addItem(tunedPart, quantity); // Adding item to cart
                        System.out.println("AddToCart - Item added to cart. Cart size: " + cart.getTotalItems());
                } else {
                        System.out.println("AddToCart - Product was null or quantity was 0");
                }
                
                response.sendRedirect("index"); // Redirect back to the home page
        }
}
