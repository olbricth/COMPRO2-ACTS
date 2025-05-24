package com.finals.coffee;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {

    private static final String CSV_FILE_PATH = "coffee_list.csv";
    private List<Coffee> coffeeList = new ArrayList<>();

    public List<Coffee> getCoffees() {
        return new ArrayList<>(coffeeList);
    }

    public void addCoffee(Coffee coffee) {
        coffeeList.add(coffee);
        saveToCsv(coffeeList);
    }

    public void deleteCoffee(int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        saveToCsv(coffeeList);
    }

    public List<Coffee> searchCoffee(String keyword) {
        if (keyword == null || keyword.isEmpty()) return getCoffees();
        return coffeeList.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void writeToDisk() {
        saveToCsv(coffeeList);
    }

    public boolean saveToCsv(List<Coffee> coffeeList) {
        try (FileWriter csvWriter = new FileWriter(CSV_FILE_PATH)) {
            csvWriter.append("ID,Name,Type,Size,Price,Roast Level,Origin,IsDecaf,Stock,Flavor Notes,Brew Method\n");
            for (Coffee coffee : coffeeList) {
                csvWriter.append(String.valueOf(coffee.getId())).append(",");
                csvWriter.append(escapeCsvField(coffee.getName())).append(",");
                csvWriter.append(escapeCsvField(coffee.getType())).append(",");
                csvWriter.append(escapeCsvField(coffee.getSize())).append(",");
                csvWriter.append(String.valueOf(coffee.getPrice())).append(",");
                csvWriter.append(escapeCsvField(coffee.getRoastLevel())).append(",");
                csvWriter.append(escapeCsvField(coffee.getOrigin())).append(",");
                csvWriter.append(String.valueOf(coffee.isDecaf())).append(",");
                csvWriter.append(String.valueOf(coffee.getStock())).append(",");
                String flavorNotesStr = String.join(";", coffee.getFlavorNotes());
                csvWriter.append(escapeCsvField(flavorNotesStr)).append(",");
                csvWriter.append(escapeCsvField(coffee.getBrewMethod())).append("\n");
            }
            System.out.println("CSV file has been updated at: " + CSV_FILE_PATH);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving coffee list to CSV: " + e.getMessage());
            return false;
        }
    }

    private String escapeCsvField(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
