package com.racecarparts.shop;

public class OrderLine {
	
	private int quantity;
	private RaceCarPart block;
	private double amount;
	
	public OrderLine(RaceCarPart block, int quantity) { // Constructor
		this.block = block;
		this.quantity = quantity;
		this.amount = block.getPrice(); // Set amount from the block's price
	}
	
	
	public int getQuantity() { // Getters
		return quantity;
		}
	public RaceCarPart getEngineBlock() {
		return block;
		}
	public double getAmount() {
		return amount;
		}
	public double getOrderTotal() { // The individual total for each OrderLine in Figma.
		return quantity * amount;
		}
	public void setQuantity(int newQuantity) { // Setters
		this.quantity = newQuantity;
		}
	
	public void setEngineBlock(RaceCarPart newBlock) {
		this.block = newBlock;
		}
		
	public void setAmount(double newAmount) {
		this.amount = newAmount;
		}
		
}
