package com.cours.buddepas.models;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {
    private String username;
    private int budget;
    private ArrayList<Ingredient> stockArrayList;
    private ArrayList<Ingredient> shoppingArrayList;
    private ArrayList<ProgrammedRecipe> programmedRecipeArrayList;

    public UserData(){
    }

    public UserData(String username, int budget) {
        this.username = username;
        this.budget = budget;
    }

    public UserData(String username, int budget, ArrayList<Ingredient> stockArrayList, ArrayList<Ingredient> shoppingArrayList, ArrayList<ProgrammedRecipe> programmedRecipeArrayList) {
        this.username = username;
        this.budget = budget;
        this.stockArrayList = stockArrayList;
        this.shoppingArrayList = shoppingArrayList;
        this.programmedRecipeArrayList = programmedRecipeArrayList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<Ingredient> getStockArrayList() {
        return stockArrayList;
    }

    public void setStockArrayList(ArrayList<Ingredient> stockArrayList) {
        this.stockArrayList = stockArrayList;
    }

    public ArrayList<Ingredient> getShoppingArrayList() {
        return shoppingArrayList;
    }

    public void setShoppingArrayList(ArrayList<Ingredient> shoppingArrayList) {
        this.shoppingArrayList = shoppingArrayList;
    }

    public ArrayList<ProgrammedRecipe> getProgrammedRecipeArrayList() {
        return programmedRecipeArrayList;
    }

    public void setProgrammedRecipeArrayList(ArrayList<ProgrammedRecipe> programmedRecipeArrayList) {
        this.programmedRecipeArrayList = programmedRecipeArrayList;
    }
}
