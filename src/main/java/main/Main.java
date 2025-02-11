package main;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //create arraylist for the coffee flavors
        ArrayList<String> flavorNotes1 = new ArrayList<>();
        flavorNotes1.add("Chocolate");
        flavorNotes1.add("Nutty");

        ArrayList<String> flavorNotes2 = new ArrayList<>();
        flavorNotes2.add("Citrus");
        flavorNotes2.add("Floral");

        //create two coffee objects
        Coffee coffee1 = new Coffee("Espresso", "Arabica", "Small", 5.0,
                "Dark", "Ethiopia", false, 10, flavorNotes1, "Espresso");
        Coffee coffee2 = new Coffee("Latte", "Robusta", "Medium", 4.5,
                "Medium", "Colombia", true, 15, flavorNotes2, "French Press");

        //print the coffee1
        System.out.println(coffee1.describe());
        System.out.println("Price: $" + coffee1.calculatePrice("Large"));
        coffee1.addFlavor("Vanilla");
        coffee1.updateStock(-2);
        coffee1.setDecaf(true);
        coffee1.discount(10);
        System.out.println("Updated Price after discount: $" + coffee1.calculatePrice("Large"));
        System.out.println("Is in stock: " + coffee1.checkStock());

        //print the coffee2
        System.out.println(coffee2.describe());
        System.out.println("Price: $" + coffee2.calculatePrice("Medium"));
        coffee2.addFlavor("Caramel");
        coffee2.updateStock(5);
        coffee2.changeRoastLevel("Light");
        coffee2.discount(15);
        System.out.println("Updated Price after discount: $" + coffee2.calculatePrice("Medium"));
        System.out.println("Is in stock: " + coffee2.checkStock());
    }
}
