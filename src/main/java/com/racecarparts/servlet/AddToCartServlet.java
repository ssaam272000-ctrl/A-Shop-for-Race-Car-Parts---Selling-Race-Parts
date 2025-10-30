package com.racecarparts.servlet;

import java.io.IOException;

import com.racecarparts.shop.EngineBlock;
import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.util.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addToCart")
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
		
		EngineBlock product = ProductCatalog.getPartbyID(partId); // This takes a string of what the part name is and defines an EngineBlock with that name within our inventory with that name.
		
		if (product != null && quantity > 0) {
			cart.addItem(product, quantity); // Adding item to cart
		}
		
		response.sendRedirect("index"); // Redirect back to the home page
	}
}
