package com.comp301.a08shopping.events;

import com.comp301.a08shopping.Product;
import com.comp301.a08shopping.Store;

public class OutOfStockEvent implements StoreEvent {
  private final Product product;
  private final Store store;

  public OutOfStockEvent(Product product, Store store) {
    if (product == null || store == null) {
      throw new IllegalArgumentException();
    } else {
      this.product = product;
      this.store = store;
    }
  }

  @Override
  public Product getProduct() {
    return product;
  }

  @Override
  public Store getStore() {
    return store;
  }
}
