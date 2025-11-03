package com.racecarparts.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.racecarparts.shop.EngineBlock;

public class ProductCatalog {

	private static final ProductCatalog INSTANCE = new ProductCatalog(); // Singleton method to creating single instances of a map of products.
	private static Map<String, EngineBlock> products ; // Create it once and use it by defining a get instance method.
	private ProductCatalog() { // This is a constructor that disable the access from outside the class, and will ensure the 		products will only be created once.
	products = new HashMap<>();
	
		products.put("Part 101", new EngineBlock(2000.00, "Part 101", 1, "engine1.jpg", "305 Engine Block Steel"));
		products.put("Part 102", new EngineBlock(2500.00, "Part 102", 1, "engine2.jpg", "305 Engine Block Aluminum"));
		products.put("Part 103", new EngineBlock(3000.00, "Part 103", 1, "engine3.jpg", "350 Engine Block Steel"));
		products.put("Part 104", new EngineBlock(3500.00, "Part 104", 1, "engine4.jpg", "350 Engine Block Aluminum"));
		products.put("Part 105", new EngineBlock(4000.00, "Part 105", 1, "engine5.jpg", "410 Engine Block Steel"));
		products.put("Part 106", new EngineBlock(4500.00, "Part 106", 1, "engine6.jpg", "410 Engine Block Aluminum"));

	}
	public static ProductCatalog getInstance() { // This is a method that will return the instance of the product catalog.
		return INSTANCE; 
	}
	public static EngineBlock getPartbyID(String partID) { // This takes a string of what the part name is and defines an EngineBlock with that name within our inventory with that name.  If name doesn't exist, it returns a Null object.
		return products.get(partID);
	}
	public static List<EngineBlock> getAllProducts() { // This will return all Engine Blocks we defined above as an Array list.
		return new ArrayList<>(products.values());
	}
}
