package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.racecarparts.shop.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/checkout")
	public class CheckoutServlet extends HttpServlet{ // handle all the Post requests for when someone wants to add a part to our cart.

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	// Get the part from the request parameter
		response.setContentType("text/html;charset=UTF-8"); // runs when someone runs page, and gives HTML
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // We don't want cache right now
		
		HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
		if (cart == null || cart.isEmpty()) { // If the cart doesn't exist, we will send the person to a different java post request to create a cartServlet.java.
			response.sendRedirect("cart"); // Redirect them to the cart end-point to create it.
			return; // Ends current DoPost method.
			
		}
		String customerName = request.getParameter("customerName"); // This will give you the Name of the Customer that they provide to the website.
		double orderTotal = cart.getTotal(); // This calls the orderTotal method
		
		cart.clear(); // Clears your Cart to return an empty cart
		
		
		// We will write an HTML page to write the Invoice
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>Order Confirmation</title>");
		out.println("<style>");
		out.println("body {");
		out.println("    background-color: hsl(89, 43%, 51%);");
		out.println("    font-family: Arial, sans-serif;");
		out.println("    padding: 20px;");
		out.println("}");
		out.println(".confirmation {");
		out.println("    background-color: white;");
		out.println("    padding: 30px;");
		out.println("    border-radius: 10px;");
		out.println("    max-width: 600px;");
		out.println("    margin: 50px auto;");
		out.println("    box-shadow: 0 4px 8px rgba(0,0,0,0.2);");
		out.println("}");
		out.println("h1 {");
		out.println("    background-color: hsl(185, 65%, 70%);");
		out.println("    padding: 15px;");
		out.println("    border-radius: 5px;");
		out.println("}");
		out.println(".info {");
		out.println("    font-size: 18px;");
		out.println("    margin: 15px 0;");
		out.println("}");
		out.println(".total {");
		out.println("    color: #e91e63;");
		out.println("    font-size: 24px;");
		out.println("    font-weight: bold;");
		out.println("}");
		out.println("a {");
		out.println("    background-color: #00bcd4;");
		out.println("    color: white;");
		out.println("    padding: 10px 20px;");
		out.println("    text-decoration: none;");
		out.println("    border-radius: 5px;");
		out.println("    display: inline-block;");
		out.println("    margin-top: 20px;");
		out.println("}");
		out.println("a:hover {");
		out.println("    background-color: #0097a7;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"confirmation\">");
		out.println("    <h1>Order Confirmed!</h1>");
		out.printf("    <p class=\"info\">Thank you for your order, <strong>%s</strong>!</p>%n", customerName);
		out.printf("    <p class=\"total\">Your order total: $%.2f</p>%n", orderTotal);
		out.println("    <p class=\"info\">We will process your order shortly.</p>");
		out.println("    <a href=\"index\">Return to Shop</a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	// Get the part from the request parameter
		response.sendRedirect("cart");
	}
}
