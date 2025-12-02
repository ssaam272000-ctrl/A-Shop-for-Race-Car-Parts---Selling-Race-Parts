package com.inverse.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple centralized logger utility.
 * All servlets/DAOs should log through this class.
 */
public class LogUtil {

    private static final Logger LOGGER = Logger.getLogger("InverseOrderingSystem");

    public static void info(String msg) {
        LOGGER.log(Level.INFO, msg);
    }

    public static void warn(String msg) {
        LOGGER.log(Level.WARNING, msg);
    }

    public static void error(String msg, Throwable t) {
        LOGGER.log(Level.SEVERE, msg, t);
    }
}
