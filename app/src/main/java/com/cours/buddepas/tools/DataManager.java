package com.cours.buddepas.tools;

import android.util.Log;
import android.widget.Toast;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    //Instance
    private DataManager() {
    }
    private static DataManager INSTANCE = new DataManager();
    public static DataManager getInstance() {
        return INSTANCE;
    }
    private String TAG = "DataManager";

    //Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    public void AddNewAndUpdateRecipe(){
        // Write a message to the database
        String name = "Tartare";
        String author = "Léonard";
        String kind = "Plat";
        Integer peopleNumber = 4;
        Integer minutesDuration = 60;
        ArrayList<Ingredient> ingredientsArrayList = new ArrayList<>();
        ArrayList<Step> stepsArrayList = new ArrayList<>();
        ingredientsArrayList.add(new Ingredient("Cuisses de poulet", 6, "pièces", 5));
        ingredientsArrayList.add(new Ingredient("Semoule", 1, "Kg", 2));
        stepsArrayList.add(new Step("Cuir le poulet" ));
        stepsArrayList.add(new Step("Cuir la semoule"));

        DatabaseReference recipeRef = database.getReference("recipes/"+name);
        recipeRef.setValue(new Recipe(name, author, kind, peopleNumber, minutesDuration, ingredientsArrayList, stepsArrayList));
    }

    public void GetAllRecipes(){
        DatabaseReference recipesRef = database.getReference("recipes");
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipesArrayList = new ArrayList<>();

                for (DataSnapshot dataValues : dataSnapshot.getChildren()){
                    Recipe recipe = dataValues.getValue(Recipe.class);
                    recipesArrayList.add(recipe);
                }

                for(int i=0;i<recipesArrayList.size();i++){
                    Log.d(TAG, "TaskTitle = "+recipesArrayList.get(i).getMinutesDuration());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
