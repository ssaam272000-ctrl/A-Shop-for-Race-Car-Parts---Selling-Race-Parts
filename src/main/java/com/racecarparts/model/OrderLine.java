package com.racecarparts.model;

import java.io.Serializable;

public class OrderLine implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private EngineBlock engineBlock;
    private int quantity;
    
    public OrderLine() {
    }
    
    public OrderLine(EngineBlock engineBlock, int quantity) {
        this.engineBlock = engineBlock;
        this.quantity = quantity;
    }
    
    public EngineBlock getEngineBlock() {
        return engineBlock;
    }
    
    public void setEngineBlock(EngineBlock engineBlock) {
        this.engineBlock = engineBlock;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return engineBlock.getPrice() * quantity;
    }
    
    @Override
    public String toString() {
        return "OrderLine{" +
                "engineBlock=" + engineBlock +
                ", quantity=" + quantity +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
