package com.cours.buddepas.models;

import java.util.ArrayList;

public class ProgrammedRecipe {
    private long id;
    private String name;
    public String author;
    private String kind;
    private Integer peopleNumber;
    private Integer minutesDuration;
    private ArrayList<Ingredient> ingredientsArrayList;
    private ArrayList<Step> stepsArrayList;
    private String date;
    private String time;

    public ProgrammedRecipe(){}

    public ProgrammedRecipe(Recipe recipe, String date, String time){
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.author = recipe.getAuthor();
        this.kind = recipe.getKind();
        this.peopleNumber = recipe.getPeopleNumber();
        this.minutesDuration = recipe.getMinutesDuration();
        this.ingredientsArrayList = recipe.getIngredientsArrayList();
        this.stepsArrayList = recipe.getStepsArrayList();
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ProgrammedRecipe(long id, String name, String author, String kind, Integer peopleNumber, Integer minutesDuration, ArrayList<Ingredient> ingredientsArrayList, ArrayList<Step> stepsArrayList, String date, String time) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.peopleNumber = peopleNumber;
        this.minutesDuration = minutesDuration;
        this.ingredientsArrayList = ingredientsArrayList;
        this.stepsArrayList = stepsArrayList;
        this.date = date;
        this.time = time;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
