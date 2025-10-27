package com.racecarparts.servlet;

import com.racecarparts.model.EngineBlock;
import com.racecarparts.model.ShoppingCart;
import com.racecarparts.util.ProductCatalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet({"", "/", "/index"})
public class IndexServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        int cartItems = (cart != null) ? cart.getTotalItems() : 0;
        
        List<EngineBlock> products = ProductCatalog.getAllProducts();
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>A Shop for Race Car Parts - Babby-Hemanth-Saam LLC</title>");
        out.println("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");
        out.println("    <link rel=\"stylesheet\" href=\"css/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container-fluid\">");
        out.println("        <header class=\"header-bar\">");
        out.println("            <h1 class=\"main-title\">A Shop for Race Car Parts</h1>");
        out.println("            <a href=\"cart\" class=\"cart-link\">For Customer</a>");
        out.println("        </header>");
        out.println("        <h2 class=\"company-name\">Babby-Hemanth-Saam LLC</h2>");
        out.println("        ");
        out.println("        <div class=\"product-grid\">");
        
        for (EngineBlock product : products) {
            out.println("            <div class=\"product-card\">");
            out.println("                <div class=\"product-image\">");
            out.println("                    <div class=\"engine-block-placeholder\"></div>");
            out.println("                </div>");
            out.println("                <div class=\"product-info\">");
            out.printf("                    <div class=\"price\">$%.2f</div>%n", product.getPrice());
            out.println("                    <form action=\"addToCart\" method=\"post\" class=\"add-form\">");
            out.printf("                        <input type=\"hidden\" name=\"partId\" value=\"%s\">%n", product.getPartId());
            out.println("                        <input type=\"hidden\" name=\"quantity\" value=\"1\">");
            out.println("                        <button type=\"submit\" class=\"btn-add\">Add</button>");
            out.println("                    </form>");
            out.println("                </div>");
            out.println("                <div class=\"product-details\">");
            out.printf("                    <strong>Part %s</strong> %s%n", product.getPartId(), product.getName());
            out.println("                </div>");
            out.println("            </div>");
        }
        
        out.println("        </div>");
        out.println("        ");
        out.println("        <div class=\"footer-content\">");
        out.println("            <div class=\"car-image-placeholder\"></div>");
        
        if (cartItems > 0) {
            out.println("            <div class=\"cart-preview\">");
            out.println("                <h3>Shopping Cart:</h3>");
            out.printf("                <p>You have %d item(s) in your cart</p>%n", cartItems);
            out.println("                <a href=\"cart\" class=\"btn btn-primary\">View Cart</a>");
            out.println("            </div>");
        }
        
        out.println("        </div>");
        out.println("    </div>");
        out.println("    ");
        out.println("    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }
}
