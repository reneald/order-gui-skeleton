package com.switchfully.vaadin.ordergui.interfaces.items;

public class Item {
    public String id;
    public String name;
    public String description;
    public float price;
    public int amountOfStock;
    public String stockUrgency;

    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Item setPrice(float price) {
        this.price = price;
        return this;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public Item setAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
        return this;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }

    public Item setStockUrgency(String stockUrgency) {
        this.stockUrgency = stockUrgency;
        return this;
    }
}
