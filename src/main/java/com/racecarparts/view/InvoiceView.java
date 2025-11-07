package com.racecarparts.view;

import java.util.List;
import com.racecarparts.shop.OrderLine;
import java.text.NumberFormat;
import java.util.Locale;

public class  InvoiceView extends BaseView { // The controller will receive the order information with the customer data and it retrieves the cart information from the session. It prepares the invoice data such as invoice number, calculates the tax, carrier, and total, and then it passes all this information to the view. The controller delegates to the invoice view with all the prepared data.
  public String render(String invoiceNumber, String invoiceDate,
                          String customerName, String billingAddress, String customerNotes,
                          List<OrderLine> orderLines,
                          double subtotal, double tax, double carrier, double total) { // This method generates the complete invoice page.
          StringBuilder html = new StringBuilder();
          html.append(generateHeader("Invoice - " + invoiceNumber));

          html.append("<style>\n");
          html.append("    body { background-color: #B8DDE8 !important; }\n");
          html.append("</style>\n");

          html.append("<div style='max-width: 1000px; margin: 0 auto; background: #B6EFE2; padding: 40px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>\n");

          html.append(generateCompanyHeader(invoiceNumber, invoiceDate));
          html.append(generateCustomerInfo(customerName, billingAddress, customerNotes));
          html.append(generateOrderTable(orderLines));
          html.append(generateTotals(subtotal, tax, carrier, total));

          html.append("<div style='text-align: center; margin-top: 40px;'>\n");
          html.append("    <a href='/' class='cart-button'>Return to Shop</a>\n");
          html.append("</div>\n");

          html.append("</div>\n");
          html.append(generateFooter());
        return html.toString();
      }
      
      private String generateCompanyHeader(String invoiceNumber, String invoiceDate) { // This generates the company header with the invoice number and date.
              StringBuilder html = new StringBuilder();
              html.append("<h1 style='text-align: center; margin: 0 0 15px 0; color: #000; font-size: 32px; font-weight: bold;'>")
                    .append("A Shop for Race Car Parts")
                    .append("</h1>\n");
              html.append("<div style='background-color: #A7D8E8; padding: 20px; border-radius: 5px; margin-bottom: 30px;'>\n");
              html.append("  <div style='display: flex; justify-content: space-between; align-items: flex-start; gap: 20px;'>\n");
              html.append("    <div style='flex: 0 0 30%;'>\n");
              html.append("      <img src='/images/racingengine.jpg' alt='Engine Image' ")
                  .append("style='width: 100%; height: auto; border-radius: 8px; object-fit: cover;'>\n");
              html.append("    </div>\n");
              html.append("    <div style='flex: 1; background-color: #D4EBF1; padding: 10px 15px; border-radius: 5px;'>\n");
              html.append("      <strong>Hemanth–Saam LLC</strong><br>\n");
              html.append("      Parts 1 Boulevard<br>\n");
              html.append("      Columbus, GA 31904 US<br>\n");
              html.append("      1-800-272-7625\n");
              html.append("    </div>\n");
              html.append("    <div style='flex: 0 0 25%; background-color: #B8DDE8; padding: 15px; border-radius: 5px; text-align: right;'>\n");
              html.append("      <h2 style='margin: 0 0 10px 0; font-size: 20px; color: #000;'>INVOICE</h2>\n");
              html.append("      <div style='font-size: 14px; color: #000;'>\n");
              html.append("        <strong>Invoice Date:</strong><br>")
                  .append("<span>").append(escapeHtml(invoiceDate)).append("</span><br><br>\n");
              html.append("        <strong>Invoice #:</strong><br>")
                  .append("<span>").append(escapeHtml(invoiceNumber)).append("</span>\n");
              html.append("      </div>\n");
              html.append("    </div>\n");
              html.append("  </div>\n");
              html.append("</div>\n");
              return html.toString();
          }

      private String generateCustomerInfo(String customerName, String billingAddress, String customerNotes) { // This generates the customer information with their name and billing address. If there are any notes, it will also display that.
      StringBuilder html = new StringBuilder();

      html.append("<div style='display: flex; justify-content: space-between; margin-bottom: 30px; gap: 20px;'>\n");

      html.append("  <div style='flex: 1; padding: 15px; background-color: #D4EBF1; border: 1px solid #999; border-radius: 5px;'>\n");
      html.append("    <h3 style='margin: 0 0 10px 0; color: #333; font-size: 18px; font-weight: bold;'>Bill To:</h3>\n");
      html.append("    <div style='color: #333; line-height: 1.6; font-size: 14px;'>\n");
      html.append("      <strong>").append(escapeHtml(customerName)).append("</strong><br>\n");
      html.append("      ").append(escapeHtml(billingAddress)).append("\n");
      if (customerNotes != null && !customerNotes.trim().isEmpty()) {
          html.append("      <br><br><em>Notes: ").append(escapeHtml(customerNotes)).append("</em>\n");
      }
      html.append("    </div>\n");
      html.append("  </div>\n");
      html.append("  <div style='flex: 1; padding: 15px; background-color: #D4EBF1; border: 1px solid #999; border-radius: 5px;'>\n");
      html.append("    <h3 style='margin: 0 0 10px 0; color: #333; font-size: 18px; font-weight: bold;'>Ship To:</h3>\n");
      html.append("    <div style='color: #333; line-height: 1.6; font-size: 14px;'>\n");
      html.append("      <strong>").append(escapeHtml(customerName)).append("</strong><br>\n");
      html.append("      ").append(escapeHtml(billingAddress)).append("\n");
      html.append("    </div>\n");
      html.append("  </div>\n");
      html.append("</div>\n");

      return html.toString();
  }
      
