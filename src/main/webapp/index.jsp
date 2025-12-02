<%@ page import="java.util.List" %>
<%@ page import="com.inverse.model.Product" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inverse Ordering System | Race Car Parts</title>

    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background: #f8f9fa;
            color: #333;
        }

        header {
            background: linear-gradient(135deg, #111, #333);
            color: white;
            text-align: center;
            padding: 30px 0;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
        }

        header h1 {
            margin: 0;
            font-size: 2rem;
            letter-spacing: 1px;
        }

        header h3 {
            margin-top: 10px;
            font-weight: 400;
            font-size: 1.1rem;
            color: #ddd;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 40px auto;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
        }

        .card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            text-align: center;
            transition: all 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
        }

        .card img {
            width: 100%;
            height: 220px;
            object-fit: cover;
            border-bottom: 1px solid #ddd;
        }

        .card h3 {
            color: #222;
            margin: 15px 0 5px;
            font-size: 1.2rem;
        }

        .card p {
            color: #555;
            padding: 0 15px;
            min-height: 50px;
            font-size: 0.95rem;
        }

        .price {
            font-size: 18px;
            font-weight: bold;
            color: #0a9b31;
            margin: 15px 0;
        }

        .order-btn {
            background: #007bff;
            color: white;
            border: none;
            padding: 12px 22px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 0.95rem;
            transition: 0.2s;
            margin-bottom: 20px;
        }

        .order-btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <header>
        <h1>Inverse Ordering System</h1>
        <h3>Available Race Car Parts</h3>
    </header>

    <div class="container">
        <%
            List<Product> productList = (List<Product>) request.getAttribute("productList");
            if (productList != null && !productList.isEmpty()) {
                for (Product product : productList) {
        %>
            <div class="card">
                <!-- ✅ Corrected image path -->
                <img src="<%= request.getContextPath() + "/" + product.getImageUrl() %>"
                     alt="<%= product.getName() %>">

                <h3><%= product.getName() %></h3>
                <p><%= product.getDescription() %></p>

                <!-- 💰 Currency symbol -->
                <div class="price">₹<%= String.format("%.2f", product.getPrice()) %></div>

                <!-- ✅ Updated form to point to OrderServlet -->
                <form action="<%= request.getContextPath() %>/placeOrder" method="get">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <!-- You can make customerName dynamic later -->
                    <input type="hidden" name="customerName" value="Guest">
                    <button class="order-btn">Order Now</button>
                </form>
            </div>
        <%
                }
            } else {
        %>
            <p style="text-align:center; font-size:1.1rem; color:#666;">
                No products available.
            </p>
        <%
            }
        %>
    </div>
</body>
</html>
