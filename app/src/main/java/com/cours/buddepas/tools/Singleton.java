package com.cours.buddepas.tools;

import android.util.Log;

import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.models.UserData;
import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;

public class Singleton {
    private String TAG = "Singleton";
    private Recipe currentRecipe;
    private ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    private boolean loading = true;
    private UserData currentUserData;

    //Instance
    private Singleton() {
    }
    private static Singleton INSTANCE = new Singleton();

    public static Singleton getInstance() {
        return INSTANCE;
    }

    //Getters and setters
    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public ArrayList<Recipe> getRecipesArrayList() {
        return recipesArrayList;
    }

    public void setRecipesArrayList(ArrayList<Recipe> recipesArrayList) {
        this.recipesArrayList = recipesArrayList;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public UserData getCurrentUserData() {
        return currentUserData;
    }

    public void setCurrentUserData(UserData currentUserData) {
        this.currentUserData = currentUserData;
    }
}
