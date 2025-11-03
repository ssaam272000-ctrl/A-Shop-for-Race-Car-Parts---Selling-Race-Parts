package com.racecarparts.shop;

import com.racecarparts.shop.RaceCarPart;

public class EngineBlock implements RaceCarPart { // This is a subclass of the RaceCarPart interface.
	private double price;
	private String engineName;
	private int quantity;
	private String imageURL;
	private String description;
	
	public EngineBlock(double price, String engineName, int quantity, String imageURL, String description) {
	     this.price = price;
	     this.engineName = engineName;
	     this.quantity = quantity;
	     this.imageURL = imageURL;
	     this.description = description;
	 
	 }
	
	public double getPrice() { // Getters
		return price;
	}
	public String getEngineName() { 
		return engineName;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getImageURL() {
		return imageURL;
	}
	public String getDescription() {
		return description;
	}
	
	public void setPrice(double newPrice) { // Setters
		this.price = newPrice;
		}
		
	public void setQuantity(int newQuantity) {
		this.quantity = newQuantity;
		}
	
	public void setImageURL(String newImageURL) {
		this.imageURL = newImageURL;
		}
	public void setDescription(String newDescription) {
		this.description = newDescription;
		}	
		
}
