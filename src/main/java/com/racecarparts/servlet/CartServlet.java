package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;

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
                
                // Generate HTML for the cart page
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
                out.println("<title>Shopping Cart - Race Car Parts</title>");
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
                out.println("table {");
                out.println("    width: 100%;");
                out.println("    background-color: white;");
                out.println("    border-collapse: collapse;");
                out.println("    margin: 20px 0;");
                out.println("    border-radius: 5px;");
                out.println("}");
                out.println("th, td {");
                out.println("    padding: 12px;");
                out.println("    text-align: left;");
                out.println("    border-bottom: 1px solid #ddd;");
                out.println("}");
                out.println("th {");
                out.println("    background-color: DodgerBlue;");
                out.println("    color: white;");
                out.println("}");
                out.println("button, .btn {");
                out.println("    background-color: #00bcd4;");
                out.println("    color: white;");
                out.println("    border: none;");
                out.println("    padding: 8px 16px;");
                out.println("    border-radius: 5px;");
                out.println("    cursor: pointer;");
                out.println("    text-decoration: none;");
                out.println("    display: inline-block;");
                out.println("    margin: 2px;");
                out.println("}");
                out.println("button:hover, .btn:hover {");
                out.println("    background-color: #0097a7;");
                out.println("}");
                out.println(".btn-warning {");
                out.println("    background-color: #ff9800;");
                out.println("}");
                out.println(".btn-warning:hover {");
                out.println("    background-color: #e68900;");
                out.println("}");
                out.println(".btn-danger {");
                out.println("    background-color: #f44336;");
                out.println("}");
                out.println(".btn-danger:hover {");
                out.println("    background-color: #da190b;");
                out.println("}");
                out.println(".btn-success {");
                out.println("    background-color: #4CAF50;");
                out.println("    font-size: 18px;");
                out.println("    padding: 12px 30px;");
                out.println("}");
                out.println(".btn-success:hover {");
                out.println("    background-color: #45a049;");
                out.println("}");
                out.println(".summary {");
                out.println("    background-color: white;");
                out.println("    padding: 20px;");
                out.println("    border-radius: 5px;");
                out.println("    margin: 20px 0;");
                out.println("}");
                out.println(".summary-row {");
                out.println("    display: flex;");
                out.println("    justify-content: space-between;");
                out.println("    margin: 10px 0;");
                out.println("    font-size: 18px;");
                out.println("}");
                out.println(".summary-total {");
                out.println("    font-weight: bold;");
                out.println("    font-size: 24px;");
                out.println("    color: #e91e63;");
                out.println("    border-top: 2px solid #ddd;");
                out.println("    padding-top: 10px;");
                out.println("    margin-top: 10px;");
                out.println("}");
                out.println("input[type=\"number\"] {");
                out.println("    width: 60px;");
                out.println("    padding: 5px;");
                out.println("}");
                out.println("input[type=\"text\"], textarea {");
                out.println("    width: 100%;");
                out.println("    padding: 8px;");
                out.println("    border: 1px solid #ddd;");
                out.println("    border-radius: 3px;");
                out.println("    margin: 5px 0;");
                out.println("}");
                out.println(".checkout-form {");
                out.println("    background-color: white;");
                out.println("    padding: 20px;");
                out.println("    border-radius: 5px;");
                out.println("    margin: 20px 0;");
                out.println("}");
                out.println(".empty-cart {");
                out.println("    background-color: white;");
                out.println("    padding: 30px;");
                out.println("    border-radius: 5px;");
                out.println("    text-align: center;");
                out.println("    font-size: 18px;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                
                out.println("<h1>Shopping Cart</h1>");
                out.println("<a href=\"index\" class=\"btn\">Continue Shopping</a>");
                
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
                
                if (cart.isEmpty()) {
                        out.println("<div class=\"empty-cart\">");
                        out.println("    Your shopping cart is empty. <a href=\"index\" class=\"btn\">Start shopping</a>");
                        out.println("</div>");
                } else {
                        out.println("<form action=\"updateCart\" method=\"post\">");
                        out.println("    <input type=\"hidden\" name=\"action\" value=\"update\">");
                        out.println("    <table>");
                        out.println("        <thead>");
                        out.println("            <tr>");
                        out.println("                <th>Part ID</th>");
                        out.println("                <th>Product Name</th>");
                        out.println("                <th>Unit Price</th>");
                        out.println("                <th>Quantity</th>");
                        out.println("                <th>Subtotal</th>");
                        out.println("                <th>Action</th>");
                        out.println("            </tr>");
                        out.println("        </thead>");
                        out.println("        <tbody>");
                        
                        for (OrderLine line : cart.getOrderLines()) {
                                out.println("            <tr>");
                                out.printf("                <td>%s</td>%n", line.getEngineBlock().getEngineName());
                                out.printf("                <td>%s</td>%n", line.getEngineBlock().getDescription());
                                out.printf("                <td>%s</td>%n", currencyFormat.format(line.getEngineBlock().getPrice()));
                                out.println("                <td>");
                                out.printf("                    <input type=\"hidden\" name=\"partId\" value=\"%s\">%n", line.getEngineBlock().getEngineName());
                                out.printf("                    <input type=\"number\" name=\"quantity\" value=\"%d\" min=\"0\">%n", line.getQuantity());
                                out.println("                </td>");
                                out.printf("                <td>%s</td>%n", currencyFormat.format(line.getOrderTotal()));
                                out.println("                <td>");
                                out.printf("                    <a href=\"updateCart?action=remove&partId=%s\" class=\"btn btn-danger\">Remove</a>%n", line.getEngineBlock().getEngineName());
                                out.println("                </td>");
                                out.println("            </tr>");
                        }
                        
                        out.println("        </tbody>");
                        out.println("    </table>");
                        out.println("    <button type=\"submit\" class=\"btn\">Update Cart</button>");
                        out.println("    <a href=\"updateCart?action=clear\" class=\"btn btn-warning\">Clear Cart</a>");
                        out.println("</form>");
                        
                        out.println("<div class=\"summary\">");
                        out.println("    <h3>Order Summary</h3>");
                        out.println("    <div class=\"summary-row\">");
                        out.println("        <span>Subtotal:</span>");
                        out.printf("        <span>%s</span>%n", currencyFormat.format(cart.getSubTotal()));
                        out.println("    </div>");
                        out.println("    <div class=\"summary-row\">");
                        out.println("        <span>Shipping (5%):</span>");
                        out.printf("        <span>%s</span>%n", currencyFormat.format(cart.getCarrier()));
                        out.println("    </div>");
                        out.println("    <div class=\"summary-row\">");
                        out.println("        <span>Tax (10%):</span>");
                        out.printf("        <span>%s</span>%n", currencyFormat.format(cart.getTax()));
                        out.println("    </div>");
                        out.println("    <div class=\"summary-row summary-total\">");
                        out.println("        <span>Total:</span>");
                        out.printf("        <span>%s</span>%n", currencyFormat.format(cart.getTotal()));
                        out.println("    </div>");
                        out.println("</div>");
                        
                        out.println("<div class=\"checkout-form\">");
                        out.println("    <h3>Checkout</h3>");
                        out.println("    <form action=\"checkout\" method=\"post\">");
                        out.println("        <label for=\"customerName\">Customer Name:</label>");
                        out.println("        <input type=\"text\" id=\"customerName\" name=\"customerName\" required>");
                        out.println("        <label for=\"billingAddress\">Billing Address:</label>");
                        out.println("        <textarea id=\"billingAddress\" name=\"billingAddress\" rows=\"3\" required></textarea>");
                        out.println("        <br><br>");
                        out.println("        <button type=\"submit\" class=\"btn btn-success\">Complete Order</button>");
                        out.println("    </form>");
                        out.println("</div>");
                }
                
                out.println("</body>");
                out.println("</html>");
        }
}
