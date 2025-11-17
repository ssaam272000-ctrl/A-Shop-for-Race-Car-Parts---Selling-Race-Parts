package com.racecarparts.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCart - Manages customer's shopping cart
 * 
 * Updated to use RaceCarPart interface instead of concrete EngineBlock class.
 * This allows the cart to handle any type of race car part, including decorated parts
 * with upgrades and customizations.
 */
public class ShoppingCart {
				private List<OrderLine> orderLine;
				private double shippingRate = 0.05;
				private double taxRate = 0.10;

				public ShoppingCart() {
								orderLine = new ArrayList<OrderLine>();
				}

				public double getSubTotal() {
								double sum = 0;
								for (OrderLine currentOrder : orderLine) {
												sum += currentOrder.getOrderTotal();
								}
								return sum;
				}

				public double getTotal() {
								double SubTotal = this.getSubTotal();
								return SubTotal + (SubTotal * shippingRate) + (SubTotal * taxRate);
				}

				public double getTax() {
								double SubTotal = this.getSubTotal();
								return SubTotal * taxRate;
				}

				public double getCarrier() {
								double SubTotal = this.getSubTotal();
								return SubTotal * shippingRate;
				}
				public void addItem(RaceCarPart part, int quantity) { // Adding item - now accepts any RaceCarPart (including decorated parts)
								for (OrderLine o : orderLine) { // Check each part in order line, check if we already have our item in our orderLine.
												// Compare both name AND description to distinguish between decorated and non-decorated parts
												// This ensures that "Part 101" and "Part 101 + Ceramic Coating" are treated as different items
												if (o.getEngineBlock().getEngineName().equals(part.getEngineName()) &&
														o.getEngineBlock().getDescription().equals(part.getDescription())) {
																int newQuantity = o.getQuantity() + quantity;  // variable
																o.setQuantity(newQuantity);
																// Update the amount to match the current part's price (in case decorators differ)
																o.setAmount(part.getPrice());
																return; // Exit after updating
												}

								}
								orderLine.add(new OrderLine(part, quantity)); // This adds a new OrderLine item to your shopping cart.
				}
				public void removeItem(String partName) {
								orderLine.removeIf(line -> line.getEngineBlock().getEngineName().equals(partName)); // Loops through the Array List of order line and matches the name of each order line with the part name that you are trying to remove. If matches it removes it from OrderLine Array List.
				}
				public void clear() { // remove Everything from the Shopping Cart
								orderLine.clear();
				}
				public boolean isEmpty() {
								return orderLine.isEmpty();
				}
				public int getTotalItems() { // This will give you the total number of items in the shopping cart
								return orderLine.size();
				}
				public List<OrderLine> getOrderLines() { // Get all order lines for displaying in cart
								return new ArrayList<>(orderLine); // Return a copy so clearing cart doesn't affect returned list
				}
				public void updateQuantity(String partID, int quantity) { // Update the Quantity of the RaceCarPart with PartID to provide the quantity.
								if (quantity <= 0) {
												removeItem(partID);
												return;
								}
								for (OrderLine line : orderLine) { // Loop through the OrderLine
												if (line.getEngineBlock().getEngineName().equals(partID)) { // This checking if each line in OrderLine has a part name that matches the part name that the user wants to update.
																line.setQuantity(quantity); // Sets the quantity
																return; // ends this method
												}
								}
				}

}
