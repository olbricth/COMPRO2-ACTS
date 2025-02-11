package main;

import java.util.ArrayList;

public class Coffee {

    //declare the properties
    public String name;
    public String type;
    public String size;
    public double price;
    public String roastLevel;
    public String origin;
    public boolean isDecaf;
    public int stock;
    public ArrayList<String> flavorNotes;
    public String brewMethod;

    //construct the declared properties
    public Coffee(String name, String type, String size, double price,
                  String roastLevel, String origin, boolean isDecaf,
                  int stock, ArrayList<String> flavorNotes, String brewMethod) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
    }

    //use methods to calculate
    public double calculatePrice(String size) {
        if (size.equalsIgnoreCase("Small")) {
            return price;
        } else if (size.equalsIgnoreCase("Medium")) {
            return price + 1.0;
        } else if (size.equalsIgnoreCase("Large")) {
            return price + 2.0;
        } else {
            return price; // Default
        }
    }
    public boolean checkStock() {
        return stock > 0;
    }
    public void addFlavor(String note) {
        flavorNotes.add(note);
    }
    public void updateStock(int quantity) {
        stock += quantity;
    }
    public String describe() {
        return name + " is a " + roastLevel + " roast coffee with flavor notes of " +
                flavorNotes + ". Origin: " + origin + ". Brewing method: " + brewMethod + ".";
    }
    public void setDecaf(boolean isDecaf) {
        this.isDecaf = isDecaf;
    }
    public void changeRoastLevel(String newRoastLevel) {
        this.roastLevel = newRoastLevel;
    }
    public void discount(double percentage) {
        price -= price * (percentage / 100);
    }
}
