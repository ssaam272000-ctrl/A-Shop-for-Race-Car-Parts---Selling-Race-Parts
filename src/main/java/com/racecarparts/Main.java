package com.racecarparts;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    
    public static void main(String[] args) throws LifecycleException {
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "5000";
        }
        
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.setHostname("0.0.0.0");
        tomcat.setBaseDir(".");
        tomcat.getConnector();
        
        String webappDir = new File("src/main/webapp").getAbsolutePath();
        
        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        
        // Register servlets
        Tomcat.addServlet(context, "IndexServlet", new com.racecarparts.servlet.IndexServlet());
        context.addServletMappingDecoded("", "IndexServlet");
        context.addServletMappingDecoded("/", "IndexServlet");
        context.addServletMappingDecoded("/index", "IndexServlet");
        
        Tomcat.addServlet(context, "CartServlet", new com.racecarparts.servlet.CartServlet());
        context.addServletMappingDecoded("/cart", "CartServlet");
        
        Tomcat.addServlet(context, "AddToCartServlet", new com.racecarparts.servlet.AddToCartServlet());
        context.addServletMappingDecoded("/addToCart", "AddToCartServlet");
        
        Tomcat.addServlet(context, "UpdateCartServlet", new com.racecarparts.servlet.UpdateCartServlet());
        context.addServletMappingDecoded("/updateCart", "UpdateCartServlet");
        
        Tomcat.addServlet(context, "CheckoutServlet", new com.racecarparts.servlet.CheckoutServlet());
        context.addServletMappingDecoded("/checkout", "CheckoutServlet");
        
        File staticDir = new File(webappDir);
        context.setDocBase(staticDir.getAbsolutePath());
        
        System.out.println("===========================================");
        System.out.println("Starting Tomcat server on port " + port);
        System.out.println("Web application directory: " + webappDir);
        System.out.println("Server accessible at http://0.0.0.0:" + port);
        System.out.println("===========================================");
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
