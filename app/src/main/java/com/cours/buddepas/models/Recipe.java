package com.cours.buddepas.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String kind;
    private Integer peopleNumber;
    private Integer minutesDuration;
    private ArrayList<Ingredient> ingredientsArrayList;
    private ArrayList<Step> stepsArrayList;

    public Recipe(String name, String kind, Integer peopleNumber, Integer minutesDuration, ArrayList<Ingredient> ingredientsArrayList, ArrayList<Step> stepsArrayList) {
        this.name = name;
        this.kind = kind;
        this.peopleNumber = peopleNumber;
        this.minutesDuration = minutesDuration;
        this.ingredientsArrayList = ingredientsArrayList;
        this.stepsArrayList = stepsArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getMinutesDuration() {
        return minutesDuration;
    }

    public void setMinutesDuration(Integer minutesDuration) {
        this.minutesDuration = minutesDuration;
    }

    public ArrayList<Ingredient> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(ArrayList<Ingredient> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }

    public ArrayList<Step> getStepsArrayList() {
        return stepsArrayList;
    }

    public void setStepsArrayList(ArrayList<Step> stepsArrayList) {
        this.stepsArrayList = stepsArrayList;
    }
}
