<%@ page import="com.inverse.dao.ProductDAO" %>
<%@ page import="com.inverse.model.Product" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation - Inverse Ordering System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 60px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .details {
            margin-top: 20px;
        }
        .details p {
            font-size: 16px;
            margin: 8px 0;
        }
        .btn {
            display: block;
            width: 100%;
            text-align: center;
            background-color: #007bff;
            color: white;
            padding: 12px;
            border-radius: 5px;
            margin-top: 25px;
            text-decoration: none;
            font-weight: bold;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            String productIdParam = request.getParameter("productId");
            if (productIdParam != null) {
                int productId = Integer.parseInt(productIdParam);
                ProductDAO dao = new ProductDAO();
                List<Product> allProducts = dao.getAllProducts();
                Product selectedProduct = null;

                for (Product p : allProducts) {
                    if (p.getProductId() == productId) {
                        selectedProduct = p;
                        break;
                    }
                }

                if (selectedProduct != null) {
        %>
                    <h2>Order Confirmation</h2>
                    <div class="details">
                        <p><strong>Product:</strong> <%= selectedProduct.getName() %></p>
                        <p><strong>Description:</strong> <%= selectedProduct.getDescription() %></p>
                        <p><strong>Price:</strong> $<%= String.format("%.2f", selectedProduct.getPrice()) %></p>
                        <p><strong>Cost:</strong> $<%= String.format("%.2f", selectedProduct.getCost()) %></p>
                    </div>

                    <a href="invoice.jsp?productId=<%= selectedProduct.getProductId() %>" class="btn">
                        Generate Invoice
                    </a>
        <%
                } else {
        %>
                    <h2>Product not found!</h2>
                    <p>Please return to the home page and try again.</p>
                    <a href="products" class="btn">Back to Shop</a>
        <%
                }
            } else {
        %>
                <h2>No product selected!</h2>
                <a href="products" class="btn">Back to Shop</a>
        <%
            }
        %>
    </div>
</body>
</html>
