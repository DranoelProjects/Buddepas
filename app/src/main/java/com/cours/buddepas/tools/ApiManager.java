package com.cours.buddepas.tools;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.models.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    //Recipes
    long maxId = 0;

    public void InitRecipesMaxId(){
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
    }
    public void AddNewRecipe(Recipe recipe){
        //adding new recipe to database
        DatabaseReference recipeRef = database.getReference().child("recipes");
        recipe.setId(maxId+1);
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
        singleton.setLoading(true);
        DatabaseReference recipeRef = database.getReference("recipes/"+id);
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Recipe currentRecipe = dataSnapshot.getValue(Recipe.class);
                singleton.setCurrentRecipe(currentRecipe);
                singleton.setLoading(false);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void SetUserData(UserData userData){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, ""+user.getUid());
        DatabaseReference userRef = database.getReference("users/"+user.getUid());
        userRef.setValue(userData);
    }

    public void GetUserData(){
        singleton.setLoadingUserData(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = database.getReference("users/"+user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData currentUserData = dataSnapshot.getValue(UserData.class);
                singleton.setCurrentUserData(currentUserData);
                singleton.setLoadingUserData(false);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
