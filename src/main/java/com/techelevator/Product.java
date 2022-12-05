package com.techelevator;

public abstract class Product {

    private String name;
    private double cost;
    private String menuID;
    private int quantity;
    private String type;

    public Product (String menuID, String name, String cost, String type, String quantity) {
        this.menuID = menuID;
        this.name = name;
        this.cost = Double.parseDouble(cost);
        this.type = type;
        this.quantity = Integer.parseInt(quantity);
    }

    public String getName() {return name;}

    public double getCost() {return cost;}

    public String getMenuID() {return menuID;}

    public String getType() {return type;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

}
