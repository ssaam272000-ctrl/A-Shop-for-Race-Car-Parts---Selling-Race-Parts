# Database Integration Guide

## Overview
Your race car parts shop now stores all orders in a PostgreSQL database, which is automatically provided and managed by Replit.

## What's Been Set Up

### 1. Database Tables
Three tables have been created to store your data:

**orders** - Stores customer order information
- Order ID (auto-generated)
- Customer name
- Billing address/email
- Total amount
- Order date (automatically set)

**order_items** - Stores individual items in each order
- Item ID
- Order ID (links to the order)
- Product name
- Quantity
- Unit price
- Total price
- Decorators (coating/tuning options)

**products** - Reserved for future product catalog storage
- Product ID
- Name, description, price
- Image URL

### 2. Automatic Order Saving
When a customer completes checkout:
1. Their order details are automatically saved to the database
2. All items in their cart are saved with quantities and prices
3. The invoice number shown to the customer is the actual database order ID
4. Everything happens in a transaction (all-or-nothing for data safety)

### 3. Replit Database Integration
Your application connects to Replit's PostgreSQL database using:
- **DATABASE_URL** environment variable (automatically set by Replit)
- PostgreSQL JDBC Driver (handles all database communication)
- Automatic connection management (connects when needed, closes safely)

## How It Works

### Database Connection
The `DatabaseConnection.java` class:
- Automatically converts Replit's database URL to the correct format
- Extracts username and password from the URL
- Manages connections safely

### Order Storage
When checkout happens (`CheckoutServlet.java`):
```java
OrderDAO orderDAO = new OrderDAO();
int orderId = orderDAO.saveOrder(customerName, billingAddress, customerNotes, orderLines, orderTotal);
```

The order and all its items are saved atomically in a transaction.

### Database Initialization
On server startup (`Main.java`):
- Checks if tables exist
- Creates them if they don't
- Safe to run multiple times (won't duplicate tables)

## Viewing Your Data

You can query your database directly using the Replit database tools or SQL commands:

**View recent orders:**
```sql
SELECT id, customer_name, total_amount, order_date 
FROM orders 
ORDER BY order_date DESC 
LIMIT 10;
```

**View order items for a specific order:**
```sql
SELECT product_name, quantity, unit_price, total_price 
FROM order_items 
WHERE order_id = 1;
```

**Count total orders:**
```sql
SELECT COUNT(*) FROM orders;
```

## Benefits

1. **Persistent Storage** - Orders are saved permanently, not lost when the server restarts
2. **Data Integrity** - Transactions ensure orders are saved completely or not at all
3. **Scalability** - PostgreSQL can handle thousands of orders efficiently
4. **Reporting** - You can run queries to analyze sales, popular products, etc.
5. **Replit Managed** - Database backups, scaling, and maintenance handled by Replit

## Code Structure

```
src/main/java/com/racecarparts/
├── dao/
│   └── OrderDAO.java           # Database operations for orders
├── util/
│   ├── DatabaseConnection.java # Database connection manager
│   └── DatabaseInitializer.java # Database schema setup
└── servlet/
    └── CheckoutServlet.java    # Updated to save orders
```

## Next Steps (Optional)

You could enhance this further by:
- Loading products from the database instead of hardcoding them
- Creating an admin panel to view orders
- Generating reports on sales data
- Adding customer accounts and order history
- Implementing search and filtering for orders
