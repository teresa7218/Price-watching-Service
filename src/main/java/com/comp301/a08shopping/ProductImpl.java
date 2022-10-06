package com.comp301.a08shopping;

public class ProductImpl implements Product {
  private String n;
  private double bp, disc;
  private int i;

  public ProductImpl(String name, double basePrice) {
    if (name == null || basePrice < 0) {
      throw new IllegalArgumentException();
    } else {
      n = name;
      bp = basePrice;
      disc = 1.0;
      i = 1;
    }
  }

  public ProductImpl(String name, double basePrice, int inventory) {
    if (name == null || basePrice < 0 || inventory < 0) {
      throw new IllegalArgumentException();
    } else {
      n = name;
      bp = basePrice;
      disc = 1.0;
      i = inventory;
    }
  }

  @Override
  public String getName() {
    return n;
  }

  @Override
  public double getSale() {
    return disc;
  }

  @Override
  public double getBasePrice() {
    return bp * disc;
  }

  @Override
  public int getInventory() {
    return i;
  }

  @Override
  public void setInventory(int i) {
    this.i = i;
  }

  @Override
  public void setSale(double disc) {
    this.disc = disc;
  }
}
