package model;

public enum Product {
    COLA("Cola", 25),
    DIET_COLA("Diet Cola", 35),
    LIME_SODA("Lime Soda", 25),
    WATER("Water", 45);

    private String name;
    private int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
