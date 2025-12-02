<%@ page import="com.inverse.dao.OrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.inverse.dao.OrderDAO.Order" %>
<%@ page import="com.inverse.dao.OrderDAO.OrderItemRow" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order History | Inverse Ordering System</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        header {
            background: linear-gradient(135deg, #111, #333);
            color: white;
            text-align: center;
            padding: 20px 0;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
        }
        .container {
            width: 90%;
            max-width: 1000px;
            margin: 30px auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        a.btn {
            display: inline-block;
            background: #007bff;
            color: white;
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 6px;
            margin-top: 20px;
        }
        a.btn:hover {
            background: #0056b3;
        }
        .back {
            text-align: center;
        }
        .order-header {
            background: #eee;
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>
<header>
    <h1>Inverse Ordering System</h1>
    <h3>Order History</h3>
</header>

<div class="container">
    <h2>All Orders</h2>

    <%
        String customerName = (String) session.getAttribute("customerName");
        if (customerName == null || customerName.trim().isEmpty()) {
    %>
        <p style="text-align:center;">No customer found. Please place an order first.</p>
        <div class="back"><a href="products" class="btn">Back to Shop</a></div>
    <%
        } else {
            OrderDAO dao = new OrderDAO();
            List<Order> orders = dao.getOrdersByCustomer(customerName);

            if (orders == null || orders.isEmpty()) {
    %>
        <p style="text-align:center;">No orders found for <strong><%= customerName %></strong>.</p>
        <div class="back"><a href="products" class="btn">Back to Shop</a></div>
    <%
            } else {
    %>
        <p><strong>Customer:</strong> <%= customerName %></p>

        <table>
            <tr>
                <th>Order ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price (₹)</th>
                <th>Total (₹)</th>
                <th>Date</th>
            </tr>
            <%
                for (Order o : orders) {
                    for (OrderItemRow item : o.items) {
            %>
            <tr>
                <td><%= o.orderId %></td>
                <td><%= item.productName %></td>
                <td><%= item.quantity %></td>
                <td><%= String.format("%.2f", item.price) %></td>
                <td><%= String.format("%.2f", o.totalAmount) %></td>
                <td><%= o.orderDate %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <div class="back">
            
            <a href="products" class="btn">Back to Shop</a>
            <a href="logout" class="btn" style="background:#dc3545;">Switch Customer</a>
        </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
