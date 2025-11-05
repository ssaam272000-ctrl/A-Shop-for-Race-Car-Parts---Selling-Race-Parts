package com.racecarparts.decorator;
import com.racecarparts.shop.RaceCarPart;


public abstract class PartDecorator implements RaceCarPart { // Decorator wraps RaceCarParts in the Decorator, and delegates most of the delegation to the wrapped RaceCarPart.
  protected RaceCarPart wrappedPart; // This is the parts being decorated.
  
public PartDecorator(RaceCarPart part) { // The constructor takes a RaceCarPart and stores it in the wrappedPart field.
    wrappedPart = part;
}
  @Override
  public double getPrice() { // Getters
    return wrappedPart.getPrice();
  }
  @Override
  public String getEngineName() { 
    return wrappedPart.getEngineName();
  }
  @Override
  public int getQuantity() {
    return wrappedPart.getQuantity();
  }
  @Override
  public String getImageURL() {
    return wrappedPart.getImageURL();
  }
  @Override
  public String getDescription() {
    return wrappedPart.getDescription();
  }
  
  public void setPrice(double newPrice) { // Setters
    wrappedPart.getPrice();
    }
  @Override
  public void setQuantity(int newQuantity) {
      wrappedPart.getQuantity();
    }
  
  public void setImageURL(String newImageURL) {
      wrappedPart.getImageURL();
    }
  
  public void setDescription(String newDescription) {
      wrappedPart.getDescription();
    }	
 
}
  