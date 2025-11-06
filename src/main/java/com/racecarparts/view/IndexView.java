package com.racecarparts.view;

import java.util.Date;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
import com.racecarparts.shop.RaceCarPart;
import java.text.SimpleDateFormat;
import java.util.List;


public class IndexView extends BaseView { // This gives you the complete homepage showing the product catalog.  
  public String render(List<RaceCarPart> products, int cartItemCount) { // This handles the MVC flow for the homepage. The controller index servlet retrieves the product from the product catalog and it passes the products to the view.
      StringBuilder html = new StringBuilder();
      NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); 

      html.append(generateHeader("Race Car Parts Shop"));

      html.append("<div class='header'>\n");
      html.append("    <h1>A Shop for Race Car Parts</h1>\n");
      html.append("    <div>\n");
      html.append("        <a href='/demo' class='cart-button' style='margin-right: 10px;'>Demo</a>\n");
      html.append("        <a href='/cart' class='cart-button'>View Cart (").append(cartItemCount).append(")</a>\n");
      html.append("    </div>\n");
      html.append("</div>\n");

      html.append("<div class='subheader'>Hemanth-Saam LLC</div>\n");

      SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
      html.append("<div style='background-color: white; padding: 10px; border-radius: 5px; margin-bottom: 20px; display: inline-block;'>\n");
      html.append("    Date: ").append(dateFormat.format(new Date())).append("\n");
      html.append("</div>\n");

      html.append(generateProductGrid(products, currencyFormat));

      html.append("<div style='width: 100%; margin-top: 40px;'>\n");
      html.append("    <img src='/images/skagit-speedway.gif' alt='Sprint Car' style='width: 100%; height: auto; border-radius: 0;'>\n");
      html.append("</div>\n");
      html.append("</div>\n");

      html.append(generateFooter());

      return html.toString();
  }
  private String generateProductGrid(List<RaceCarPart> products, NumberFormat currencyFormat) { // This will generate a 3 column grid of product layout.
          StringBuilder html = new StringBuilder();

          html.append("<style>\n");
          html.append("    .product-grid {\n");
          html.append("        display: grid;\n");
          html.append("        grid-template-columns: repeat(3, 1fr);\n");
          html.append("        gap: 20px;\n");
          html.append("        margin-bottom: 20px;\n");
          html.append("    }\n");
          html.append("    .product-card {\n");
          html.append("        background-color: white;\n");
          html.append("        border-radius: 10px;\n");
          html.append("        padding: 20px;\n");
          html.append("        text-align: center;\n");
          html.append("        box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n");
          html.append("    }\n");
          html.append("    .product-card img {\n");
          html.append("        max-width: 100%;\n");
          html.append("        height: 200px;\n");
          html.append("        object-fit: contain;\n");
          html.append("        margin-bottom: 15px;\n");
          html.append("    }\n");
          html.append("    .price {\n");
          html.append("        color: #ff1493;\n");
          html.append("        font-size: 24px;\n");
          html.append("        font-weight: bold;\n");
          html.append("        margin: 10px 0;\n");
          html.append("    }\n");
          html.append("    .part-info {\n");
          html.append("        font-size: 14px;\n");
          html.append("        margin: 10px 0;\n");
          html.append("        color: #333;\n");
          html.append("    }\n");
          html.append("    .add-to-cart-btn {\n");
          html.append("        background-color: #00bfff;\n");
          html.append("        color: white;\n");
          html.append("        border: none;\n");
          html.append("        padding: 12px 24px;\n");
          html.append("        font-size: 16px;\n");
          html.append("        border-radius: 5px;\n");
          html.append("        cursor: pointer;\n");
          html.append("        font-weight: bold;\n");
          html.append("    }\n");
          html.append("    .add-to-cart-btn:hover {\n");
          html.append("        background-color: #1e90ff;\n");
          html.append("    }\n");
          html.append("</style>\n");

          html.append("<div class='product-grid'>\n");

          // Generate each product card
          for (RaceCarPart product : products) {
              html.append("    <div class='product-card'>\n");
              html.append("        <img src='").append(escapeHtml(product.getImageURL())).append("' alt='")
                  .append(escapeHtml(product.getEngineName())).append("'>\n");
              html.append("        <div class='price'>").append(currencyFormat.format(product.getPrice())).append("</div>\n");
              html.append("        <div class='part-info'><strong>").append(escapeHtml(product.getEngineName()))
                  .append("</strong> - ").append(escapeHtml(product.getDescription())).append("</div>\n");
              html.append("        <form action='/addToCart' method='post'>\n");
              html.append("            <input type='hidden' name='partId' value='").append(escapeHtml(product.getEngineName())).append("'>\n");
              html.append("            <input type='hidden' name='quantity' value='1'>\n");
              html.append("            <button type='submit' class='add-to-cart-btn'>Add to Cart</button>\n");
              html.append("        </form>\n");
              html.append("    </div>\n");
          }

          html.append("</div>\n");

          return html.toString();
      }
}
