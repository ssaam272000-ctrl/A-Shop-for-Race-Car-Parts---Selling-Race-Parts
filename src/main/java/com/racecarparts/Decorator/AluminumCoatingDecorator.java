package com.racecarparts.decorator;

import com.racecarparts.shop.RaceCarPart;

public class AluminumCoatingDecorator extends PartDecorator { // This class adds Aluminum  Coating to the part.
   private static final double COATING_PRICE = 250.00; // This is the price of the Aluminum Coating.
   private static final String COATING_DESCRIPTION = " + Aluminum Coating"; // This is the description of the Aluminum Coating.
    public AluminumCoatingDecorator(RaceCarPart part) { // This constructor adds Aluminum Coating to the part.
    super(part);
  }
  @Override
   public double getPrice() { // This method overrides the price and returns the price of the part plus the price of the Aluminum Coating.
     return wrappedPart.getPrice() + COATING_PRICE; // This is the price of the part plus the price of the Aluminum Coating.
   }
   @Override
   public String getDescription() { // This method returns the description of the part plus the description of the Aluminum Coating.
     return wrappedPart.getDescription() + COATING_DESCRIPTION; // This is the description of the part plus the description of the Aluminum Coating.
   }
}
  