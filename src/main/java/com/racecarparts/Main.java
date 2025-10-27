package com.racecarparts;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
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
        tomcat.getConnector();
        
        String webappDir = new File("src/main/webapp").getAbsolutePath();
        Context context = tomcat.addWebapp("", webappDir);
        context.setAllowCasualMultipartParsing(true);
        
        System.out.println("Starting Tomcat server on port " + port);
        System.out.println("Web application directory: " + webappDir);
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
