package com.finals.coffee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/") // Handles requests to the home page and applies a search filter
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        List<Coffee> coffeeList = coffeeService.getAllCoffees(); // Fetch list from service

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
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/coffee/";
    }

    @GetMapping("/new")
    public String create(Model model) {
        List<String> roastLevels = List.of("Light", "Medium", "Dark");
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("newCoffee", new Coffee());
        return "create";
    }

    @PostMapping("/save")
    public String store(@ModelAttribute("newCoffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            return "create";
        }
        coffeeService.addCoffee(coffee);
        return "redirect:/coffee/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            model.addAttribute("coffee", coffee);
            return "edit";
        }
        return "redirect:/coffee/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            model.addAttribute("coffee", coffee);
            return "edit";
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/coffee/";
    }
}