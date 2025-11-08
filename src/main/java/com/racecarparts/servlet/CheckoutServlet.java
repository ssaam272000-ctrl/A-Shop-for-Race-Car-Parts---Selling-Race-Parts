package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.shop.OrderLine;
import com.racecarparts.dao.OrderDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.sql.SQLException;
import com.racecarparts.view.CheckoutView;
import com.racecarparts.view.InvoiceView;

public class CheckoutServlet extends HttpServlet{ // handle all the Post requests for when someone wants to add a part to our cart.

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      // Get the part from the request parameter
                response.setContentType("text/html;charset=UTF-8"); // runs when someone runs page, and gives HTML
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // We don't want cache right now
                
                HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
                if (cart == null || cart.isEmpty()) { // If the cart doesn't exist, we will send the person to a different java post request to create a cartServlet.java.
                        response.sendRedirect("cart"); // Redirect them to the cart end-point to create it.
                        return; // Ends current DoPost method.
                        
                }
                String customerName = request.getParameter("customerName"); // This will give you the Name of the Customer that they provide to the website.
                String billingAddress = request.getParameter("billingAddress"); // This will give you the Billing Address of the Customer that they provide to the website.
                String customerNotes = request.getParameter("customerNotes"); // This will give you the Notes of the Customer that they provide to the website.
                double orderTotal = cart.getTotal(); // This calls the orderTotal method
                // Save Cart Details before clearing Invoice Display
                List<OrderLine> orderLines = cart.getOrderLines(); // Call the the method for all order lines displaying in Invoice.
                double subTotal = cart.getSubTotal();
                double tax = cart.getTax();
                double carrier = cart.getCarrier();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy"); // This will give you the date in the format of Month Day, Year.
                String invoiceDate = dateFormat.format(new java.util.Date()); // This will give you the current date in the format of Month Day, Year.
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // This will format the prices with commas in the thousands place.
                
                int invoiceNumber = -1;
                try {
                    OrderDAO orderDAO = new OrderDAO();
                    invoiceNumber = orderDAO.saveOrder(customerName, billingAddress, customerNotes, orderLines, orderTotal);
                    System.out.println("Order saved to database with ID: " + invoiceNumber);
                } catch (SQLException e) {
                    System.err.println("Error saving order to database: " + e.getMessage());
                    e.printStackTrace();
                    invoiceNumber = new Random().nextInt(10000000);
                }
                
                String invoiceNumberString = String.format("%07d", invoiceNumber); // This will format the invoice number to be 7 digits long.
                        
                cart.clear(); // Clears your Cart to return an empty cart
                
                InvoiceView view = new InvoiceView(); // This will delegate the HTML generation to the view.
                String html = view.render(invoiceNumberString, invoiceDate, customerName, billingAddress, customerNotes, orderLines, subTotal, tax, carrier, orderTotal);
                // We will write an HTML page to write the Invoice
                PrintWriter out = response.getWriter();
                out.println(html); // This sends the HTML response to the client.
                
    }
        
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       // Get the part from the request parameter
            HttpSession session = request.getSession(); // Get HTTP Session to see if we have defined a shopping cart available.
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart"); // This is trying to see if a current shopping cart exists in our current session.
            if (cart == null || cart.isEmpty()) { // If the cart doesn't exist, we will send the person to a different java post request to create a cartServlet.java.
                    response.sendRedirect("cart"); // Redirect them to the cart end-point to create it.
                    return; 
            }
            CheckoutView view = new CheckoutView(); // This will delegate the HTML generation to the view.
            String html = view.render(cart.getTotal()); // This will render the HTML for the homepage.
            
            PrintWriter out = response.getWriter(); // This will send the HTML response to the client 
            out.println(html); // This sends the HTML response to the client.
        }
                
}
