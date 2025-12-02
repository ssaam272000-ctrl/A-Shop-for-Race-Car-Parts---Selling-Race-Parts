<%@ page import="java.util.*, com.inverse.dao.OrderDAO.Order, com.inverse.dao.OrderDAO.OrderItemRow, com.inverse.dao.OrderDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Fetch order ID from URL parameter
    String orderIdParam = request.getParameter("orderId");
    Order order = null;

    if (orderIdParam != null && !orderIdParam.trim().isEmpty()) {
        try {
            int orderId = Integer.parseInt(orderIdParam);
            OrderDAO dao = new OrderDAO();
            order = dao.readOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // constants used for display (must match servlet)
    double ALUMINUM_COATING = 500.0;
    double STEEL_COATING = 357.0;
    double TAX_RATE = 0.005; // 0.5%
    double ORDER_FEE = 0.50;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>A Shop for Race Car Parts — Invoice</title>
    <style>
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
            max-width: 700px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        .order-info {
            margin-bottom: 30px;
        }

        .order-info p {
            margin: 5px 0;
            font-size: 15px;
            color: #444;
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

        .total {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-top: 10px;
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

        .no-order {
            text-align: center;
            font-size: 18px;
            color: #666;
            margin-top: 50px;
        }

        .breakdown {
            text-align: right;
            margin-top: 10px;
            font-size: 15px;
        }
    </style>
</head>
<body>

<header>
    A Shop for Race Car Parts — Invoice
</header>

<%
    if (order == null) {
%>
    <div class="no-order">
        <p>❌ Sorry, no order found!</p>
        <a href="products"><button class="btn">Back to Shop</button></a>
    </div>
<%
    } else {
%>

<div class="container">
    <h2>Thank You for Your Order, <%= order.customerName %>! 🎉</h2>

    <div class="order-info">
        <p><b>Order ID:</b> <%= order.orderId %></p>
        <p><b>Date:</b> <%= order.orderDate %></p>
        <p><b>Customer:</b> <%= order.customerName %></p>
    </div>

    <table>
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Qty</th>
                <th>Unit Price (incl. coating)</th>
                <th>Line Subtotal</th>
            </tr>
        </thead>
        <tbody>
        <%
            double computedSubtotal = 0.0;
            if (order.items != null && !order.items.isEmpty()) {
                for (OrderItemRow row : order.items) {
                    // determine coating by product name
                    String pname = (row.productName == null) ? "" : row.productName.toLowerCase();
                    double coating = 0.0;
                    String coatingLabel = "";
                    if (pname.contains("alum") || pname.contains("aluminum")) {
                        coating = ALUMINUM_COATING;
                        coatingLabel = "Aluminum Coating";
                    } else if (pname.contains("steel")) {
                        coating = STEEL_COATING;
                        coatingLabel = "Steel Coating";
                    }

                    double unitPriceWithCoating = row.price;
                    double baseUnitPrice = unitPriceWithCoating - coating; // assuming price stored included coating
                    if (baseUnitPrice < 0) baseUnitPrice = 0; // safety
                    double lineSubtotal = row.quantity * unitPriceWithCoating;
                    computedSubtotal += lineSubtotal;
        %>
            <tr>
                <td style="text-align:left">
                    <div><%= row.productName %></div>
                    <% if (!coatingLabel.isEmpty()) { %>
                        <div style="font-size:12px; color:#666; margin-top:6px;">
                            Base: ₹ <%= String.format("%.2f", baseUnitPrice) %>
                            &nbsp;•&nbsp;
                            <span style="font-weight:bold;"><%= coatingLabel %> +₹ <%= String.format("%.2f", coating) %></span>
                        </div>
                    <% } %>
                </td>
                <td><%= row.quantity %></td>
                <td>₹ <%= String.format("%.2f", unitPriceWithCoating) %></td>
                <td>₹ <%= String.format("%.2f", lineSubtotal) %></td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <div class="breakdown">
        <div>Subtotal: ₹ <%= String.format("%.2f", computedSubtotal) %></div>
        <%
            double tax = Math.round(computedSubtotal * TAX_RATE * 100.0) / 100.0;
            double fee = ORDER_FEE;
            double grand = Math.round((computedSubtotal + tax + fee) * 100.0) / 100.0;
        %>
        <div>Tax (0.5%): ₹ <%= String.format("%.2f", tax) %></div>
        <div>Fee: ₹ <%= String.format("%.2f", fee) %></div>
        <div style="font-weight:bold; margin-top:8px; font-size:18px;">Grand Total: ₹ <%= String.format("%.2f", grand) %></div>
    </div>

    <div class="btn-container">
        <a href="products"><button class="btn">🛒 Continue Shopping</button></a>
        <a href="viewHistory"><button class="btn">📜 View Order History</button></a>
    </div>
</div>

<%
    }
%>

</body>
</html>
