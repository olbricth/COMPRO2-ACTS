package com.gaby.finalreq;

import com.gaby.finalreq.Coffee;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private List<Coffee> coffeeExamList;
    private final String FILE_NAME = "database.csv";

    public CoffeeService() {
        coffeeExamList = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> getCoffeeExamList() {
        return coffeeExamList;
    }

    public void deleteCoffeeExam(int id) {
        coffeeExamList.removeIf(coffeeExam -> coffeeExam.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword){
        if(keyword.trim().isEmpty()){
            return new ArrayList<>(coffeeExamList);
        }

        String lower = keyword.toLowerCase();
        return coffeeExamList.stream().filter(s ->
                (s.getName() != null && s.getName().toLowerCase().contains(lower)) ||
                        (s.getType() != null && s.getType().toLowerCase().contains(lower)) ||
                        (s.getSize() != null && s.getSize().toLowerCase().contains(lower)) ||
                        (s.getBrewMethod() != null && s.getBrewMethod().toLowerCase().contains(lower)) ||
                        (s.getFlavorNotes() != null && s.getFlavorNotes().stream().anyMatch(fn -> fn.toLowerCase().contains(lower))) ||
                        (s.getRoastLevel() != null && s.getRoastLevel().toLowerCase().contains(lower)) ||
                        (s.getOrigin() != null && s.getOrigin().toLowerCase().contains(lower))
        ).collect(Collectors.toList());
    }

    public Coffee getCoffee(int id){
        return coffeeExamList.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void updateCoffee(int id, Coffee update){
        for(int i = 0; i < coffeeExamList.size(); i++){
            if(coffeeExamList.get(i).getId() == id){
                coffeeExamList.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    public void addCoffee(Coffee coffeeExam){
        coffeeExamList.add(coffeeExam);
        writeToDisk();
    }

    public int getId(){
        if(coffeeExamList.isEmpty()){
            return 0;
        }
        return coffeeExamList.get(coffeeExamList.size() - 1).getId();
    }

    public void writeToDisk(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Coffee s : coffeeExamList){
                String line = s.getId() + ","
                        + escapeCSV(s.getName()) + ","
                        + escapeCSV(s.getType()) + ","
                        + escapeCSV(s.getSize()) + ","
                        + s.getPrice() + ","
                        + escapeCSV(s.getRoastLevel()) + ","
                        + escapeCSV(s.getOrigin()) + ","
                        + s.isDecaf() + ","
                        + s.getStock() + ","
                        + escapeCSV(s.getBrewMethod()) + ","
                        + escapeCSV(String.join(";", s.getFlavorNotes() != null ? s.getFlavorNotes() : new ArrayList<>())) + ","
                        + escapeCSV(s.getImagePath() != null ? s.getImagePath() : "");
                bw.write(line);
                bw.newLine();
            }
        }catch(IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFromDisk(){
        File file = new File(FILE_NAME);
        if(!file.exists()){
            System.out.println("File not found");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = splitCSV(line);

                Coffee c = new Coffee();
                c.setId(Integer.parseInt(data[0]));
                c.setName(data[1]);
                c.setType(data[2]);
                c.setSize(data[3]);
                c.setPrice(Double.parseDouble(data[4]));
                c.setRoastLevel(data[5]);
                c.setOrigin(data[6]);
                c.setDecaf(Boolean.parseBoolean(data[7]));
                c.setStock(Integer.parseInt(data[8]));
                c.setBrewMethod(data[9]);
                c.setFlavorNotes(data[10].isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(data[10].split(";"))));
                c.setImagePath(data.length > 11 ? data[11] : "");
                coffeeExamList.add(c);
            }
        }catch(IOException e){
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Escape commas, quotes and new lines in CSV fields by wrapping with quotes
    private String escapeCSV(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"") || input.contains("\n")) {
            input = input.replace("\"", "\"\"");
            return "\"" + input + "\"";
        }
        return input;
    }

    // Split CSV line respecting quotes (basic implementation)
    private String[] splitCSV(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for(char c : line.toCharArray()){
            if(c == '"'){
                inQuotes = !inQuotes;
            } else if(c == ',' && !inQuotes){
                tokens.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString());
        return tokens.toArray(new String[0]);
    }

    //Add this method to fix "cannot resolve getAllCoffee"
    public List<Coffee> getAllCoffee() {
        return new ArrayList<>(coffeeExamList);
    }
}
