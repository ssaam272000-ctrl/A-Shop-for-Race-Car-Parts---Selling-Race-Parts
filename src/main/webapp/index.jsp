<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.racecarparts.util.ProductCatalog" %>
<%@ page import="com.racecarparts.model.ShoppingCart" %>
<%
    request.setAttribute("products", ProductCatalog.getAllProducts());
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    int cartItems = (cart != null) ? cart.getTotalItems() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>A Shop for Race Car Parts - Babby-Hemanth-Saam LLC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container-fluid">
        <header class="header-bar">
            <h1 class="main-title">A Shop for Race Car Parts</h1>
            <a href="cart.jsp" class="cart-link">For Customer</a>
        </header>
        <h2 class="company-name">Babby-Hemanth-Saam LLC</h2>
        
        <div class="product-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card">
                    <div class="product-image">
                        <div class="engine-block-placeholder"></div>
                    </div>
                    <div class="product-info">
                        <div class="price">$<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/></div>
                        <form action="addToCart" method="post" class="add-form">
                            <input type="hidden" name="partId" value="${product.partId}">
                            <input type="hidden" name="quantity" value="1">
                            <button type="submit" class="btn-add">Add</button>
                        </form>
                    </div>
                    <div class="product-details">
                        <strong>Part ${product.partId}</strong> ${product.name}
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="footer-content">
            <div class="car-image-placeholder"></div>
            <% if (cartItems > 0) { %>
                <div class="cart-preview">
                    <h3>Shopping Cart:</h3>
                    <p>You have <%= cartItems %> item(s) in your cart</p>
                    <a href="cart.jsp" class="btn btn-primary">View Cart</a>
                </div>
            <% } %>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
