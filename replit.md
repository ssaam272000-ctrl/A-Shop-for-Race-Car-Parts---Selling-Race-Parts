# Race Car Parts Shop - Java Web Application

## Overview
A Java web application with Tomcat integration for managing a shopping cart for race car engine blocks. Built for Babby-Hemanth-Saam LLC.

## Project Architecture
- **Language**: Java 11
- **Server**: Apache Tomcat (Embedded)
- **Build Tool**: Maven
- **Frontend**: JSP with JSTL, HTML, CSS, Bootstrap
- **Backend**: Java Servlets

## Project Structure
```
src/
├── main/
│   ├── java/com/racecarparts/
│   │   ├── model/
│   │   │   ├── EngineBlock.java
│   │   │   ├── OrderLine.java
│   │   │   └── ShoppingCart.java
│   │   ├── servlet/
│   │   │   ├── AddToCartServlet.java
│   │   │   ├── UpdateCartServlet.java
│   │   │   └── CheckoutServlet.java
│   │   └── Main.java (Tomcat embedded server)
│   └── webapp/
│       ├── WEB-INF/web.xml
│       ├── index.jsp
│       ├── cart.jsp
│       └── css/style.css
```

## Key Classes
- **EngineBlock**: Product model with name, price, and part ID
- **OrderLine**: Shopping cart line item with EngineBlock, quantity, and subtotal
- **ShoppingCart**: Manages cart operations, calculations (subtotal, shipping, total)

## Features
- Product catalog display
- Add to cart functionality
- Dynamic quantity updates
- Shopping cart calculations (subtotal, shipping, total)
- Session-based cart persistence
- Order checkout

## Recent Changes
- Initial project setup (October 27, 2025)
- Created all model classes, servlets, and JSP pages
- Configured Tomcat embedded server

## User Preferences
None specified yet.
