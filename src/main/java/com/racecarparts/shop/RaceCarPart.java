package com.racecarparts.shop;

public interface RaceCarPart { // This interface will define the methods that all subclasses of RaceCarPart will have to implement.
  double getPrice(); // Getters
  String getEngineName();
  int getQuantity();
  String getImageURL();
  String getDescription();
  
  void setQuantity(int newQuantity); // Setters
  
  





  
}
  