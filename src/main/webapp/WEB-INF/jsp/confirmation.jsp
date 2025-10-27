<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container mt-5">
        <div class="alert alert-success">
            <h1>Order Confirmed!</h1>
            <p>Thank you for your order, ${customerName}!</p>
            <p>Your order total: <strong>$<fmt:formatNumber value="${orderTotal}" pattern="#,##0.00"/></strong></p>
            <p>We will process your order shortly.</p>
        </div>
        
        <a href="index.jsp" class="btn btn-primary">Return to Shop</a>
    </div>
</body>
</html>
