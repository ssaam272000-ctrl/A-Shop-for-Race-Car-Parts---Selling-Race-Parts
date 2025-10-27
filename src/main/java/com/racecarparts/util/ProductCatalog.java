package com.racecarparts.util;

import com.racecarparts.model.EngineBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCatalog {
    private static final Map<String, EngineBlock> products = new HashMap<>();
    
    static {
        products.put("101", new EngineBlock("101", "SB2 Engine Block", 2289.93, "engine1.jpg"));
        products.put("203", new EngineBlock("203", "383 Engine Block Bare", 3183.93, "engine2.jpg"));
        products.put("304", new EngineBlock("304", "400 Dap'rs Block Bare", 3360.00, "engine3.jpg"));
        products.put("102", new EngineBlock("102", "350 Engine Block Broncos", 2689.93, "engine4.jpg"));
        products.put("201", new EngineBlock("201", "383 Engine Block Broncos", 3186.64, "engine5.jpg"));
        products.put("306", new EngineBlock("306", "400 Engine Block Broncos", 3439.93, "engine6.jpg"));
    }
    
    public static EngineBlock getProductById(String partId) {
        return products.get(partId);
    }
    
    public static List<EngineBlock> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