  private String generateOrderTable(List<OrderLine> orderLines) { // This generates the order table with the order lines.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

      html.append("<style>\n");
      html.append("    .invoice-table table {\n");
      html.append("        width: 100%;\n");
      html.append("        border-collapse: collapse;\n");
      html.append("        margin-bottom: 20px;\n");
      html.append("        background-color: white;\n");
      html.append("    }\n");
      html.append("    .invoice-table th {\n");
      html.append("        background-color: #B8DDE8;\n");
      html.append("        color: #333;\n");
      html.append("        padding: 10px;\n");
      html.append("        text-align: left;\n");
      html.append("        font-weight: bold;\n");
      html.append("        border: 1px solid #999;\n");
      html.append("        font-size: 14px;\n");
      html.append("    }\n");
      html.append("    .invoice-table td {\n");
      html.append("        padding: 10px;\n");
      html.append("        border: 1px solid #999;\n");
      html.append("        color: #333;\n");
      html.append("        background-color: white;\n");
      html.append("        font-size: 14px;\n");
      html.append("    }\n");
      html.append("</style>\n");

      html.append("<div class='invoice-table'>\n");
      html.append("    <p style='margin-bottom: 10px; color: #333;'><strong>This order is authorized to be processed on: ").append(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy"))).append("</strong></p>\n");
      html.append("    <table>\n");
      html.append("        <tr>\n");
      html.append("            <th>QTY</th>\n");
      html.append("            <th>Part Code</th>\n");
      html.append("            <th>Part Description</th>\n");
      html.append("            <th>Amount Paid</th>\n");
      html.append("            <th>Total Cost</th>\n");
      html.append("        </tr>\n");

      for (OrderLine line : orderLines) { // This for loop will generate each row of the order table.
          html.append("        <tr>\n");
          html.append("            <td>").append(line.getQuantity()).append("</td>\n");
          html.append("            <td>").append(escapeHtml(line.getEngineBlock().getEngineName())).append("</td>\n");
          html.append("            <td>").append(escapeHtml(line.getEngineBlock().getDescription())).append("</td>\n");
          html.append("            <td>").append(currencyFormat.format(line.getEngineBlock().getPrice())).append("</td>\n");
          html.append("            <td>").append(currencyFormat.format(line.getOrderTotal())).append("</td>\n");
          html.append("        </tr>\n");
      }

      html.append("    </table>\n");
      html.append("</div>\n");

      return html.toString();
  }
      
  private String generateTotals(double subtotal, double tax, double carrier, double total) { // This generates the totals section with the subtotal, tax, carrier, and total.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

      html.append("<div style='max-width: 350px; margin-left: auto; margin-top: 20px;'>\n");
      html.append("    <div style='display: flex; justify-content: space-between; padding: 8px 12px; background-color: #D4EBF1;'>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>SubTotal:</span>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>").append(currencyFormat.format(subtotal)).append("</span>\n");
      html.append("    </div>\n");
      html.append("    <div style='display: flex; justify-content: space-between; padding: 8px 12px; background-color: #D4EBF1; margin-top: 2px;'>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>Taxes @ 10%:</span>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>").append(currencyFormat.format(tax)).append("</span>\n");
      html.append("    </div>\n");
      html.append("    <div style='display: flex; justify-content: space-between; padding: 8px 12px; background-color: #D4EBF1; margin-top: 2px;'>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>Carrier [5% Flat]:</span>\n");
      html.append("        <span style='color: #333; font-size: 14px;'>").append(currencyFormat.format(carrier)).append("</span>\n");
      html.append("    </div>\n");
      html.append("    <div style='display: flex; justify-content: space-between; padding: 12px; font-size: 16px; font-weight: bold; background-color: #B8DDE8; margin-top: 10px;'>\n");
      html.append("        <span style='color: #333;'>Total:</span>\n");
      html.append("        <span style='color: #333;'>").append(currencyFormat.format(total)).append("</span>\n");
      html.append("    </div>\n");
      html.append("</div>\n");

      return html.toString();
  }

}