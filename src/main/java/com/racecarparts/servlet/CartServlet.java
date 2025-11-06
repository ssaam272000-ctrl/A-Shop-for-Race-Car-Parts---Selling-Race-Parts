package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;
import com.racecarparts.view.CartView;

import com.racecarparts.shop.OrderLine;
import com.racecarparts.shop.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet { // handle all the Post requests for when someone wants to add a part to our cart.
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException { // Get the part from the request parameter
                response.setContentType("text/html;charset=UTF-8"); // runs when someone runs page, and gives HTML
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // We don't want cache right now
                
                HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
                if (cart == null) { // SINGLETON METHOD HERE; In order to avoid creating duplicate shopping carts, if a shopping cart (DOES NOT EXIST), then it creates a new one.
                        cart = new ShoppingCart();
                        session.setAttribute("cart", cart); // This stores the shopping cart we just created into our session, so you do not have to recreate this again.
                }
                CartView view = new CartView(); // This will delegate the HTML generation to the view.
                                String html = view.render(cart); // This will render the HTML for the homepage.
                // Generate HTML for the cart page
                PrintWriter out = response.getWriter(); // This will send the HTML response to the client.
                                out.println(html); //This sends the HTML response to the client.
                
        }
}
