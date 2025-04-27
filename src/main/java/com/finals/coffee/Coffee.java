package com.finals.coffee;

import jakarta.validation.constraints.*;
import java.util.List;

public class Coffee {
    private int id;

    @NotBlank(message = "Coffee name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Size is required")
    private String size;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0.01")
    private Double price;

    @NotBlank(message = "Roast level is required")
    private String roastLevel;

    @Size(max = 100, message = "Origin must be at most 100 characters")
    private String origin;

    private Boolean isDecaf;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be at least 0")
    private Integer stock;

    private List<String> flavorNotes;

    @NotBlank(message = "Brew method is required")
    private String brewMethod;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRoastLevel() {
        return roastLevel;
    }

    public void setRoastLevel(String roastLevel) {
        this.roastLevel = roastLevel.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin.trim();
    }

    public Boolean getIsDecaf() {
        return isDecaf;
    }

    public void setIsDecaf(Boolean isDecaf) {
        this.isDecaf = isDecaf;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<String> getFlavorNotes() {
        return flavorNotes;
    }

    public void setFlavorNotes(List<String> flavorNotes) {
        this.flavorNotes = flavorNotes;
    }

    public String getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod.trim();
    }

    public Boolean isDecaf() {
        return this.isDecaf;
    }
}