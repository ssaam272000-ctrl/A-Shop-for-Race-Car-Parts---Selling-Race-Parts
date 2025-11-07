package com.racecarparts.view;

public class BaseView { // Used to provide the HTML generation utility for all the views.

  protected String generateHeader(String title) { // This method generates the common header across all webpages.
      StringBuilder html = new StringBuilder();  //This includes CSS styling for the header, green background, and a grid layout for the parts.
      html.append("<!DOCTYPE html>\n");
      html.append("<html>\n");
      html.append("<head>\n");
      html.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      html.append("    <title>").append(title).append("</title>\n");
      html.append("    <style>\n");
      html.append("        body { \n");
      html.append("            background-color: hsl(89, 43%, 51%); \n");
      html.append("            font-family: Arial, sans-serif; \n");
      html.append("            margin: 0; \n");
      html.append("            padding: 20px; \n");
      html.append("        }\n");
      html.append("        .header { \n");
      html.append("            background: linear-gradient(135deg, #1e90ff 0%, #00bfff 100%); \n");
      html.append("            color: white; \n");
      html.append("            padding: 20px; \n");
      html.append("            text-align: center; \n");
      html.append("            border-radius: 10px; \n");
      html.append("            margin-bottom: 20px; \n");
      html.append("            display: flex; \n");
      html.append("            justify-content: space-between; \n");
      html.append("            align-items: center; \n");
      html.append("        }\n");
      html.append("        .header h1 { \n");
      html.append("            margin: 0; \n");
      html.append("            font-size: 36px; \n");
      html.append("        }\n");
      html.append("        .subheader { \n");
      html.append("            background-color: #1e90ff; \n");
      html.append("            color: white; \n");
      html.append("            padding: 15px; \n");
      html.append("            border-radius: 8px; \n");
      html.append("            margin-bottom: 20px; \n");
      html.append("            font-size: 24px; \n");
      html.append("            font-weight: bold; \n");
      html.append("        }\n");
      html.append("        .cart-button { \n");
      html.append("            background-color: white; \n");
      html.append("            color: #00bfff; \n");
      html.append("            border: none; \n");
      html.append("            padding: 15px 30px; \n");
      html.append("            font-size: 18px; \n");
      html.append("            font-weight: bold; \n");
      html.append("            border-radius: 8px; \n");
      html.append("            cursor: pointer; \n");
      html.append("            text-decoration: none; \n");
      html.append("            display: inline-block; \n");
      html.append("        }\n");
      html.append("        .cart-button:hover { \n");
      html.append("            background-color: #f0f0f0; \n");
      html.append("        }\n");
      html.append("    </style>\n");
      html.append("</head>\n");
      html.append("<body>\n");
      return html.toString();
  }
  protected String generateFooter() { // This method generates the common footer across all webpages.
    return "</body>\n</html>\n"; // This is the HTML for the footer.
}
  protected String escapeHtml(String text) {
      if (text == null) {
          return "";
      }
      return text.replace("&", "&amp;") // This escapes the HTML special characters.
                 .replace("<", "&lt;")
                 .replace(">", "&gt;")
                 .replace("\"", "&quot;")
                 .replace("'", "&#x27;");
    
    
    
      }
  
  }
  

