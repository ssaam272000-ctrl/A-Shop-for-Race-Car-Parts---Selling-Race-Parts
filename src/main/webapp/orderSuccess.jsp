<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Placed Successfully</title>
    <style>
        body { font-family: 'Segoe UI'; background:#f8f9fa; text-align:center; padding-top:100px; }
        .box { background:white; display:inline-block; padding:30px; border-radius:10px;
               box-shadow:0 4px 10px rgba(0,0,0,0.1); }
        a.btn { display:inline-block; background:#007bff; color:white; padding:10px 20px;
                border-radius:5px; text-decoration:none; margin:10px; }
        a.btn:hover { background:#0056b3; }
    </style>
</head>
<body>
    <div class="box">
        <h2>🎉 Order placed successfully!</h2>
        <p>Your order has been placed under <strong><%= session.getAttribute("customerName") %></strong>.</p>
        <a class="btn" href="products">Back to Shop</a>
        <a class="btn" href="viewOrders">View Order History</a>
    </div>
</body>
</html>
