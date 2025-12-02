package com.inverse.util;

public class EmailService {
    /**
     * Stub: for demo we just log. Replace with JavaMail / Jakarta Mail when you want real email.
     *
     * @param recipient customer name or email (if you collect email)
     * @param orderId   order id
     */
    public static void sendOrderConfirmation(String recipient, int orderId) {
        System.out.println("EmailService: sending confirmation to " + recipient + " for order " + orderId);
        // TODO: integrate real email sending via JavaMail if desired.
    }
}
