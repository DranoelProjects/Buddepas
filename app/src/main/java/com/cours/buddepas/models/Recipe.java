package com.cours.buddepas.models;

import java.util.ArrayList;

public class Recipe {
    private long id;
    private String name;
    public String author;
    private String kind;
    private Integer peopleNumber;
    private Integer minutesDuration;
    private ArrayList<Ingredient> ingredientsArrayList;
    private ArrayList<Step> stepsArrayList;

    public Recipe(){}

    public Recipe(long id, String name, String author, String kind, Integer peopleNumber, Integer minutesDuration, ArrayList<Ingredient> ingredientsArrayList, ArrayList<Step> stepsArrayList) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.peopleNumber = peopleNumber;
        this.minutesDuration = minutesDuration;
        this.ingredientsArrayList = ingredientsArrayList;
        this.stepsArrayList = stepsArrayList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
