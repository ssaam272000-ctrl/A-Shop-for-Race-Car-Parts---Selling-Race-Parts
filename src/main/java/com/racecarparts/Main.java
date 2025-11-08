package com.racecarparts;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import com.racecarparts.util.DatabaseInitializer;

public class Main {

        public static void main(String[] args) throws LifecycleException {
                DatabaseInitializer.initializeDatabase();
                
                String port = "5000";
                Tomcat tomcat = new Tomcat();
                tomcat.setPort(Integer.parseInt(port));
                tomcat.setHostname("0.0.0.0");
                tomcat.setBaseDir(".");
                tomcat.getConnector();
                String webappDir = new File("src/main/webapp").getAbsolutePath(); // This is trying to give you HTML from the file within this directory path.
                Context context = tomcat.addContext("", new File(".").getAbsolutePath()); // This is to creating an empty blueprint where later it will be used to map the Sub-directories to the Java file.
                
                // Register all servlets
                Tomcat.addServlet(context, "IndexServlet", new com.racecarparts.servlet.IndexServlet()); // This is saying the home-page of web-site is defined in the IndexServlet file.
                context.addServletMappingDecoded("", "IndexServlet"); // This is explicitly redirecting the user to IndexServlet if they don't type anything after the main home-page.
                context.addServletMappingDecoded("/", "IndexServlet"); // This is explicitly redirecting the user to IndexServlet if they don't type anything after the main home-page.
                context.addServletMappingDecoded("/index", "IndexServlet");
                
                Tomcat.addServlet(context, "CartServlet", new com.racecarparts.servlet.CartServlet()); // Mapping CartServlet to handle shopping cart display
                context.addServletMappingDecoded("/cart", "CartServlet");
                
                Tomcat.addServlet(context, "AddToCartServlet", new com.racecarparts.servlet.AddToCartServlet()); // Mapping AddToCartServlet to handle adding items to cart
                context.addServletMappingDecoded("/addToCart", "AddToCartServlet");
                
                Tomcat.addServlet(context, "UpdateCartServlet", new com.racecarparts.servlet.UpdateCartServlet()); // Mapping UpdateCartServlet to handle cart updates, removals, and clear operations
                context.addServletMappingDecoded("/updateCart", "UpdateCartServlet");
                
                Tomcat.addServlet(context, "CheckoutServlet", new com.racecarparts.servlet.CheckoutServlet()); // Mapping CheckoutServlet to handle order checkout
                context.addServletMappingDecoded("/checkout", "CheckoutServlet");
                
                // Add DefaultServlet to serve static resources (images, CSS, etc.)
                Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
                context.addServletMappingDecoded("/images/*", "default");
                context.addServletMappingDecoded("/css/*", "default");
                
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
