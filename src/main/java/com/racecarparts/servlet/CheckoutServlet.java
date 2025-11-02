package com.racecarparts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.shop.OrderLine;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;

@WebServlet("/checkout")
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
                double orderTotal = cart.getTotal(); // This calls the orderTotal method
                // Save Cart Details before clearing Invoice Display
                List<OrderLine> orderLines = cart.getOrderLines(); // Call the the method for all order lines displaying in Invoice.
                double subTotal = cart.getSubTotal();
                double tax = cart.getTax();
                double carrier = cart.getCarrier();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy"); // This will give you the date in the format of Month Day, Year.
                String invoiceDate = dateFormat.format(new java.util.Date()); // This will give you the current date in the format of Month Day, Year.
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // This will format the prices with commas in the thousands place.
                // Generate a random order number       
                int invoiceNumber = new Random().nextInt(10000000); // This will generate a random number for the invoice number.
                        
                cart.clear(); // Clears your Cart to return an empty cart
                
                
                // We will write an HTML page to write the Invoice
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
                out.println("<title>Invoice - Order Confirmation</title>");
                out.println("<style>");
                out.println("body {");
                out.println("    background-color: #b0e0d8;");
                out.println("    font-family: Arial, sans-serif;");
                out.println("    padding: 20px;");
                out.println("    margin: 0;");
                out.println("}");
                out.println("h1 {");
                out.println("    background-color: hsl(185, 65%, 70%);");
                out.println("    padding: 15px;");
                out.println("    margin: 0;");
                out.println("}");
                out.println(".invoice-container {");
                out.println("    max-width: 900px;");
                out.println("    margin: 0 auto;");
                out.println("    background-color: #b0e0d8;");
                out.println("}");
                out.println(".header-section {");
                out.println("    display: flex;");
                out.println("    align-items: flex-start;");
                out.println("    margin-bottom: 20px;");
                out.println("}");
                out.println(".engine-image {");
                out.println("    width: 180px;");
                out.println("    height: auto;");
                out.println("    margin-right: 15px;");
                out.println("    border: 2px solid #333;");
                out.println("}");
                out.println(".company-info {");
                out.println("    background-color: #7ba7d8;");
                out.println("    padding: 15px;");
                out.println("    flex-grow: 1;");
                out.println("    position: relative;");
                out.println("}");
                out.println(".company-name {");
                out.println("    font-weight: bold;");
                out.println("    font-size: 16px;");
                out.println("    margin-bottom: 5px;");
                out.println("}");
                out.println(".invoice-title {");
                out.println("    position: absolute;");
                out.println("    top: 15px;");
                out.println("    right: 15px;");
                out.println("    font-size: 20px;");
                out.println("    font-weight: bold;");
                out.println("}");
                out.println(".invoice-meta {");
                out.println("    position: absolute;");
                out.println("    top: 50px;");
                out.println("    right: 15px;");
                out.println("    text-align: right;");
                out.println("}");
                out.println(".billing-section {");
                out.println("    display: flex;");
                out.println("    justify-content: space-between;");
                out.println("    margin: 20px 0;");
                out.println("}");
                out.println(".bill-to, .ship-to {");
                out.println("    width: 45%;");
                out.println("}");
                out.println(".section-title {");
                out.println("    font-weight: bold;");
                out.println("    margin-bottom: 5px;");
                out.println("}");
                out.println(".order-info {");
                out.println("    margin: 15px 0;");
                out.println("    font-weight: bold;");
                out.println("}");
                out.println("table {");
                out.println("    width: 100%;");
                out.println("    border-collapse: collapse;");
                out.println("    margin: 20px 0;");
                out.println("    background-color: white;");
                out.println("}");
                out.println("th {");
                out.println("    background-color: #f0f0f0;");
                out.println("    padding: 8px;");
                out.println("    text-align: left;");
                out.println("    border: 1px solid #333;");
                out.println("    font-weight: bold;");
                out.println("}");
                out.println("td {");
                out.println("    padding: 8px;");
                out.println("    border: 1px solid #333;");
                out.println("}");
                out.println(".totals-section {");
                out.println("    text-align: right;");
                out.println("    margin-top: 20px;");
                out.println("    font-weight: bold;");
                out.println("}");
                out.println(".totals-section div {");
                out.println("    margin: 5px 0;");
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
         
                out.println("<h1>A Shop for Race Car Parts</h1>");
         
                out.println("<div class=\"invoice-container\">");
         
                // Header with image and company info
                out.println("<div class=\"header-section\">");
                out.println("    <img src=\"/images/racingengine.jpg\" alt=\"Racing Engine\" class=\"engine-image\">");
                out.println("    <div class=\"company-info\">");
                out.println("        <div class=\"invoice-title\">INVOICE</div>");
                out.println("        <div class=\"company-name\">Gabby-Hemanth-Saam LLC</div>");
                out.println("        <div>Parts 1 Boulevard</div>");
                out.println("        <div>Columbus, GA 31904 US</div>");
                out.println("        <div>1-800-272-7625</div>");
                out.println("        <div class=\"invoice-meta\">");
                out.println("            <div><strong>Invoice Date</strong></div>");
                out.printf("            <div>%s</div>%n", invoiceDate);
                out.println("            <div style=\"margin-top: 10px;\"><strong>Invoice #</strong></div>");
                out.printf("            <div>%d</div>%n", invoiceNumber);
                out.println("        </div>");
                out.println("    </div>");
                out.println("</div>");
         
                // Billing section
                out.println("<div class=\"billing-section\">");
                out.println("    <div class=\"bill-to\">");
                out.println("        <div class=\"section-title\">Bill To:</div>");
                out.printf("        <div>%s</div>%n", customerName);
                out.println("        <div>1820 State Rte 66</div>");
                out.println("        <div>Kingman, AZ 86409 US</div>");
                out.println("        <div>Email: <span style=\"color: blue;\">Charles.Teeple@csurocks.com</span></div>");
                out.println("        <div>1-800-356-8615</div>");
                out.println("    </div>");
                out.println("    <div class=\"ship-to\">");
                out.println("        <div class=\"section-title\">Ship To:</div>");
                out.printf("        <div>%s</div>%n", customerName);
                out.println("        <div>1820 State Rte 66</div>");
                out.println("        <div>Kingman, AZ 86409 US</div>");
                out.println("    </div>");
                out.println("</div>");
         
                // Order processing date
                out.printf("<div class=\"order-info\">This order is scheduled to be processed on: %s</div>%n", invoiceDate);
                out.println("<div><strong>Notes/Special Instructions:</strong> Free Text box for Customer to make Notes</div>");
         
                // Order items table
                out.println("<table>");
                out.println("    <tr>");
                out.println("        <th>QTY:</th>");
                out.println("        <th>Part Code:</th>");
                out.println("        <th>Part Description:</th>");
                out.println("        <th>Amount Field:</th>");
                out.println("        <th>Total Field:</th>");
                out.println("    </tr>");
         
                // Display each order line
                for (OrderLine line : orderLines) {
                                out.println("    <tr>");
                                out.printf("        <td>%d</td>%n", line.getQuantity());
                                out.printf("        <td>%s</td>%n", line.getEngineBlock().getEngineName());
                                out.printf("        <td>%s</td>%n", line.getEngineBlock().getDescription());
                                out.printf("        <td>%s</td>%n", currencyFormat.format(line.getEngineBlock().getPrice()));
                                out.printf("        <td>%s</td>%n", currencyFormat.format(line.getOrderTotal()));
                                out.println("    </tr>");
                }
         
                out.println("</table>");
         
                // Totals section
                out.println("<div class=\"totals-section\">");
                out.printf("    <div>SubTotal: <span style=\"margin-left: 20px;\">%s</span></div>%n", currencyFormat.format(subTotal));
                out.printf("    <div>Taxes @ 10%%: <span style=\"margin-left: 20px;\">%s</span></div>%n", currencyFormat.format(tax));
                out.printf("    <div>Carrier (5%% Flat): <span style=\"margin-left: 20px;\">%s</span></div>%n", currencyFormat.format(carrier));
                out.printf("    <div style=\"font-size: 18px; margin-top: 10px;\">Total: <span style=\"margin-left: 20px;\">%s</span></div>%n", currencyFormat.format(orderTotal));
                out.println("</div>");
         
                out.println("<a href=\"index\">Return to Shop</a>");
         
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
    }
        
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       // Get the part from the request parameter
                response.sendRedirect("cart");
        }
}
