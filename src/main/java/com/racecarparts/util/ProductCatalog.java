package com.racecarparts.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.racecarparts.shop.EngineBlock;

public class ProductCatalog {

	private static final Map<String, EngineBlock> products = new HashMap<>(); // Hash-map to map products
	
	static {
		products.put("Part 101", new EngineBlock(2289.93, "Part 101", 1, "engine1.jpg", "SB2 Engine Block"));
		products.put("Part 102", new EngineBlock(2689.93, "Part 102", 1, "engine2.jpg", "350 Engine Block Broncos"));
		products.put("Part 201", new EngineBlock(3186.64, "Part 201", 1, "engine3.jpg", "383 Engine Block Broncos"));
		products.put("Part 301", new EngineBlock(3999.99, "Part 301", 1, "engine4.jpg", "427 Engine Block"));
		products.put("Part 401", new EngineBlock(4500.00, "Part 401", 1, "engine5.jpg", "454 Engine Block"));
		products.put("Part 501", new EngineBlock(5200.00, "Part 501", 1, "engine6.jpg", "502 Engine Block"));
	}
	public static EngineBlock getPartbyID(String partID) { // This takes a string of what the part name is and defines an EngineBlock with that name within our inventory with that name.  If name doesn't exist, it returns a Null object.
		return products.get(partID);
	}
	public static List<EngineBlock> getAllProducts() { // This will return all Engine Blocks we defined above as an Array list.
		return new ArrayList<>(products.values());
	}
}
