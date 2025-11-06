package com.racecarparts.view;

public class DemoView extends BaseView {
    
    public String render(String username, String message) {
        StringBuilder html = new StringBuilder();
        
        html.append(generateHeader("Authentication & File Upload Demo"));
        
        html.append("<div class='header'>\n");
        html.append("    <h1>Demo: Authentication & File Upload</h1>\n");
        html.append("    <a href='/' class='cart-button'>Back to Shop</a>\n");
        html.append("</div>\n");
        
        if (message != null && !message.isEmpty()) {
            html.append("<div style='background-color: #4CAF50; color: white; padding: 15px; border-radius: 5px; margin-bottom: 20px;'>\n");
            html.append("    ").append(escapeHtml(message)).append("\n");
            html.append("</div>\n");
        }
        
        html.append("<div style='display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 20px;'>\n");
        
        html.append(generateAuthSection(username));
        
        html.append(generateUploadSection(username));
        
        html.append("</div>\n");
        
        html.append(generateInfoSection());
        
        html.append(generateFooter());
        
        return html.toString();
    }
    
    private String generateAuthSection(String username) {
        StringBuilder html = new StringBuilder();
        
        html.append("<div style='background-color: white; padding: 30px; border-radius: 10px;'>\n");
        html.append("    <h2 style='color: #1e90ff; margin-top: 0;'>Authentication</h2>\n");
        
        if (username == null) {
            html.append("    <p>Please log in to access file upload features.</p>\n");
            html.append("    <form action='/demo' method='post'>\n");
            html.append("        <input type='hidden' name='action' value='login'>\n");
            html.append("        <div style='margin-bottom: 15px;'>\n");
            html.append("            <label style='display: block; margin-bottom: 5px; font-weight: bold;'>Username:</label>\n");
            html.append("            <input type='text' name='username' required style='width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px;'>\n");
            html.append("        </div>\n");
            html.append("        <div style='margin-bottom: 15px;'>\n");
            html.append("            <label style='display: block; margin-bottom: 5px; font-weight: bold;'>Password:</label>\n");
            html.append("            <input type='password' name='password' required style='width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px;'>\n");
            html.append("        </div>\n");
            html.append("        <button type='submit' style='background-color: #4CAF50; color: white; padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: bold;'>Login</button>\n");
            html.append("    </form>\n");
        } else {
            html.append("    <p style='font-size: 18px;'>Welcome, <strong>").append(escapeHtml(username)).append("</strong>!</p>\n");
            html.append("    <p>You are currently logged in.</p>\n");
            html.append("    <form action='/demo' method='post' style='margin-top: 20px;'>\n");
            html.append("        <input type='hidden' name='action' value='logout'>\n");
            html.append("        <button type='submit' style='background-color: #ff6b6b; color: white; padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: bold;'>Logout</button>\n");
            html.append("    </form>\n");
        }
        
        html.append("</div>\n");
        
        return html.toString();
    }
    
    private String generateUploadSection(String username) {
        StringBuilder html = new StringBuilder();
        
        html.append("<div style='background-color: white; padding: 30px; border-radius: 10px;'>\n");
        html.append("    <h2 style='color: #1e90ff; margin-top: 0;'>File Upload</h2>\n");
        
        if (username == null) {
            html.append("    <p style='color: #666;'>You must be logged in to upload files.</p>\n");
            html.append("    <div style='background-color: #f5f5f5; padding: 20px; border-radius: 5px; text-align: center; color: #999;'>\n");
            html.append("        <p>🔒 Upload feature locked</p>\n");
            html.append("    </div>\n");
        } else {
            html.append("    <p>Upload a file to the server.</p>\n");
            html.append("    <form action='/demo' method='post' enctype='multipart/form-data'>\n");
            html.append("        <input type='hidden' name='action' value='upload'>\n");
            html.append("        <div style='margin-bottom: 15px;'>\n");
            html.append("            <label style='display: block; margin-bottom: 5px; font-weight: bold;'>Choose file:</label>\n");
            html.append("            <input type='file' name='file' required style='width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px;'>\n");
            html.append("        </div>\n");
            html.append("        <button type='submit' style='background-color: #00bfff; color: white; padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: bold;'>Upload File</button>\n");
            html.append("    </form>\n");
        }
        
        html.append("</div>\n");
        
        return html.toString();
    }
    
    private String generateInfoSection() {
        StringBuilder html = new StringBuilder();
        
        html.append("<div style='background-color: white; padding: 30px; border-radius: 10px;'>\n");
        html.append("    <h2 style='color: #1e90ff; margin-top: 0;'>How It Works</h2>\n");
        html.append("    <div style='display: grid; grid-template-columns: 1fr 1fr; gap: 20px;'>\n");
        html.append("        <div>\n");
        html.append("            <h3 style='color: #4CAF50;'>Authentication</h3>\n");
        html.append("            <ul style='line-height: 1.8;'>\n");
        html.append("                <li>Enter any username and password to log in</li>\n");
        html.append("                <li>Session-based authentication using HttpSession</li>\n");
        html.append("                <li>Must be logged in to upload files</li>\n");
        html.append("                <li>Click logout to end your session</li>\n");
        html.append("            </ul>\n");
        html.append("        </div>\n");
        html.append("        <div>\n");
        html.append("            <h3 style='color: #00bfff;'>File Upload</h3>\n");
        html.append("            <ul style='line-height: 1.8;'>\n");
        html.append("                <li>Supports files up to 10MB</li>\n");
        html.append("                <li>Files are saved to the 'uploads' directory</li>\n");
        html.append("                <li>Uses Servlet 3.0 @MultipartConfig</li>\n");
        html.append("                <li>Requires authentication to access</li>\n");
        html.append("            </ul>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</div>\n");
        
        return html.toString();
    }
}
