package com.racecarparts.view;

import java.util.List;
import com.racecarparts.shop.OrderLine;
import java.text.NumberFormat;
import java.util.Locale;

public class  InvoiceView extends BaseView { // The controller will receive the order information with the customer data and it retrieves the cart information from the session. It prepares the invoice data such as invoice number, calculates the tax, carrier, and total, and then it passes all this information to the view. The controller delegates to the invoice view with all the prepared data.
  public String render(String invoiceNumber, String invoiceDate, 
                          String customerName, String billingAddress, String customerNotes,
                          List<OrderLine> orderLines, 
                          double subtotal, double tax, double carrier, double total) {
          StringBuilder html = new StringBuilder();
          html.append(generateHeader("Invoice - " + invoiceNumber));
  // Invoice header
          html.append("<div style='text-align: center; margin-bottom: 30px;'>\n");
          html.append("    <h1 style='color: #1e90ff; margin: 0;'>INVOICE</h1>\n");
          html.append("</div>\n");
          // Company info box
          html.append(generateCompanyInfo(invoiceNumber, invoiceDate));
          // Customer info
          html.append(generateCustomerInfo(customerName, billingAddress, customerNotes));
          // Order items table
          html.append(generateOrderTable(orderLines));
  // Totals
          html.append(generateTotals(subtotal, tax, carrier, total));
          // Thank you message and home link
          html.append("<div style='text-align: center; margin-top: 40px;'>\n");
          html.append("    <h2 style='color: #1e90ff;'>Thank you for your order!</h2>\n");
          html.append("    <a href='/' class='cart-button'>Return to Shop</a>\n");
          html.append("</div>\n");
          html.append(generateFooter());
        return html.toString();
      }
  private String generateCompanyInfo(String invoiceNumber, String invoiceDate) { // This generates the blue company info box at the top of the invoice number and the dates.
      StringBuilder html = new StringBuilder();
      html.append("<div style='background: linear-gradient(135deg, #1e90ff 0%, #00bfff 100%); color: white; padding: 20px; border-radius: 10px; margin-bottom: 20px; position: relative;'>\n");
      html.append("    <h2 style='margin: 0 0 10px 0;'>Gabby-Hemanth-Saam LLC</h2>\n");
      html.append("    <p style='margin: 0;'>Race Car Parts Specialist</p>\n");
      html.append("    <div style='margin-top: 10px;'>\n");
      html.append("        <strong>Invoice #:</strong> ").append(escapeHtml(invoiceNumber)).append("<br>\n");
      html.append("        <strong>Date:</strong> ").append(escapeHtml(invoiceDate)).append("\n");
      html.append("    </div>\n");
      html.append("</div>\n");
      return html.toString();
  }
  private String generateCustomerInfo(String customerName, String billingAddress, String customerNotes) { // This generates the customer information with their name and billing address. If there are any notes, it will also display that.
      StringBuilder html = new StringBuilder();
      html.append("<div style='background-color: white; padding: 20px; border-radius: 10px; margin-bottom: 20px;'>\n");
      html.append("    <h3 style='color: #1e90ff; margin-top: 0;'>Customer Information</h3>\n");
      html.append("    <p><strong>Name:</strong> ").append(escapeHtml(customerName)).append("</p>\n");
      html.append("    <p><strong>Billing Address:</strong> ").append(escapeHtml(billingAddress)).append("</p>\n");
      if (customerNotes != null && !customerNotes.trim().isEmpty()) {
          html.append("    <p><strong>Notes:</strong> ").append(escapeHtml(customerNotes)).append("</p>\n");
      }
      html.append("</div>\n");
      return html.toString();
  }
  private String generateOrderTable(List<OrderLine> orderLines) { // This gives you a table of all the order items by using each order in the order lines list.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

      html.append("<style>\n");
      html.append("    .invoice-table {\n");
      html.append("        background-color: white;\n");
      html.append("        padding: 20px;\n");
      html.append("        border-radius: 10px;\n");
      html.append("        margin-bottom: 20px;\n");
      html.append("    }\n");
      html.append("    .invoice-table table {\n");
      html.append("        width: 100%;\n");
      html.append("        border-collapse: collapse;\n");
      html.append("    }\n");
      html.append("    .invoice-table th {\n");
      html.append("        background-color: #1e90ff;\n");
      html.append("        color: white;\n");
      html.append("        padding: 12px;\n");
      html.append("        text-align: left;\n");
      html.append("    }\n");
      html.append("    .invoice-table td {\n");
      html.append("        padding: 10px 12px;\n");
      html.append("        border-bottom: 1px solid #ddd;\n");
      html.append("    }\n");
      html.append("</style>\n");

      html.append("<div class='invoice-table'>\n");
      html.append("    <h3 style='color: #1e90ff; margin-top: 0;'>Order Details</h3>\n");
      html.append("    <table>\n");
      html.append("        <tr>\n");
      html.append("            <th>Part ID</th>\n");
      html.append("            <th>Description</th>\n");
      html.append("            <th>Unit Price</th>\n");
      html.append("            <th>Quantity</th>\n");
      html.append("            <th>Total</th>\n");
      html.append("        </tr>\n");

      for (OrderLine line : orderLines) { // This for loop will generate each row of the order table. It will use the order lines list to get each order and then it will get the part information and the quantity and the total for that order.
          html.append("        <tr>\n");
          html.append("            <td>").append(escapeHtml(line.getEngineBlock().getEngineName())).append("</td>\n");
          html.append("            <td>").append(escapeHtml(line.getEngineBlock().getDescription())).append("</td>\n");
          html.append("            <td>").append(currencyFormat.format(line.getEngineBlock().getPrice())).append("</td>\n");
          html.append("            <td>").append(line.getQuantity()).append("</td>\n");
          html.append("            <td>").append(currencyFormat.format(line.getOrderTotal())).append("</td>\n");
          html.append("        </tr>\n");
      }

      html.append("    </table>\n");
      html.append("</div>\n");

      return html.toString();
  }
  private String generateTotals(double subtotal, double tax, double carrier, double total) { // This generates the totals box with the subtotal, tax, carrier, and finaltotal.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
      html.append("<div style='background-color: white; padding: 20px; border-radius: 10px; max-width: 400px; margin-left: auto;'>\n");
      html.append("    <div>SubTotal: <span style='margin-left: 20px;'>").append(currencyFormat.format(subtotal)).append("</span></div>\n");
      html.append("    <div>Taxes @ 10%: <span style='margin-left: 20px;'>").append(currencyFormat.format(tax)).append("</span></div>\n");
      html.append("    <div>Carrier (5% Flat): <span style='margin-left: 20px;'>").append(currencyFormat.format(carrier)).append("</span></div>\n");
      html.append("    <div style='font-size: 18px; margin-top: 10px;'>Total: <span style='margin-left: 20px;'>").append(currencyFormat.format(total)).append("</span></div>\n");
      html.append("</div>\n");
      return html.toString();
  }
  
}