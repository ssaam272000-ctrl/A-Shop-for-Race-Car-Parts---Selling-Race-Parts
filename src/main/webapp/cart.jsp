<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.racecarparts.model.ShoppingCart" %>
<%
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    if (cart == null) {
        cart = new ShoppingCart();
        session.setAttribute("cart", cart);
    }
    request.setAttribute("cart", cart);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - Race Car Parts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header class="my-4">
            <h1>Shopping Cart</h1>
            <a href="index.jsp" class="btn btn-secondary">Continue Shopping</a>
        </header>
        
        <c:choose>
            <c:when test="${cart.isEmpty()}">
                <div class="alert alert-info">
                    Your shopping cart is empty. <a href="index.jsp">Start shopping</a>
                </div>
            </c:when>
            <c:otherwise>
                <form action="updateCart" method="post">
                    <input type="hidden" name="action" value="update">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Part ID</th>
                                <th>Product Name</th>
                                <th>Unit Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cart.orderLines}" var="line">
                                <tr>
                                    <td>${line.engineBlock.partId}</td>
                                    <td>${line.engineBlock.name}</td>
                                    <td>$<fmt:formatNumber value="${line.engineBlock.price}" pattern="#,##0.00"/></td>
                                    <td>
                                        <input type="hidden" name="partId" value="${line.engineBlock.partId}">
                                        <input type="number" name="quantity" value="${line.quantity}" 
                                               min="0" class="form-control" style="width: 80px;">
                                    </td>
                                    <td>$<fmt:formatNumber value="${line.subtotal}" pattern="#,##0.00"/></td>
                                    <td>
                                        <a href="updateCart?action=remove&partId=${line.engineBlock.partId}" 
                                           class="btn btn-danger btn-sm">Remove</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <div class="row">
                        <div class="col-md-6">
                            <button type="submit" class="btn btn-primary">Update Cart</button>
                            <a href="updateCart?action=clear" class="btn btn-warning">Clear Cart</a>
                        </div>
                        <div class="col-md-6">
                            <div class="cart-summary">
                                <table class="table">
                                    <tr>
                                        <td><strong>Subtotal:</strong></td>
                                        <td class="text-end">$<fmt:formatNumber value="${cart.subtotal}" pattern="#,##0.00"/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Shipping:</strong></td>
                                        <td class="text-end">$<fmt:formatNumber value="${cart.shippingCost}" pattern="#,##0.00"/></td>
                                    </tr>
                                    <tr class="table-active">
                                        <td><strong>Total:</strong></td>
                                        <td class="text-end"><strong>$<fmt:formatNumber value="${cart.total}" pattern="#,##0.00"/></strong></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </form>
                
                <hr>
                
                <div class="checkout-section mt-4">
                    <h3>Checkout</h3>
                    <form action="checkout" method="post">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="customerName" class="form-label">Customer Name:</label>
                                    <input type="text" class="form-control" id="customerName" 
                                           name="customerName" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="billingAddress" class="form-label">Billing Address:</label>
                                    <textarea class="form-control" id="billingAddress" 
                                              name="billingAddress" rows="3" required></textarea>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success btn-lg">Complete Order</button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
