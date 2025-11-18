package com.racecarparts.servlet;

import java.io.IOException;

import com.racecarparts.shop.EngineBlock;
import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.util.ProductCatalog;
import com.racecarparts.Factory.RaceCarPartFactory;
import com.racecarparts.shop.RaceCarPart;
import com.racecarparts.decorator.PerformanceTuningDecorator;
import com.racecarparts.decorator.AluminumCoatingDecorator;
import com.racecarparts.decorator.SteelCoatingDecorator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddToCartServlet extends HttpServlet { // handle all the Post requests for when someone wants to add a part to our cart.
        
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Get the part from the request parameter
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
                
                HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
                
                if (cart == null) { // SINGLETON METHOD HERE; In order to avoid creating duplicate shopping carts, if a shopping cart (DOES NOT EXIST), then it creates a new one.
                        cart = new ShoppingCart();
                        session.setAttribute("cart", cart); // This stores the shopping cart we just created into our session, so you do not have to recreate this again.
                }
                
                RaceCarPart product = RaceCarPartFactory.createPart(partId);  // FACTORY METHOD HERE; This is creating a new part based on the part ID that we just got from the request.
                
                if (product != null && quantity > 0) { // This is checking if the part exists and if the quantity is greater than 0.
                        RaceCarPart tunedPart = new PerformanceTuningDecorator(product); // This is using the decorator pattern that is defined in the java files, so it adds performance tuning to the part.
                        RaceCarPart finalPart;
                        if (product.getDescription().contains("Steel")) { // This is checking if the part is a steel part, and if it is, it adds steel coating to the part.
                                finalPart = new SteelCoatingDecorator(tunedPart); // This is using the decorator pattern that is defined in the java files, so it adds steel coating to the part.
                        } else if (product.getDescription().contains("Aluminum")) { // This is checking if the part is an aluminum part, and if it is, it adds aluminum coating to the part.
                                finalPart = new AluminumCoatingDecorator(tunedPart); // This is using the decorator pattern that is defined in the java files, so it adds aluminum coating to the part.
                        } else { // If it is neither steel nor aluminum, it just adds the performance tuning to the part.
                                finalPart = tunedPart; // If no specific coating is needed, just use the tuned part
                        }
                        cart.addItem(finalPart, quantity); // Adding the fully decorated part to cart with all enhancements.
                }
                
                response.sendRedirect("index"); // Redirect back to the home page
        }
}
