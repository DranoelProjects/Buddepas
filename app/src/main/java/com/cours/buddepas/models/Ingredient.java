package com.cours.buddepas.models;

public class Ingredient {
    private long id;
    private String kind;
    private String name;
    private Integer amount;
    private String unit;
    private Float price;

    public Ingredient(){}

    public Ingredient(Integer id, String kind, String name, Integer amount, String unit, Float price) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getGramAmount() {
        if (unit.toLowerCase() == "kg")
        {
            return 1000*amount;
        }
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
