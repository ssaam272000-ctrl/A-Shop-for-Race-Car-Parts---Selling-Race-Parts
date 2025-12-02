<%@ page import="java.util.*, com.inverse.dao.OrderDAO, com.inverse.dao.OrderDAO.Order" %>
<%@ page import="com.inverse.util.HtmlUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String customerName = (String) session.getAttribute("customerName");
    List<Order> orders = new ArrayList<>();

    if (customerName != null && !customerName.trim().isEmpty()) {
        try {
            OrderDAO dao = new OrderDAO();
            orders = dao.getOrdersByCustomer(customerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order History - A Shop for Race Car Parts</title>
    <style>
        /* (same styling as before) */
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        header {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            padding: 20px 40px;
            text-align: center;
            font-size: 24px;
        }

        .container {
            background: white;
            margin: 40px auto;
            padding: 40px;
            border-radius: 10px;
            max-width: 900px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
        }

        table th, table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        table th {
            background-color: #007bff;
            color: white;
        }

        table td {
            font-size: 15px;
            color: #333;
        }

        .no-orders {
            text-align: center;
            font-size: 18px;
            color: #666;
            margin: 50px 0;
        }

        .btn-container {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 25px;
            margin: 0 10px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
        }

        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<header>
    Order History 📜
</header>

<div class="container">
    <%
        if (customerName == null) {
    %>
        <div class="no-orders">
            <p>Please enter your name first to view order history.</p>
            <a href="setCustomer.jsp"><button class="btn">Enter Name</button></a>
        </div>
    <%
        } else if (orders == null || orders.isEmpty()) {
    %>
        <div class="no-orders">
            <p>No past orders found for <strong><%= HtmlUtils.escape(customerName) %></strong>.</p>
            <a href="products"><button class="btn">🛒 Shop Now</button></a>
        </div>
    <%
        } else {
    %>
        <h2>Order History for <%= HtmlUtils.escape(customerName) %></h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Total Amount (₹)</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Order o : orders) {
            %>
                <tr>
                    <td><%= o.orderId %></td>
                    <td><%= HtmlUtils.escape(String.valueOf(o.orderDate)) %></td>
                    <td><%= String.format("%.2f", o.totalAmount) %></td>
                    <td>
                        <a href="invoice.jsp?orderId=<%= o.orderId %>">
                            <button class="btn">View Invoice</button>
                        </a>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <div class="btn-container">
            <a href="products"><button class="btn">⬅ Back to Shop</button></a>
        </div>
    <%
        }
    %>
</div>

</body>
</html>
