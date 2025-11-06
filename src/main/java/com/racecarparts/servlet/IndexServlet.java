package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
import com.racecarparts.view.IndexView;

import com.racecarparts.shop.EngineBlock;
import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.util.ProductCatalog;
import com.racecarparts.shop.RaceCarPart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

@WebServlet({"", "/", "/index"})
public class IndexServlet extends HttpServlet{ // handle all the web requests match the home page with index file, 

         @Override
            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // doGet function for a get request to the website, it will execute DoGet function.

                 response.setContentType("text/html;charset=UTF-8"); // runs when someone runs page, and gives HTML
                 response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // We don't want cache right now
                 HttpSession session = request.getSession(); // Create a web session
                 ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // this is creating a shopping cart object whenever someone makes a get request.

                 int cartItems = 0;
                 
                 if (cart != null) {
                         cartItems = cart.getTotalItems(); // Calls the cartItems method
                                         
                 }
                 List<RaceCarPart> allProducts = ProductCatalog.getInstance().getAllProducts(); // Applying SINGLETON HERE Getting a list of all the products call the method that we just defined to get all products. It was defined in the Product Catalog class.
                                 IndexView view = new IndexView(); // This will delegate the HTML generation to the view.
                                 String html = view.render(allProducts, cartItems); // This will render the HTML for the homepage.
                                 

                    PrintWriter out = response.getWriter();
                    out.println(html); //This sends the HTML response to the client.
                                     
         }
        
}
