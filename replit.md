# Race Car Parts Shop - Java Web Application

## Overview
A Java web application with embedded Tomcat server for managing a shopping cart for race car engine blocks. Built for Babby-Hemanth-Saam LLC. The application features a product catalog, shopping cart management, and checkout functionality.

## Project Architecture
- **Language**: Java 11
- **Server**: Apache Tomcat 9.0.93 (Embedded)
- **Build Tool**: Maven
- **Frontend**: Servlet-based HTML rendering with Bootstrap 5
- **Backend**: Java Servlets with programmatic registration
- **Session Management**: HttpSession for cart persistence

## Project Structure
```
src/
├── main/
│   ├── java/com/racecarparts/
│   │   ├── model/
│   │   │   ├── EngineBlock.java       (Product model)
│   │   │   ├── OrderLine.java         (Cart line item)
│   │   │   └── ShoppingCart.java      (Cart management)
│   │   ├── servlet/
│   │   │   ├── IndexServlet.java      (Product catalog display)
│   │   │   ├── CartServlet.java       (Shopping cart display)
│   │   │   ├── AddToCartServlet.java  (Add items to cart)
│   │   │   ├── UpdateCartServlet.java (Update/remove/clear cart)
│   │   │   └── CheckoutServlet.java   (Order completion)
│   │   ├── util/
│   │   │   └── ProductCatalog.java    (Product data provider)
│   │   └── Main.java                  (Tomcat server initialization)
│   └── webapp/
│       └── css/style.css              (Custom styles)
├── pom.xml                            (Maven configuration)
└── target/                            (Compiled classes)
```

## Key Classes

### Model Layer
- **EngineBlock**: Product model with partId, name, and price
- **OrderLine**: Shopping cart line item containing EngineBlock, quantity, and calculated subtotal
- **ShoppingCart**: Manages cart operations with methods for:
  - Adding items (addItem)
  - Updating quantities (updateQuantity)
  - Removing items (removeItem)
  - Clearing cart (clear)
  - Calculating subtotal, shipping, and total

### Servlet Layer
- **IndexServlet**: Displays product catalog with 6 engine block products
- **CartServlet**: Shows shopping cart with items, quantities, prices, and checkout form
- **AddToCartServlet**: Handles adding products to cart (POST)
- **UpdateCartServlet**: Handles cart modifications - update quantities, remove items, clear cart (GET/POST)
- **CheckoutServlet**: Processes orders and displays confirmation (POST)

### Utility Layer
- **ProductCatalog**: Provides product data for 6 race car engine blocks with prices ranging from $2289.93 to $3439.93

## Features
- Product catalog with 6 race car engine blocks from Babby-Hemanth-Saam LLC
- Add to cart with quantity selection
- Shopping cart with dynamic calculations:
  - Subtotal (sum of all line items)
  - Shipping cost (10% of subtotal)
  - Total (subtotal + shipping)
- Cart management:
  - Update quantities
  - Remove individual items
  - Clear entire cart
- Session-based cart persistence
- Checkout with customer information
- Order confirmation page
- Responsive design with Bootstrap 5

## Technical Implementation

### Embedded Tomcat Setup
- Programmatic servlet registration (no web.xml scanning)
- Server bound to 0.0.0.0:5000 for Replit compatibility
- Cache-Control headers to prevent caching issues
- Direct servlet instantiation in Main.java

### Shopping Cart Logic
- Session-scoped cart storage
- Automatic quantity updates for duplicate items
- Zero-quantity removal
- Subtotal calculation with precision
- Shipping calculated as 10% of subtotal

## Running the Application
The application runs automatically via the configured workflow:
```bash
mvn compile exec:java -Dexec.mainClass="com.racecarparts.Main"
```

Access the application at the Replit preview URL on port 5000.

## Recent Changes

### November 07, 2025
- **Invoice Redesign**:
  - Completely redesigned invoice layout to match client mockup
  - Added company branding header with "A Shop for Race Car Parts" and logo
  - Included Casey Henderson contact information (address, phone)
  - Created side-by-side "Bill To" and "Ship To" sections
  - Reordered table columns to: QTY, Part Code, Part Description, Amount Paid, Total Cost
  - Updated totals section labels to "Deposit (8% Dep.)" and "Carrier (Tin Env.)"
  - Applied light blue/cyan color scheme (#B8DDE8, #D4EBF1) throughout invoice
  - Added authorization date message above order table

- **Color Scheme Update**:
  - Changed entire application background from green (#84b84c) to light blue (#B8DDE8)
  - Updated all pages (homepage, cart, checkout, invoice) to use consistent blue theme
  - Matches client mockup design specifications

### October 27, 2025
- **Initial Implementation**:
  - Created complete Maven project structure
  - Implemented all model classes (EngineBlock, OrderLine, ShoppingCart)
  - Built servlet layer with 5 servlets
  - Created ProductCatalog with 6 products
  - Set up embedded Tomcat server with programmatic servlet registration

- **Bug Fixes**:
  - Fixed UpdateCartServlet to handle both GET and POST requests
  - Standardized all servlet redirects to use servlet paths instead of JSP paths
  - Added processRequest method for consistent request handling
  - Verified end-to-end cart functionality

- **Architecture Decision**:
  - Switched from JSP to pure servlet-based HTML rendering to avoid JSP compilation issues with embedded Tomcat
  - Used programmatic servlet registration instead of annotation scanning for better control

## User Preferences
None specified yet.

## Next Steps (Optional Enhancements)
- Add input validation with error feedback for quantity updates
- Implement persistent storage (database) for products and orders
- Add product images and detailed descriptions
- Create admin panel for product management
- Add email confirmation for orders
- Implement payment integration
