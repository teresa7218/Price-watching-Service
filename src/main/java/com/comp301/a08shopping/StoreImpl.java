package com.comp301.a08shopping;

import com.comp301.a08shopping.events.*;
import com.comp301.a08shopping.exceptions.OutOfStockException;
import com.comp301.a08shopping.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StoreImpl implements Store {
  private final String name;
  private final List<StoreObserver> sbs;
  private final List<Product> ps;

  public StoreImpl(String name) {
    if (name == null) {
      throw new IllegalArgumentException();
    } else {
      this.name = name;
      sbs = new ArrayList<>();
      ps = new ArrayList<>();
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void addObserver(StoreObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    } else {
      sbs.add(observer);
    }
  }

  @Override
  public void removeObserver(StoreObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    } else {
      sbs.remove(observer);
    }
  }

  @Override
  public List<Product> getProducts() {
    List<Product> clone = new ArrayList<>();
    for (Product p : ps) {
      clone.add(p);
    }
    return clone;
  }

  @Override
  public Product createProduct(String name, double basePrice, int inventory) {
    if (name == null || basePrice <= 0 || inventory < 0) {
      throw new IllegalArgumentException();
    } else {
      Product p = new ProductImpl(name, basePrice, inventory);
      ps.add(p);
      return p;
    }
  }

  @Override
  public ReceiptItem purchaseProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else if (product.getInventory() == 0) {
      throw new OutOfStockException();
    } else {
      notifyObserver(new PurchaseEvent(product, this));
      product.setInventory(product.getInventory() - 1);
      if (product.getInventory() == 0) {
        notifyObserver(new OutOfStockEvent(product, this));
      }
      return new ReceiptItemImpl(product.getName(), product.getBasePrice(), name);
    }
  }

  @Override
  public void restockProduct(Product product, int numItems) {
    if (product == null || numItems < 0) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      if (product.getInventory() == 0) {
        notifyObserver(new BackInStockEvent(product, this));
      }
      product.setInventory(product.getInventory() + numItems);
    }
  }

  @Override
  public void startSale(Product product, double percentOff) {
    if (product == null || percentOff < 0 || percentOff > 1) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      product.setSale(1 - percentOff);
      notifyObserver(new SaleStartEvent(product, this));
    }
  }

  @Override
  public void endSale(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      product.setSale(1);
      notifyObserver(new SaleEndEvent(product, this));
    }
  }

  @Override
  public int getProductInventory(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      return product.getInventory();
    }
  }

  @Override
  public boolean getIsInStock(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      return product.getInventory() > 0;
    }
  }

  @Override
  public double getSalePrice(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      return product.getBasePrice();
    }
  }

  @Override
  public boolean getIsOnSale(Product product) {
    if (product == null) {
      throw new IllegalArgumentException();
    } else if (!ps.contains(product)) {
      throw new ProductNotFoundException();
    } else {
      return product.getSale() < 1.00;
    }
  }

  @Override
  public void notifyObserver(StoreEvent e) {
    if (e == null) {
      throw new IllegalArgumentException();
    } else {
      for (StoreObserver sb : sbs) {
        sb.update(e);
      }
    }
  }
}
