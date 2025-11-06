<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.racecarparts.model.EngineBlock" %>
<%@ page import="com.racecarparts.util.ProductCatalog" %>
<%@ page import="com.racecarparts.model.ShoppingCart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    // Make products available to JSTL
    request.setAttribute("parts", ProductCatalog.getAllProducts());
    
    // Get cart items count
    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    int cartItems = (cart != null) ? cart.getTotalItems() : 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>A Shop for Race Car Parts</title>
<style>
/* body {
    background-color: hsl(89, 43%, 51%);
    font-family: Arial, sans-serif;
    padding: 20px;
}
h1 {
    background-color: hsl(185, 65%, 70%);
    padding: 15px;
    margin: 10px 0;
}
h2 {
    background-color: DodgerBlue;
    color: white;
    padding: 10px;
    margin: 10px 0;
}
ul {
    list-style-type: none;
    padding: 0;
}
li {
    background-color: white;
    margin: 10px 0;
    padding: 15px;
    border-radius: 5px;
}
.price {
    font-size: 24px;
    font-weight: bold;
    color: #e91e63;
}
.cart-link {
    float: right;
    background-color: white;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 5px;
    color: #0097a7;
    font-weight: bold;
}
button {
    background-color: #00bcd4;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
}
button:hover {
    background-color: #0097a7;
} */
</style>
</head>

<body>
    <section>
        <h1>A Shop for Race Car Parts <a href="cart" class="cart-link">View Cart (<%= cartItems %>)</a></h1>
        <h2>Hemanth-Saam LLC</h2>
        <p>Date: <%= new Date() %></p>
    
    </section>
    <section>
          <ul>
        <c:forEach items="${parts}" var="part">
            <li>
                <div class="price">$<fmt:formatNumber value="${part.price}" pattern="#,##0.00"/></div>
                <div><strong>Part ${part.partId}</strong> - ${part.name}</div>
                <form action="addToCart" method="post">
                    <input type="hidden" name="partId" value="${part.partId}">
                    <input type="hidden" name="quantity" value="1">
                    <button type="submit">Add to Cart</button>
                </form>
            </li>
        </c:forEach>
        </ul>      
    </section>
    <section>
       <h3>Shop Owner's Report</h3> 
    </section>    


</body>
</html>
