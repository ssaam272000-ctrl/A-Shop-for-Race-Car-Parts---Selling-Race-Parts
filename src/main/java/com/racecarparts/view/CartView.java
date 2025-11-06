package com.racecarparts.view;

import com.racecarparts.shop.ShoppingCart;
import com.racecarparts.shop.OrderLine;
import com.racecarparts.shop.RaceCarPart;
import java.text.NumberFormat;
import java.util.Locale;


public class CartView extends BaseView { // This will give you the shopping cart page.
  public String render(ShoppingCart cart) { // This gives you the shopping cart page with all the parts in the cart.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

      html.append(generateHeader("Shopping Cart"));

      // Page header
      html.append("<div class='header'>\n");
      html.append("    <h1>Shopping Cart</h1>\n");
      html.append("    <a href='/' class='cart-button'>Continue Shopping</a>\n");
      html.append("</div>\n");

      // Check if cart is empty
      if (cart.isEmpty()) {
          html.append("<div style='background-color: white; padding: 40px; text-align: center; border-radius: 10px;'>\n");
          html.append("    <h2>Your cart is empty</h2>\n");
          html.append("    <p>Add some parts to get started!</p>\n");
          html.append("    <a href='/' class='cart-button'>Browse Products</a>\n");
          html.append("</div>\n");
      } else {
          html.append(generateCartTable(cart, currencyFormat));
          html.append(generateCartSummary(cart, currencyFormat));
          html.append(generateCheckoutButton());
      }

      html.append(generateFooter());

      return html.toString();
  }
  private String generateCartTable(ShoppingCart cart, NumberFormat currencyFormat) { // This gives you the cart items table with add and remove functions.
      StringBuilder html = new StringBuilder();

      html.append("<style>\n");
      html.append("    .cart-table {\n");
      html.append("        background-color: white;\n");
      html.append("        border-radius: 10px;\n");
      html.append("        padding: 20px;\n");
      html.append("        margin-bottom: 20px;\n");
      html.append("    }\n");
      html.append("    table {\n");
      html.append("        width: 100%;\n");
      html.append("        border-collapse: collapse;\n");
      html.append("    }\n");
      html.append("    th {\n");
      html.append("        background-color: #1e90ff;\n");
      html.append("        color: white;\n");
      html.append("        padding: 12px;\n");
      html.append("        text-align: left;\n");
      html.append("    }\n");
      html.append("    td {\n");
      html.append("        padding: 12px;\n");
      html.append("        border-bottom: 1px solid #ddd;\n");
      html.append("    }\n");
      html.append("    input[type='number'] {\n");
      html.append("        width: 60px;\n");
      html.append("        padding: 5px;\n");
      html.append("    }\n");
      html.append("    .update-btn, .remove-btn {\n");
      html.append("        padding: 8px 16px;\n");
      html.append("        border: none;\n");
      html.append("        border-radius: 5px;\n");
      html.append("        cursor: pointer;\n");
      html.append("        font-weight: bold;\n");
      html.append("        margin-right: 5px;\n");
      html.append("    }\n");
      html.append("    .update-btn {\n");
      html.append("        background-color: #00bfff;\n");
      html.append("        color: white;\n");
      html.append("    }\n");
      html.append("    .remove-btn {\n");
      html.append("        background-color: #ff6b6b;\n");
      html.append("        color: white;\n");
      html.append("    }\n");
      html.append("</style>\n");

      html.append("<div class='cart-table'>\n");
      html.append("    <table>\n");
      html.append("        <tr>\n");
      html.append("            <th>Part</th>\n");
      html.append("            <th>Description</th>\n");
      html.append("            <th>Price</th>\n");
      html.append("            <th>Quantity</th>\n");
      html.append("            <th>Subtotal</th>\n");
      html.append("            <th>Actions</th>\n");
      html.append("        </tr>\n");

      for (OrderLine line : cart.getOrderLines()) { // This for Loop will generate each row of the cart table.
          RaceCarPart part = line.getEngineBlock();
          int quantity = line.getQuantity();
          double subtotal = line.getOrderTotal();

          html.append("        <tr>\n");
          html.append("            <td><strong>").append(escapeHtml(part.getEngineName())).append("</strong></td>\n");
          html.append("            <td>").append(escapeHtml(part.getDescription())).append("</td>\n");
          html.append("            <td>").append(currencyFormat.format(part.getPrice())).append("</td>\n");
          html.append("            <td>\n");
          html.append("                <form action='/updateCart' method='post' style='display: inline;'>\n");
          html.append("                    <input type='hidden' name='partId' value='").append(escapeHtml(part.getEngineName())).append("'>\n");
          html.append("                    <input type='number' name='quantity' value='").append(quantity).append("' min='0'>\n");
          html.append("            </td>\n");
          html.append("            <td>").append(currencyFormat.format(subtotal)).append("</td>\n");
          html.append("            <td>\n");
          html.append("                    <button type='submit' class='update-btn'>Update</button>\n");
          html.append("                </form>\n");
          html.append("                <form action='/removeFromCart' method='post' style='display: inline;'>\n");
          html.append("                    <input type='hidden' name='partId' value='").append(escapeHtml(part.getEngineName())).append("'>\n");
          html.append("                    <button type='submit' class='remove-btn'>Remove</button>\n");
          html.append("                </form>\n");
          html.append("            </td>\n");
          html.append("        </tr>\n");
      }

      html.append("    </table>\n");
      html.append("</div>\n");

      return html.toString();
  }
  private String generateCartSummary(ShoppingCart cart, NumberFormat currencyFormat) { // This will give you a cart summary with the total price of the cart.
      StringBuilder html = new StringBuilder();

      html.append("<div style='background-color: white; padding: 20px; border-radius: 10px; text-align: right; margin-bottom: 20px;'>\n");
      html.append("    <h2>Cart Total: ").append(currencyFormat.format(cart.getSubTotal())).append("</h2>\n");
      html.append("</div>\n");

      return html.toString();
  }
  private String generateCheckoutButton() { // This will give you the checkout button.
      StringBuilder html = new StringBuilder();

      html.append("<div style='text-align: center;'>\n");
      html.append("    <a href='/checkout' class='cart-button' style='font-size: 20px; padding: 20px 40px;'>Proceed to Checkout</a>\n");
      html.append("</div>\n");

      return html.toString();
  }
}