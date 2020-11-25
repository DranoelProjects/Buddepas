package com.cours.buddepas.models;

public class Ingredient {
    private String name;
    private Integer amount;
    private String unit;
    private Integer price;

    public Ingredient(String name, Integer amount, String unit, Integer price) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
