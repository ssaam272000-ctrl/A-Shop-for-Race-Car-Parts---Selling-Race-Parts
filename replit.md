# Race Car Parts Shop - Java Web Application

## Overview
A Java web application with embedded Tomcat server for managing a shopping cart for race car engine blocks. Built for Babby-Hemanth-Saam LLC. The application features a product catalog, shopping cart management, and checkout functionality.

## Project Architecture
- **Language**: Java 11
- **Server**: Apache Tomcat 9.0.93 (Embedded)
- **Build Tool**: Maven
- **Database**: PostgreSQL (Replit-managed)
- **Frontend**: Servlet-based HTML rendering with Bootstrap 5
- **Backend**: Java Servlets with programmatic registration
- **Session Management**: HttpSession for cart persistence
- **Persistence**: JDBC for database operations

## Project Structure
```
src/
├── main/
│   ├── java/com/racecarparts/
│   │   ├── dao/
│   │   │   └── OrderDAO.java          (Database operations for orders)
│   │   ├── Decorator/
│   │   │   ├── PartDecorator.java
│   │   │   ├── AluminumCoatingDecorator.java
│   │   │   ├── SteelCoatingDecorator.java
│   │   │   └── PerformanceTuningDecorator.java
│   │   ├── Factory/
│   │   │   └── RaceCarPartFactory.java
│   │   ├── shop/
│   │   │   ├── RaceCarPart.java       (Product model)
│   │   │   ├── EngineBlock.java       (Engine block implementation)
│   │   │   ├── OrderLine.java         (Cart line item)
│   │   │   └── ShoppingCart.java      (Cart management)
│   │   ├── servlet/
│   │   │   ├── IndexServlet.java      (Product catalog display)
│   │   │   ├── CartServlet.java       (Shopping cart display)
│   │   │   ├── AddToCartServlet.java  (Add items to cart)
│   │   │   ├── UpdateCartServlet.java (Update/remove/clear cart)
│   │   │   └── CheckoutServlet.java   (Order completion)
│   │   ├── util/
│   │   │   ├── ProductCatalog.java    (Product data provider)
│   │   │   ├── DatabaseConnection.java (Database connection manager)
│   │   │   └── DatabaseInitializer.java (Database schema setup)
│   │   ├── view/
│   │   │   ├── BaseView.java
│   │   │   ├── IndexView.java
│   │   │   ├── CartView.java
│   │   │   ├── CheckoutView.java
│   │   │   └── InvoiceView.java
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

## Database Integration

### Database Schema
The application uses PostgreSQL with the following tables:

**products** - Product catalog
- id (SERIAL PRIMARY KEY)
- name (VARCHAR 255)
- description (TEXT)
- base_price (DECIMAL 10,2)
- image_url (VARCHAR 255)
- created_at (TIMESTAMP)

**orders** - Customer orders
- id (SERIAL PRIMARY KEY)
- customer_name (VARCHAR 255)
- customer_email (VARCHAR 255)
- total_amount (DECIMAL 10,2)
- order_date (TIMESTAMP)

**order_items** - Items in each order
- id (SERIAL PRIMARY KEY)
- order_id (INTEGER, foreign key to orders)
- product_id (INTEGER, foreign key to products)
- product_name (VARCHAR 255)
- quantity (INTEGER)
- unit_price (DECIMAL 10,2)
- total_price (DECIMAL 10,2)
- decorators (TEXT)

### Database Connection
The application connects to Replit's PostgreSQL database using:
- **Connection Manager**: `DatabaseConnection.java` - Uses DATABASE_URL environment variable
- **Schema Setup**: `DatabaseInitializer.java` - Automatically creates tables on startup
- **Data Access**: `OrderDAO.java` - Handles all order persistence operations
- **JDBC Driver**: PostgreSQL JDBC Driver v42.7.1

### How It Works
1. Application starts and initializes database schema (if not exists)
2. When a customer completes checkout, order is saved to database
3. Order ID from database is used as the invoice number
4. All order details and line items are persisted in transactions

## Recent Changes

### November 8, 2025
- **Database Integration**:
  - Added PostgreSQL database support
  - Created database schema with products, orders, and order_items tables
  - Implemented DatabaseConnection utility using DATABASE_URL from Replit
  - Added DatabaseInitializer for automatic schema creation
  - Built OrderDAO for order persistence with transaction support
  - Updated CheckoutServlet to save orders to database
  - Database invoice number now matches actual order ID

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
