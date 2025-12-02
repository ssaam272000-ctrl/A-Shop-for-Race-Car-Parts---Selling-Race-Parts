<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inverse.model.Product" %>
<%@ page import="com.inverse.util.HtmlUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🛒 A Shop for Race Car Parts</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #007bff;
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h1 {
            margin: 0;
            font-size: 24px;
        }

        .nav-buttons {
            display: flex;
            gap: 10px;
        }

        .nav-buttons form {
            display: inline;
        }

        .nav-buttons button {
            background: white;
            color: #007bff;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        .nav-buttons button:hover {
            background-color: #0056b3;
            color: white;
        }

        .container {
            max-width: 1000px;
            margin: 30px auto;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #007bff;
            margin-bottom: 25px;
        }

        .products {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
        }

        .card {
            border: 1px solid #ddd;
            border-radius: 10px;
            overflow: hidden;
            text-align: center;
            transition: 0.3s;
            background-color: #fafafa;
        }

        .card:hover {
            box-shadow: 0 3px 10px rgba(0,0,0,0.2);
        }

        .card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }

        .card h3 {
            margin: 10px 0;
            color: #333;
        }

        .price {
            color: #007bff;
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .quantity {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 10px;
        }

        .quantity button {
            width: 30px;
            height: 30px;
            background-color: #007bff;
            border: none;
            color: white;
            font-weight: bold;
            font-size: 18px;
            border-radius: 5px;
            cursor: pointer;
        }

        .quantity input {
            width: 40px;
            text-align: center;
            margin: 0 5px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .place-order {
            display: block;
            margin: 40px auto 0;
            background-color: #28a745;
            color: white;
            border: none;
            padding: 15px 30px;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .place-order:hover {
            background-color: #218838;
        }

        .user-info {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .error {
            color: #dc3545;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>

    <script>
        function increaseQuantity(id, maxStock) {
            // If this product is out of stock, show popup and do nothing
            if (maxStock <= 0) {
                alert("This product is out of stock.");
                return;
            }

            const input = document.getElementById("quantity_" + id);
            let value = parseInt(input.value || "0", 10);
            value++;

            // enforce max and show message if exceeding
            if (!isNaN(maxStock) && maxStock > 0 && value > maxStock) {
                alert("Only " + maxStock + " item(s) are available in stock.");
                value = maxStock;
            }

            input.value = value;
        }

        function decreaseQuantity(id) {
            const input = document.getElementById("quantity_" + id);
            let value = parseInt(input.value || "0", 10);

            if (value > 0) {
                value--;
            }

            input.value = value;
        }
    </script>
</head>
<body>
<header>
    <h1>🛍️ A Shop for Race Car Parts</h1>
    <div class="nav-buttons">
        <form action="viewHistory" method="get">
            <button type="submit">📜 View History</button>
        </form>
        <form action="logout" method="post">
            <button type="submit">🚪 Logout</button>
        </form>
    </div>
</header>

<div class="container">
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products == null) {
            products = (List<Product>) application.getAttribute("products"); // fallback
        }

        String customerName = (String) session.getAttribute("customerName");
        String error = (String) request.getAttribute("error");
    %>

    <% if (error != null) { %>
        <div class="error"><%= HtmlUtils.escape(error) %></div>
    <% } %>

    <% if (customerName != null) { %>
        <div class="user-info">
            Welcome back, <strong><%= HtmlUtils.escape(customerName) %></strong>!
        </div>
    <% } else { %>
        <div class="user-info">
            Welcome! Browse and place your order below.
        </div>
    <% } %>

    <h2>Available Products</h2>

    <form action="placeOrder" method="post">
        <div class="products">
            <%
                if (products != null && !products.isEmpty()) {
                    for (Product p : products) {
                        String img = (p.getImageUrl() != null && !p.getImageUrl().trim().isEmpty())
                                ? p.getImageUrl()
                                : "placeholder.jpg";
            %>
            <div class="card">
                <img src="<%= request.getContextPath() %>/images/<%= HtmlUtils.escape(img) %>"
                     alt="<%= HtmlUtils.escape(p.getName()) %>"
                     width="200" height="200"
                     style="border-radius:10px;">
                <h3><%= HtmlUtils.escape(p.getName()) %></h3>
                <div class="price">$<%= String.format("%.2f", p.getPrice()) %></div>

                <div class="quantity">
                    <button type="button" onclick="decreaseQuantity(<%= p.getProductId() %>)">-</button>
                    <input
                        type="number"
                        id="quantity_<%= p.getProductId() %>"
                        name="quantity_<%= p.getProductId() %>"
                        value="0"
                        min="0"
                        max="<%= p.getStock() %>">
                    <button type="button"
                            onclick="increaseQuantity(<%= p.getProductId() %>, <%= p.getStock() %>)">+
                    </button>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <p>No products available.</p>
            <% } %>
        </div>

        <button type="submit" class="place-order">🛒 Place Order</button>
    </form>
</div>
</body>
</html>
