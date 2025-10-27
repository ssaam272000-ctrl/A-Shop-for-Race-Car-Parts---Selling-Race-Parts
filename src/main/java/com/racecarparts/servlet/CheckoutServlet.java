package com.racecarparts.servlet;

import com.racecarparts.model.ShoppingCart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }
        
        String customerName = request.getParameter("customerName");
        double orderTotal = cart.getTotal();
        
        cart.clear();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Order Confirmation</title>");
        out.println("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("    <link rel=\"stylesheet\" href=\"css/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container mt-5\">");
        out.println("        <div class=\"alert alert-success\">");
        out.println("            <h1>Order Confirmed!</h1>");
        out.printf("            <p>Thank you for your order, %s!</p>%n", customerName);
        out.printf("            <p>Your order total: <strong>$%.2f</strong></p>%n", orderTotal);
        out.println("            <p>We will process your order shortly.</p>");
        out.println("        </div>");
        out.println("        ");
        out.println("        <a href=\"index\" class=\"btn btn-primary\">Return to Shop</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("cart");
    }
}
