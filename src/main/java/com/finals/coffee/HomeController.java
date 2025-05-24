package com.finals.coffee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private final CoffeeService coffeeService;

    @Autowired
    public HomeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        List<Coffee> filteredList = coffeeService.searchCoffee(search);
        model.addAttribute("coffees", filteredList);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("coffee", new Coffee(0, "", "", "", 0.0, "", "", false, 0, new ArrayList<>(), ""));
        return "create";
    }

    @PostMapping("/saveCoffee")
    public String saveCoffee(@RequestParam String name,
                             @RequestParam String type,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String roastLevel,
                             @RequestParam String origin,
                             @RequestParam(required = false, defaultValue = "false") boolean isDecaf,
                             @RequestParam int stock,
                             @RequestParam(required = false, defaultValue = "") String flavorNotes,
                             @RequestParam String brewMethod) {

        List<String> notesList = flavorNotes.isEmpty() ? new ArrayList<>() : Arrays.asList(flavorNotes.split("\\s*,\\s*"));
        int newId = coffeeService.getCoffees().stream().mapToInt(Coffee::getId).max().orElse(0) + 1;

        Coffee coffee = new Coffee(newId, name, type, size, price, roastLevel, origin, isDecaf, stock, notesList, brewMethod);
        coffeeService.addCoffee(coffee);

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffeeToEdit = coffeeService.getCoffees().stream()
                .filter(coffee -> coffee.getId() == id)
                .findFirst().orElse(null);
        if (coffeeToEdit != null) {
            model.addAttribute("coffee", coffeeToEdit);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam(required = false, defaultValue = "false") boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam List<String> flavorNotes,
                         @RequestParam String brewMethod) {

        coffeeService.deleteCoffee(id); // Remove old
        Coffee updated = new Coffee(id, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(updated); // Add new
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("coffee", new Coffee(0, "", "", "", 0.0, "", "", false, 0, new ArrayList<>(), ""));
        return "create";
    }
}
