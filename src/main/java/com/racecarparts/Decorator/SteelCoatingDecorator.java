package com.racecarparts.decorator;
import com.racecarparts.shop.RaceCarPart;

public class SteelCoatingDecorator extends PartDecorator { // This class adds Steel Coating to the part.
  private static final double COATING_PRICE = 350.00;
  private static final String COATING_DESCRIPTION = " + Steel Coating";

  public SteelCoatingDecorator(RaceCarPart part) { // This constructor adds Steel Coating to the part.
    super(part);
    
  }
  @Override
  public double getPrice() { // This method returns the price of the part plus the price of the Steel Coating.
    return wrappedPart.getPrice() + COATING_PRICE; // This is the price of the part plus the price of the Steel Coating.
  }
  @Override
  public String getDescription() { // This method returns the description of the part plus the description of the Steel Coating.
    return wrappedPart.getDescription() + COATING_DESCRIPTION; // This is the description of the part plus the description of the Steel Coating.
  }
}