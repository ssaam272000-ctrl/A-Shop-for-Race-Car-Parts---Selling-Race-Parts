package com.racecarparts.model;

import java.io.Serializable;

public class EngineBlock implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String partId;
    private String name;
    private double price;
    private String imageUrl;
    
    public EngineBlock() {
    }
    
    public EngineBlock(String partId, String name, double price, String imageUrl) {
        this.partId = partId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    
    public String getPartId() {
        return partId;
    }
    
    public void setPartId(String partId) {
        this.partId = partId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Override
    public String toString() {
        return "EngineBlock{" +
                "partId='" + partId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
