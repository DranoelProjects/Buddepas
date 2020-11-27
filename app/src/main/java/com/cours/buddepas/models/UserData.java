package com.cours.buddepas.models;

public class UserData {
    private String username;
    private int budget;

    public UserData(){
    }

    public UserData(String username, int budget) {
        this.username = username;
        this.budget = budget;
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
}
