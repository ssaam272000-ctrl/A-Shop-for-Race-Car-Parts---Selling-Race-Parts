package com.racecarparts.decorator;

import com.racecarparts.shop.RaceCarPart;

public class PerformanceTuningDecorator extends PartDecorator { // This class adds Optimize Power and Performance Tuning to the part.
   private static final double TUNING_PRICE = 500.00; // This is the price of the Optimize Power and Performance Tuning.
   private static final String TUNING_DESCRIPTION = " + Optimize Power and Performance Tuning"; // This is the description of the Optimize Power and Performance Tuning.
   
  public PerformanceTuningDecorator(RaceCarPart part) { // This constructor adds Optimize Power and Performance Tuning to the part.
    super(part);
  } 
  @Override
  public double getPrice() { // This method overrides the price and returns the price of the part plus the price of the Optimize Power and Performance Tuning.  
    return wrappedPart.getPrice() + TUNING_PRICE; // This is the price of the part plus the price of the Optimize Power and Performance Tuning.
  }
  @Override
  public String getDescription() { // This method overrides the description and returns the description of the part plus the description of the Optimize Power and Performance Tuning.
    return wrappedPart.getDescription() + TUNING_DESCRIPTION;// This is the description of the part plus the description of the Optimize Power and Performance Tuning.
  }
  
}