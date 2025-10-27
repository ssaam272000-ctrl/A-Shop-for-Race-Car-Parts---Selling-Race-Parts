package com.racecarparts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<OrderLine> orderLines;
    private static final double SHIPPING_COST = 6.50;
    
    public ShoppingCart() {
        this.orderLines = new ArrayList<>();
    }
    
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
    
    public void addItem(EngineBlock engineBlock, int quantity) {
        for (OrderLine line : orderLines) {
            if (line.getEngineBlock().getPartId().equals(engineBlock.getPartId())) {
                line.setQuantity(line.getQuantity() + quantity);
                return;
            }
        }
        orderLines.add(new OrderLine(engineBlock, quantity));
    }
    
    public void updateQuantity(String partId, int quantity) {
        if (quantity <= 0) {
            removeItem(partId);
            return;
        }
        
        for (OrderLine line : orderLines) {
            if (line.getEngineBlock().getPartId().equals(partId)) {
                line.setQuantity(quantity);
                return;
            }
        }
    }
    
    public void removeItem(String partId) {
        orderLines.removeIf(line -> line.getEngineBlock().getPartId().equals(partId));
    }
    
    public void clear() {
        orderLines.clear();
    }
    
    public double getSubtotal() {
        return orderLines.stream()
                .mapToDouble(OrderLine::getSubtotal)
                .sum();
    }
    
    public double getShippingCost() {
        return orderLines.isEmpty() ? 0.0 : SHIPPING_COST;
    }
    
    public double getTotal() {
        return getSubtotal() + getShippingCost();
    }
    
    public int getTotalItems() {
        return orderLines.stream()
                .mapToInt(OrderLine::getQuantity)
                .sum();
    }
    
    public boolean isEmpty() {
        return orderLines.isEmpty();
    }
}
