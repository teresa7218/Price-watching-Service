package com.comp301.a08shopping;

import com.comp301.a08shopping.events.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements Customer {
  private String n;
  private double b;
  private List<ReceiptItem> items;

  public CustomerImpl(String name, double budget) {
    if (name == null || budget < 0) {
      throw new IllegalArgumentException();
    } else {
      n = name;
      b = budget;
      items = new ArrayList<>();
    }
  }

  @Override
  public String getName() {
    return n;
  }

  @Override
  public double getBudget() {
    return b;
  }

  @Override
  public void purchaseProduct(Product product, Store store) {
    if (product == null || store == null) {
      throw new IllegalArgumentException();
    } else if (product.getBasePrice() > b) {
      throw new IllegalStateException();
    } else {
      b -= store.getSalePrice(product);
      ReceiptItem receipt = store.purchaseProduct(product);
      items.add(receipt);
    }
  }

  @Override
  public List<ReceiptItem> getPurchaseHistory() {
    return items;
  }

  @Override
  public void update(StoreEvent event) {
    String pn = event.getProduct().getName();
    String sn = event.getStore().getName();
    if (event instanceof BackInStockEvent) {
      System.out.println(pn + " is back in stock at " + sn);
    } else if (event instanceof OutOfStockEvent) {
      System.out.println(pn + " is now out of stock at " + sn);
    } else if (event instanceof PurchaseEvent) {
      System.out.println("Someone purchased " + pn + " at " + sn);
    } else if (event instanceof SaleEndEvent) {
      System.out.println("The sale for " + pn + " at " + sn + " has ended");
    } else if (event instanceof SaleStartEvent) {
      System.out.println("New sale for " + pn + " at " + sn + "!");
    }
  }
}
