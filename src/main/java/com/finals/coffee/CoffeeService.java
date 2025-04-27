package com.finals.coffee;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private ArrayList<Coffee> coffees;
    private final String FILE_NAME = "coffee_database.csv";

    public CoffeeService() {
        coffees = new ArrayList<>();
        // Load coffee data from CSV upon initialization
        readFromDisk();
    }

    public ArrayList<Coffee> getCoffees() {
        return coffees;
    }

    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword.trim().isEmpty()) {
            return coffees;
        }

        return coffees.stream().filter(c ->
                c.getName().toLowerCase().contains(keyword.toLowerCase())
                        || c.getType().toLowerCase().contains(keyword.toLowerCase())
                        || c.getRoastLevel().toLowerCase().contains(keyword.toLowerCase())
                        || c.getOrigin().toLowerCase().contains(keyword.toLowerCase())
                        || c.getBrewMethod().toLowerCase().contains(keyword.toLowerCase())
        ).collect(Collectors.toList());
    }

    public Coffee getCoffee(int id) {
        for (Coffee c : coffees) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public void updateCoffee(int id, Coffee updatedCoffee) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, updatedCoffee);
                writeToDisk();
                break;
            }
        }
    }

    public void addCoffee(Coffee coffee) {
        coffee.setId(getLastId() + 1);
        coffees.add(coffee);
        writeToDisk();
    }

    public int getLastId() {
        if (coffees.isEmpty()) {
            return 0;
        }
        return coffees.get(coffees.size() - 1).getId();
    }

    /**
     * This saves the coffee ArrayList into a CSV file
     */
    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee c : coffees) {
                bw.write(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.getIsDecaf() + ","
                        + c.getStock() + ","
                        + c.getFlavorNotes() + ","
                        + c.getBrewMethod()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving coffee data: " + e.getMessage());
        }
    }

    /**
     * This reads the CSV file and loads it to the coffees ArrayList
     */
    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Coffee database file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Coffee c = new Coffee();
                c.setId(Integer.parseInt(data[0]));
                c.setName(data[1]);
                c.setType(data[2]);
                c.setSize(data[3]);
                c.setPrice(Double.parseDouble(data[4]));
                c.setRoastLevel(data[5]);
                c.setOrigin(data[6]);
                c.setIsDecaf(Boolean.parseBoolean(data[7]));
                c.setStock(Integer.parseInt(data[8]));
                c.setFlavorNotes(List.of(data[9].split(";")));
                c.setBrewMethod(data[10]);

                coffees.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error reading coffee data: " + e.getMessage());
        }
    }

    public List<Coffee> getAllCoffees() {
        return coffees; // Return the stored list of Coffee objects
    }
}