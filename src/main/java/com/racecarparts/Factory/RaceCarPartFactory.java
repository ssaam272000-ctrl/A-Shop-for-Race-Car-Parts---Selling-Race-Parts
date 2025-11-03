package com.racecarparts.Factory;
import com.racecarparts.shop.RaceCarPart;
import com.racecarparts.util.ProductCatalog;
import com.racecarparts.shop.EngineBlock;

public class RaceCarPartFactory {
  public static RaceCarPart createPart(String partId) { // Factory method to create parts based on partId
  
  ProductCatalog catalog = ProductCatalog.getInstance();
  RaceCarPart catalogPart = catalog.getPartbyID(partId);
    
  if (catalogPart == null) { // If the part doesn't exist in the catalog, return null
    return null;
  }
    return createCopyOf(catalogPart); // Return the copy of catalogPart
 }
  private static RaceCarPart createCopyOf(RaceCarPart part) { //This is going to create a copy of the part that we just found from the catalog so that we don't accidentally change the parts.
    if (part instanceof EngineBlock) {
      return new EngineBlock(part.getPrice(), part.getEngineName(),       part.getQuantity(), part.getImageURL(), part.getDescription());
    }
      throw new IllegalArgumentException("Unknown part type: " + part.getClass().getName()); // It prevents the user from creating copies of subclasses that are not defined in the factory.
  }
    
}
     