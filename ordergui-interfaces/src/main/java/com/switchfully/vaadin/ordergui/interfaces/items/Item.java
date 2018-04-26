package com.switchfully.vaadin.ordergui.interfaces.items;

public class Item {
    private String id;
    private String name;
    private String description;
    private float price;
    private int amountOfStock;
    private String stockUrgency;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
    }

    public void setStockUrgency(String stockUrgency) {
        this.stockUrgency = stockUrgency;
    }
}
