package com.racecarparts.view;
import java.util.Date;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
import com.racecarparts.shop.RaceCarPart;

public class IndexView extends BaseView { // This gives you the complete homepage showing the product catalog.  
  public String render(List<RaceCarPart> products, int cartItemCount) {
    StringBuilder html = new StringBuilder();
    html.append(generateHeader("A Shop for Race Car Parts"));
    html.append("<h1>A Shop for Race Car Parts <a href=\"cart\" class=\"cart-link\">View Cart (" + cartItemCount + ")</a></h1>");
    html.append("<h2>Hemanth-Saam LLC</h2>");
    html.append("<p class=\"date\">Date: " + new Date() + "</p>");
    html.append("<ul>");
    for (RaceCarPart part : products) {
      html.append("<li>");
      html.append("<img src=\"images/" + part.getImageURL() + "\" alt=\"" + part.getDescription() + "\" style=\"width:200px; border-radius:5px; margin-bottom:10px;\">");
      html.append("<div class=\"price\">" + currencyFormat.format(part.getPrice()) + "</div>");
      html.append("<div class=\"part-info\"><strong>" + part.getEngineName() + "</strong> - " + part.getDescription() + "</div>");
      html.append("<form action=\"addToCart\" method=\"post\" style=\"margin-top: 10px;\">");
      html.append("<input type=\"hidden\" name=\"partId\" value=\"" + part.getEngineName() + "\">");
      html.append("<input type=\"hidden\" name=\"quantity\" value=\"1\">");
      html.append("<button type=\"submit\">Add to Cart</button>");
      html.append("</form>");
      html.append("</li>");
    }
    html.append("</ul>");
    
}