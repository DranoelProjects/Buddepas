package com.cours.buddepas.tools;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApiManager {
    //Instance
    private ApiManager() {
    }
    private static ApiManager INSTANCE = new ApiManager();
    public static ApiManager getInstance() {
        return INSTANCE;
    }
    private String TAG = "ApiManager";
    static Singleton singleton = Singleton.getInstance(); //Singleton

    //Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    //Recipes
    long maxId = 0;

    public void AddNewRecipe(Recipe recipe){
        /*String name = "Couscous";
        String author = "Léonard";
        String kind = "Plat";
        Integer peopleNumber = 4;
        Integer minutesDuration = 60;
        ArrayList<Ingredient> ingredientsArrayList = new ArrayList<>();
        ArrayList<Step> stepsArrayList = new ArrayList<>();
        ingredientsArrayList.add(new Ingredient("Cuisses de poulet", 6, "pièces", 5));
        ingredientsArrayList.add(new Ingredient("Semoule", 1, "Kg", 2));
        stepsArrayList.add(new Step("Cuir le poulet" ));
        stepsArrayList.add(new Step("Cuir la semoule"));*/

        //get following ID in order to increment it
        DatabaseReference recipeRef = database.getReference().child("recipes");
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxId=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Recipe recipe = new Recipe(maxId+1, name, author, kind, peopleNumber, minutesDuration, ingredientsArrayList, stepsArrayList);
        //adding new recipe to database
        recipeRef.child(String.valueOf(maxId+1)).setValue(recipe);
    }

    public void GetAllRecipes(){
        singleton.setLoading(true);
        DatabaseReference recipesRef = database.getReference("recipes");
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Recipe> recipesArrayList = new ArrayList<>();

                for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                    Recipe recipe = dataValues.getValue(Recipe.class);
                    recipesArrayList.add(recipe);
                }

                for(int i=0;i<recipesArrayList.size();i++){
                    Log.d(TAG, "RecipeName = "+recipesArrayList.get(i).getName());
                }

                singleton.setRecipesArrayList((ArrayList<Recipe>) recipesArrayList);
                singleton.setLoading(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void GetRecipe(long id){
        DatabaseReference recipeRef = database.getReference("recipes/"+id);
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Recipe currentRecipe = dataSnapshot.getValue(Recipe.class);
                singleton.setCurrentRecipe(currentRecipe);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
