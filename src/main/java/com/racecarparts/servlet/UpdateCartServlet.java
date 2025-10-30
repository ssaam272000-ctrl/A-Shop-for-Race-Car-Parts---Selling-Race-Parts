package com.racecarparts.servlet;

import java.io.IOException;

import com.racecarparts.shop.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet { // This will handle requests for update cart, remove cart, clear cart, or any changes to the cart.

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Get the part from the request parameter
		processRequest(request, response);	
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Get the part from the request parameter
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
		if (cart == null || cart.isEmpty()) { // If the cart doesn't exist, we will send the person to a different java post request to create a cartServlet.java.
			response.sendRedirect("cart"); // Redirect them to the cart end-point to create it.
			return; // Ends current DoPost method.
		}
		String action = request.getParameter("action"); // This will get Whatever action the user wants to perform.
		if ("update".equals(action)) {
			String[] partIds = request.getParameterValues("partId"); // Gets all the partIDs that the user updates and saves them in a string Array format.
			String[] quantities = request.getParameterValues("quantity"); // Gets all the quantities that the user updates and saves them in a string Array format.
			if (partIds != null && quantities != null) {
				for (int i = 0; i < partIds.length; i++) {
					int quantity = Integer.parseInt(quantities[i]);	
					cart.updateQuantity(partIds[i], quantity); // Calling the update method with the name of PartID and its going to update the EngineBlock with the provided PartID with the quantity.
				}
			}
		}
		
		else if ("remove".equals(action)) { // Removing the EngineBock with this PartID
			String partId = request.getParameter("partId");
			cart.removeItem(partId);
		}	
		else if ("clear".equals(action)) { // Clearing the cart
			cart.clear();
			
		}
		response.sendRedirect("cart"); // Bring the User back to the cart, so they see the updated cart.
			
	}
	
}
