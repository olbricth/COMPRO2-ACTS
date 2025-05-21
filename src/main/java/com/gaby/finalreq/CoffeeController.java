package com.gaby.finalreq;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    // Main page
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("coffee", coffeeService.searchCoffee(search));
        return "index";
    }

    // Delete
    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        coffeeService.deleteCoffeeExam(id);
        return "redirect:/";
    }

    // Go to add form
    @GetMapping("/add")
    public String add(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        return "new";
    }

    // Save new coffee
    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam String type,
                       @RequestParam String size,
                       @RequestParam double price,
                       @RequestParam String roastLevel,
                       @RequestParam String origin,
                       @RequestParam Boolean isDecaf,
                       @RequestParam int stock,
                       @RequestParam List<String> flavorNotes,
                       @RequestParam String brewMethod,
                       HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Coffee c = new Coffee();
        c.setId(coffeeService.getId() + 1);
        c.setName(name);
        c.setType(type);
        c.setSize(size);
        c.setPrice(price);
        c.setRoastLevel(roastLevel);
        c.setOrigin(origin);
        c.setDecaf(isDecaf);
        c.setStock(stock);
        c.setFlavorNotes(flavorNotes);
        c.setBrewMethod(brewMethod);

        coffeeService.addCoffee(c);
        return "redirect:/";
    }

    // Edit coffee form
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            model.addAttribute("coffee", c);
            return "edit";
        }
        return "redirect:/";
    }

    // Save edited coffee
    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam(required = false) Boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam String flavorNotes,
                         @RequestParam String brewMethod,
                         HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            c.setName(name);
            c.setType(type);
            c.setSize(size);
            c.setPrice(price);
            c.setRoastLevel(roastLevel);
            c.setOrigin(origin);
            if (isDecaf != null) {
                c.setDecaf(isDecaf);
            }
            c.setStock(stock);
            if (flavorNotes != null && !flavorNotes.isEmpty()) {
                c.setFlavorNotes(Arrays.asList(flavorNotes.split(",")));
            }
            c.setBrewMethod(brewMethod);

            coffeeService.updateCoffee(id, c);
        }
        return "redirect:/";
    }

    // **Added dashboard method**
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        List<Coffee> coffeeList = coffeeService.getCoffeeExamList();
        model.addAttribute("coffee", coffeeList);
        return "menu";  // Render menu.html
    }
}
