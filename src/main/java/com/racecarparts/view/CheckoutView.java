package com.racecarparts.view;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutView extends BaseView { // This will give you the checkout form.
  public String render(double cartTotal) {
      StringBuilder html = new StringBuilder();

      html.append(generateHeader("Checkout"));

      // Page header
      html.append("<div class='header'>\n");
      html.append("    <h1>Checkout</h1>\n");
      html.append("    <a href='/cart' class='cart-button'>Back to Cart</a>\n");
      html.append("</div>\n");

      // Checkout form
      html.append(generateCheckoutForm(cartTotal));

      html.append(generateFooter());

      return html.toString();
  }
  private String generateCheckoutForm(double cartTotal) { // This will generate the checkout form with customer information and order total.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

      html.append("<style>\n");
      html.append("    .checkout-container {\n");
      html.append("        background-color: white;\n");
      html.append("        padding: 30px;\n");
      html.append("        border-radius: 10px;\n");
      html.append("        max-width: 600px;\n");
      html.append("        margin: 0 auto;\n");
      html.append("    }\n");
      html.append("    .form-group {\n");
      html.append("        margin-bottom: 20px;\n");
      html.append("    }\n");
      html.append("    .form-group label {\n");
      html.append("        display: block;\n");
      html.append("        font-weight: bold;\n");
      html.append("        margin-bottom: 5px;\n");
      html.append("        color: #333;\n");
      html.append("    }\n");
      html.append("    .form-group input,\n");
      html.append("    .form-group textarea {\n");
      html.append("        width: 100%;\n");
      html.append("        padding: 10px;\n");
      html.append("        border: 1px solid #ddd;\n");
      html.append("        border-radius: 5px;\n");
      html.append("        font-size: 16px;\n");
      html.append("        box-sizing: border-box;\n");
      html.append("    }\n");
      html.append("    .form-group textarea {\n");
      html.append("        resize: vertical;\n");
      html.append("        min-height: 80px;\n");
      html.append("    }\n");
      html.append("    .checkout-btn {\n");
      html.append("        background-color: #00bfff;\n");
      html.append("        color: white;\n");
      html.append("        border: none;\n");
      html.append("        padding: 15px 40px;\n");
      html.append("        font-size: 18px;\n");
      html.append("        border-radius: 5px;\n");
      html.append("        cursor: pointer;\n");
      html.append("        font-weight: bold;\n");
      html.append("        width: 100%;\n");
      html.append("    }\n");
      html.append("    .checkout-btn:hover {\n");
      html.append("        background-color: #1e90ff;\n");
      html.append("    }\n");
      html.append("    .total-display {\n");
      html.append("        background-color: #f0f0f0;\n");
      html.append("        padding: 15px;\n");
      html.append("        border-radius: 5px;\n");
      html.append("        text-align: center;\n");
      html.append("        margin-bottom: 20px;\n");
      html.append("    }\n");
      html.append("</style>\n");

      html.append("<div class='checkout-container'>\n");
      html.append("    <h2 style='color: #1e90ff; margin-top: 0;'>Customer Information</h2>\n");

      html.append("    <div class='total-display'>\n");
      html.append("        <h3>Order Total: ").append(currencyFormat.format(cartTotal)).append("</h3>\n");
      html.append("    </div>\n");

      html.append("    <form action='/checkout' method='post'>\n");

      html.append("        <div class='form-group'>\n");
      html.append("            <label for='customerName'>Full Name *</label>\n");
      html.append("            <input type='text' id='customerName' name='customerName' required>\n");
      html.append("        </div>\n");

      html.append("        <div class='form-group'>\n");
      html.append("            <label for='billingAddress'>Billing Address *</label>\n");
      html.append("            <input type='text' id='billingAddress' name='billingAddress' required>\n");
      html.append("        </div>\n");

      html.append("        <div class='form-group'>\n");
      html.append("            <label for='customerNotes'>Notes / Special Instructions</label>\n");
      html.append("            <textarea id='customerNotes' name='customerNotes' placeholder='Enter any special delivery instructions or notes...'></textarea>\n");
      html.append("        </div>\n");

      html.append("        <button type='submit' class='checkout-btn'>Complete Purchase</button>\n");

      html.append("    </form>\n");
      html.append("</div>\n");

      return html.toString();
  }
}