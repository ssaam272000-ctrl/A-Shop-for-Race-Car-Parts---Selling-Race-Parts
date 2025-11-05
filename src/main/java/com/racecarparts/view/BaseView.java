package com.racecarparts.view;

public class BaseView { // Used to provide the HTML generation utility for all the views.

   protected String generateHeader(String title) { // This method generates the Header for the page, title is the title displayed in the browser tab.
     StringBuilder html = new StringBuilder(); // This is a string builder that will be used to build the HTML.
     html.append("<!DOCTYPE html>"); // This is the HTML doctype.
     html.append("<html>"); // This is the HTML tag.
     html.append("<head>"); // This is the head tag.
    return html.toString(); // This returns the HTML as a string. This is only temporary, delete it later.  
  
}
  protected String generateFooter() { // This method generates the common footer across all webpages.
    return "</body>\n</html>\n"; // This is the HTML for the footer.
}
  protected String escapeHTML() { // This method generates the escapeHTML for the page.
    return ""; // We need to come back to this later.
}
  
}
