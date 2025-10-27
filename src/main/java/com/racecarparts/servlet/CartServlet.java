package com.racecarparts.servlet;

import com.racecarparts.model.OrderLine;
import com.racecarparts.model.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Shopping Cart - Race Car Parts</title>");
        out.println("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("    <link rel=\"stylesheet\" href=\"css/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container\">");
        out.println("        <header class=\"my-4\">");
        out.println("            <h1>Shopping Cart</h1>");
        out.println("            <a href=\"index\" class=\"btn btn-secondary\">Continue Shopping</a>");
        out.println("        </header>");
        
        if (cart.isEmpty()) {
            out.println("        <div class=\"alert alert-info\">");
            out.println("            Your shopping cart is empty. <a href=\"index\">Start shopping</a>");
            out.println("        </div>");
        } else {
            out.println("        <form action=\"updateCart\" method=\"post\">");
            out.println("            <input type=\"hidden\" name=\"action\" value=\"update\">");
            out.println("            <table class=\"table table-striped\">");
            out.println("                <thead>");
            out.println("                    <tr>");
            out.println("                        <th>Part ID</th>");
            out.println("                        <th>Product Name</th>");
            out.println("                        <th>Unit Price</th>");
            out.println("                        <th>Quantity</th>");
            out.println("                        <th>Subtotal</th>");
            out.println("                        <th>Action</th>");
            out.println("                    </tr>");
            out.println("                </thead>");
            out.println("                <tbody>");
            
            for (OrderLine line : cart.getOrderLines()) {
                out.println("                    <tr>");
                out.printf("                        <td>%s</td>%n", line.getEngineBlock().getPartId());
                out.printf("                        <td>%s</td>%n", line.getEngineBlock().getName());
                out.printf("                        <td>$%.2f</td>%n", line.getEngineBlock().getPrice());
                out.println("                        <td>");
                out.printf("                            <input type=\"hidden\" name=\"partId\" value=\"%s\">%n", line.getEngineBlock().getPartId());
                out.printf("                            <input type=\"number\" name=\"quantity\" value=\"%d\" min=\"0\" class=\"form-control\" style=\"width: 80px;\">%n", line.getQuantity());
                out.println("                        </td>");
                out.printf("                        <td>$%.2f</td>%n", line.getSubtotal());
                out.println("                        <td>");
                out.printf("                            <a href=\"updateCart?action=remove&partId=%s\" class=\"btn btn-danger btn-sm\">Remove</a>%n", line.getEngineBlock().getPartId());
                out.println("                        </td>");
                out.println("                    </tr>");
            }
            
            out.println("                </tbody>");
            out.println("            </table>");
            out.println("            ");
            out.println("            <div class=\"row\">");
            out.println("                <div class=\"col-md-6\">");
            out.println("                    <button type=\"submit\" class=\"btn btn-primary\">Update Cart</button>");
            out.println("                    <a href=\"updateCart?action=clear\" class=\"btn btn-warning\">Clear Cart</a>");
            out.println("                </div>");
            out.println("                <div class=\"col-md-6\">");
            out.println("                    <div class=\"cart-summary\">");
            out.println("                        <table class=\"table\">");
            out.println("                            <tr>");
            out.println("                                <td><strong>Subtotal:</strong></td>");
            out.printf("                                <td class=\"text-end\">$%.2f</td>%n", cart.getSubtotal());
            out.println("                            </tr>");
            out.println("                            <tr>");
            out.println("                                <td><strong>Shipping:</strong></td>");
            out.printf("                                <td class=\"text-end\">$%.2f</td>%n", cart.getShippingCost());
            out.println("                            </tr>");
            out.println("                            <tr class=\"table-active\">");
            out.println("                                <td><strong>Total:</strong></td>");
            out.printf("                                <td class=\"text-end\"><strong>$%.2f</strong></td>%n", cart.getTotal());
            out.println("                            </tr>");
            out.println("                        </table>");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("        </form>");
            out.println("        ");
            out.println("        <hr>");
            out.println("        ");
            out.println("        <div class=\"checkout-section mt-4\">");
            out.println("            <h3>Checkout</h3>");
            out.println("            <form action=\"checkout\" method=\"post\">");
            out.println("                <div class=\"row\">");
            out.println("                    <div class=\"col-md-6\">");
            out.println("                        <div class=\"mb-3\">");
            out.println("                            <label for=\"customerName\" class=\"form-label\">Customer Name:</label>");
            out.println("                            <input type=\"text\" class=\"form-control\" id=\"customerName\" name=\"customerName\" required>");
            out.println("                        </div>");
            out.println("                    </div>");
            out.println("                    <div class=\"col-md-6\">");
            out.println("                        <div class=\"mb-3\">");
            out.println("                            <label for=\"billingAddress\" class=\"form-label\">Billing Address:</label>");
            out.println("                            <textarea class=\"form-control\" id=\"billingAddress\" name=\"billingAddress\" rows=\"3\" required></textarea>");
            out.println("                        </div>");
            out.println("                    </div>");
            out.println("                </div>");
            out.println("                <button type=\"submit\" class=\"btn btn-success btn-lg\">Complete Order</button>");
            out.println("            </form>");
            out.println("        </div>");
        }
        
        out.println("    </div>");
        out.println("    ");
        out.println("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }
}
