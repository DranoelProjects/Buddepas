package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private long recipeId;
    private boolean loading = true;
    private Recipe recipe = new Recipe();
    private String TAG = "RecipeDetailsActivity";


    //UI
    private TextView title;
    private TextView recipe_author;
    private TextView recipe_type;
    private TextView recipe_number_people;
    private TextView recipe_duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recette);
        singleton.setLoading(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recipeId = bundle.getLong("RecipeId");
        }
        apiManager.GetRecipe(recipeId);
        new LoadingRecipeDetailsTask().execute();

        UIinit();
    }

    private void UIinit(){
        title = (TextView) findViewById(R.id.title);
        recipe_author = (TextView) findViewById(R.id.recipe_author);
        recipe_type = (TextView) findViewById(R.id.recipe_type);
        recipe_number_people = (TextView) findViewById(R.id.recipe_number_people);
        recipe_duration = (TextView) findViewById(R.id.recipe_duration);
    }


    class LoadingRecipeDetailsTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            while(loading){
                loading = singleton.isLoading();
            }
            recipe = singleton.getCurrentRecipe();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, recipe.getName());
            title.setText(recipe.getName());
            recipe_author.setText(recipe.getAuthor());
            recipe_type.setText(recipe.getKind());
            recipe_number_people.setText("" + recipe.getPeopleNumber());
            recipe_duration.setText("" + recipe.getMinutesDuration());
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }


    /*public void GetRecipeDetails(View v){
        //DatabaseReference ref_recipe_detail = database.getReference("recipes/"+"Couscous");


        //final ArrayList<Ingredient>[] ingredientsArrayList = new ArrayList<Ingredient>[1];
        final Integer[] recipe_quantity = new Integer[1];
        final String[] recipe_unit = new String[1];
        final String[] recipe_ingredient_name = new String[1];
        //final ArrayList<Step>[] recipe_steps = new ArrayList<Step>[1];
        final int[] recipe_order = new int[1];
        final String[] recipe_step_description = new String[1];


        /*ref_recipe_detail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                Recipe recipe = dataSnapshot.getValue(Recipe.class);

                ingredientsArrayList[0] = recipe.getIngredientsArrayList();
                for(int i = 0; i< ingredientsArrayList[0].size(); i++) {
                    recipe_quantity[0] = ingredientsArrayList[0].get(i).getAmount();
                    recipe_unit[0] = ingredientsArrayList[0].get(i).getUnit();
                    recipe_ingredient_name[0] = ingredientsArrayList[0].get(i).getName();
                }

                recipe_steps[0] = recipe.getStepsArrayList();
                for(int i = 0; i< recipe_steps[0].size(); i++) {
                    recipe_order[0] = i+1;
                    recipe_step_description[0] = recipe_steps[0].get(i).getDescription();
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RecipeDetailsActivity", "Failed to read value.", error.toException());
            }
        });

    }*/
}
