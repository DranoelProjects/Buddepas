package com.cours.buddepas.tools;

import android.util.Log;

import com.cours.buddepas.models.Filter;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.models.UserData;
import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Singleton {
    private String TAG = "Singleton";
    private Recipe currentRecipe;
    private ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    private ArrayList<Recipe> filteredArrayList = new ArrayList<>();
    private boolean loading = true;
    private boolean loadingUserData = true;
    private UserData currentUserData = new UserData();
    private ArrayList<ProgrammedRecipe> calendarCopy = new ArrayList<>();
    private boolean filtered = false;
    private boolean activatedfilters = false;
    private Filter filter = new Filter();
    private ArrayList<Ingredient> ingredientsArrayList = new ArrayList<>();

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
        if (filtered) {
            return filteredArrayList;
        }
        return completeFiltered();
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
        if(currentUserData != null && currentUserData.getProgrammedRecipeArrayList()!= null){
            this.calendarCopy = new ArrayList<>(currentUserData.getProgrammedRecipeArrayList());
        } else {
            this.calendarCopy = new ArrayList<>();
        }
    }

    public boolean isLoadingUserData() {
        return loadingUserData;
    }

    public void setLoadingUserData(boolean loadingUserData) {
        this.loadingUserData = loadingUserData;
    }

    public ArrayList<ProgrammedRecipe> getCalendarCopy() {
        return calendarCopy;
    }

    public void filter(String filter) {
        filteredArrayList = new ArrayList<>();
        String[] filters = filter.split(" ");
        for (int i = 0; i < recipesArrayList.size();i++)
        {
            if (isValid(filters,recipesArrayList.get(i).getName()))
            {
                filteredArrayList.add(recipesArrayList.get(i));
            }
        }
        filtered = true;
    }

    public void cancelFilter()
    {
        filtered = false;
    }

    boolean isValid(String[] filters,String recipe)
    {
        for (int i = 0; i < filters.length; i++)
        {
            if (!recipe.toLowerCase().contains(filters[i].toLowerCase()))
            {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Ingredient> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(ArrayList<Ingredient> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }

    public void setFilter(Filter f)
    {
        this.filter = f;
    }

    private ArrayList<Recipe> completeFiltered()
    {
        ArrayList<Recipe> filtered = new ArrayList<Recipe>();
        for (int i = 0; i < recipesArrayList.size();i++)
        {
            if (filter.fitFilters(recipesArrayList.get(i)))
            {
                filtered.add(recipesArrayList.get(i));
            }
        }
        return filtered;
    }
}
