package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import com.racecarparts.shop.EngineBlock;
import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.util.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		 List<EngineBlock> allProducts = ProductCatalog.getAllProducts(); // Getting a list of all the products call the method that we just defined to get all products. It was defined in the Product Catalog class.
		 
		 // Generate HTML for the home page
		 PrintWriter out = response.getWriter();
		 out.println("<!DOCTYPE html>");
		 out.println("<html>");
		 out.println("<head>");
		 out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		 out.println("<title>A Shop for Race Car Parts</title>");
		 out.println("<style>");
		 out.println("body {");
		 out.println("    background-color: hsl(89, 43%, 51%);");
		 out.println("    font-family: Arial, sans-serif;");
		 out.println("    padding: 20px;");
		 out.println("}");
		 out.println("h1 {");
		 out.println("    background-color: hsl(185, 65%, 70%);");
		 out.println("    padding: 15px;");
		 out.println("    border-radius: 5px;");
		 out.println("}");
		 out.println("h2 {");
		 out.println("    background-color: DodgerBlue;");
		 out.println("    color: white;");
		 out.println("    padding: 10px;");
		 out.println("    border-radius: 5px;");
		 out.println("}");
		 out.println("ul {");
		 out.println("    list-style-type: none;");
		 out.println("    padding: 0;");
		 out.println("}");
		 out.println("li {");
		 out.println("    background-color: white;");
		 out.println("    margin: 10px 0;");
		 out.println("    padding: 15px;");
		 out.println("    border-radius: 5px;");
		 out.println("    box-shadow: 0 2px 4px rgba(0,0,0,0.1);");
		 out.println("}");
		 out.println(".price {");
		 out.println("    color: #e91e63;");
		 out.println("    font-size: 24px;");
		 out.println("    font-weight: bold;");
		 out.println("}");
		 out.println(".part-info {");
		 out.println("    margin-top: 10px;");
		 out.println("    color: #333;");
		 out.println("}");
		 out.println("button {");
		 out.println("    background-color: #00bcd4;");
		 out.println("    color: white;");
		 out.println("    border: none;");
		 out.println("    padding: 10px 20px;");
		 out.println("    border-radius: 5px;");
		 out.println("    cursor: pointer;");
		 out.println("    font-size: 16px;");
		 out.println("}");
		 out.println("button:hover {");
		 out.println("    background-color: #0097a7;");
		 out.println("}");
		 out.println(".cart-link {");
		 out.println("    float: right;");
		 out.println("    background-color: white;");
		 out.println("    color: #0097a7;");
		 out.println("    padding: 10px 20px;");
		 out.println("    text-decoration: none;");
		 out.println("    border-radius: 5px;");
		 out.println("    font-weight: bold;");
		 out.println("}");
		 out.println(".date {");
		 out.println("    background-color: rgba(255,255,255,0.7);");
		 out.println("    padding: 5px 10px;");
		 out.println("    border-radius: 3px;");
		 out.println("    display: inline-block;");
		 out.println("}");
		 out.println("</style>");
		 out.println("</head>");
		 out.println("<body>");
		 
		 out.println("<h1>A Shop for Race Car Parts <a href=\"cart\" class=\"cart-link\">View Cart (" + cartItems + ")</a></h1>");
		 out.println("<h2>Hemanth-Saam LLC</h2>");
		 out.println("<p class=\"date\">Date: " + new Date() + "</p>");
		 
		 out.println("<ul>");
		 for (EngineBlock part : allProducts) {
			 out.println("    <li>");
			 out.printf("        <div class=\"price\">$%.2f</div>%n", part.getPrice());
			 out.printf("        <div class=\"part-info\"><strong>%s</strong> - %s</div>%n", 
			           part.getEngineName(), part.getDescription());
			 out.println("        <form action=\"addToCart\" method=\"post\" style=\"margin-top: 10px;\">");
			 out.printf("            <input type=\"hidden\" name=\"partId\" value=\"%s\">%n", part.getEngineName());
			 out.println("            <input type=\"hidden\" name=\"quantity\" value=\"1\">");
			 out.println("            <button type=\"submit\">Add to Cart</button>");
			 out.println("        </form>");
			 out.println("    </li>");
		 }
		 out.println("</ul>");
		 
		 out.println("</body>");
		 out.println("</html>");
	 }
}
