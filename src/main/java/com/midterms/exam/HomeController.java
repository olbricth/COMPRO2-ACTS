package com.midterms.exam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController { //for handling web requests
    private List<Coffee> coffeeList = new ArrayList<>();

    //Constructor that initializes the list of coffee objects
    public HomeController() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10, List.of("Chocolate", "Nutty"), "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8, List.of("Creamy", "Sweet"), "Drip"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 5.00, "Medium", "Colombia", false, 12, List.of("Fruity", "Bold"), "French Press"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 4.75, "Dark", "Guatemala", false, 6, List.of("Chocolate", "Smooth"), "Espresso"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 3.25, "Light", "Kenya", false, 15, List.of("Citrus", "Balanced"), "Drip"));
        coffeeList.add(new Coffee(6, "Flat White", "Arabica", "Medium", 4.80, "Medium", "Australia", false, 7, List.of("Velvety", "Rich"), "Espresso"));
        coffeeList.add(new Coffee(7, "Macchiato", "Arabica", "Small", 4.00, "Dark", "Italy", false, 5, List.of("Caramel", "Strong"), "Espresso"));
        coffeeList.add(new Coffee(8, "Ristretto", "Robusta", "Small", 3.90, "Dark", "Vietnam", false, 4, List.of("Intense", "Bold"), "Espresso"));
        coffeeList.add(new Coffee(9, "Affogato", "Arabica", "Small", 5.50, "Dark", "Indonesia", false, 3, List.of("Vanilla", "Creamy"), "Espresso"));
    }

    @GetMapping("/") //Handles requests to the home page and applies a search filter
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        List<Coffee> filteredList = coffeeList.stream()
                .filter(coffee -> coffee.getName().toLowerCase().contains(search.toLowerCase()) ||
                        coffee.getType().toLowerCase().contains(search.toLowerCase()) ||
                        coffee.getSize().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(coffee.getPrice()).contains(search) ||
                        coffee.getRoastLevel().toLowerCase().contains(search.toLowerCase()) ||
                        coffee.getOrigin().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(coffee.isDecaf()).contains(search) ||
                        String.valueOf(coffee.getStock()).contains(search) ||
                        coffee.getFlavorNotes().toString().toLowerCase().contains(search.toLowerCase()) ||
                        coffee.getBrewMethod().toLowerCase().contains(search.toLowerCase()))
                .toList();

        model.addAttribute("coffees", filteredList);
        model.addAttribute("search", search);
        return "index"; //Returns the view for the coffee list

    }

    @GetMapping("/delete") //Handles deleting a coffee entry
    public String delete(@RequestParam int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        return "redirect:/";
    }

    @GetMapping("/new") //Loads the create page with an empty coffee object
    public String create(Model model) {
        model.addAttribute("coffee", new Coffee(0, "", "", "", 0.0, "", "", false, 0, new ArrayList<>(), ""));
        return "create";
    }

    @PostMapping("/saveCoffee") //Handles saving a new coffee entry

    public String saveCoffee(@RequestParam String name,
                             @RequestParam String type,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String roastLevel,
                             @RequestParam String origin,
                             @RequestParam(required = false) boolean isDecaf,
                             @RequestParam int stock,
                             @RequestParam(required = false, defaultValue = "") String flavorNotes,
                             @RequestParam String brewMethod) {

        List<String> notesList = flavorNotes.isEmpty() ? new ArrayList<>() : Arrays.asList(flavorNotes.split("\\s*,\\s*"));
        int newId = coffeeList.stream().mapToInt(Coffee::getId).max().orElse(0) + 1;
        Coffee coffee = new Coffee(newId, name, type, size, price, roastLevel, origin, isDecaf, stock, notesList, brewMethod);
        coffeeList.add(coffee);

        return "redirect:/";
    }

    @GetMapping("/edit") //Handles editing an existing coffee entry
    public String edit(@RequestParam int id, Model model) {
        Coffee coffeeToEdit = coffeeList.stream().filter(coffee -> coffee.getId() == id).findFirst().orElse(null);
        if (coffeeToEdit != null) {
            model.addAttribute("coffee", coffeeToEdit);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update") //Handles updating an existing coffee entry
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam(required = false) boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam List<String> flavorNotes,
                         @RequestParam String brewMethod) {

        coffeeList.replaceAll(coffee -> coffee.getId() == id ?
                new Coffee(id, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod) : coffee);
        return "redirect:/";
    }

    @GetMapping("/create") //Loads the create page with an empty coffee object
    public String createPage(Model model) {
        model.addAttribute("coffee", new Coffee(0, "", "", "", 0.0, "", "", false, 0, new ArrayList<>(), ""));
        return "create";

    }
}