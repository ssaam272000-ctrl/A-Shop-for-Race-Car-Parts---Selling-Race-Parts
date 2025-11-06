package com.racecarparts.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.racecarparts.view.DemoView;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 15
)
public class DemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String uploadMessage = (String) session.getAttribute("uploadMessage");
        session.removeAttribute("uploadMessage");
        
        DemoView view = new DemoView();
        String html = view.render(username, uploadMessage);
        
        PrintWriter out = response.getWriter();
        out.println(html);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username != null && !username.trim().isEmpty() && 
                password != null && !password.trim().isEmpty()) {
                session.setAttribute("username", username);
                session.setAttribute("uploadMessage", "Login successful! Welcome, " + username);
            } else {
                session.setAttribute("uploadMessage", "Login failed. Please provide both username and password.");
            }
        } 
        else if ("logout".equals(action)) {
            session.removeAttribute("username");
            session.setAttribute("uploadMessage", "You have been logged out.");
        }
        else if ("upload".equals(action)) {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                session.setAttribute("uploadMessage", "Please login first to upload files.");
            } else {
                Part filePart = request.getPart("file");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    
                    Path uploadDir = Paths.get("uploads");
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    
                    Path filePath = uploadDir.resolve(fileName);
                    try (InputStream fileContent = filePart.getInputStream()) {
                        Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    
                    session.setAttribute("uploadMessage", 
                        "File '" + fileName + "' uploaded successfully by " + username + "!");
                } else {
                    session.setAttribute("uploadMessage", "No file selected.");
                }
            }
        }
        
        response.sendRedirect("demo");
    }
}
