package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.InputRecipeIngredientAdapter;
import com.cours.buddepas.adapters.InputRecipeStepAdapter;
import com.cours.buddepas.adapters.OutputRecipeIngredientAdapter;
import com.cours.buddepas.adapters.OutputRecipeStepAdapter;
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
    private androidx.recyclerview.widget.RecyclerView recyclerViewIngredients;
    private androidx.recyclerview.widget.RecyclerView recyclerViewSteps;
    private ImageButton imageButtonGoBack;

    //RecyclerView
    private OutputRecipeStepAdapter outputRecipeStepAdapter;
    private RecyclerView.LayoutManager recipeStepsLayoutManager;
    private OutputRecipeIngredientAdapter outputRecipeIngredientAdapter;
    private RecyclerView.LayoutManager recipeIngredientsLayoutManager;

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
        title = findViewById(R.id.title);
        recipe_author = findViewById(R.id.recipe_author);
        recipe_type = findViewById(R.id.recipe_type);
        recipe_number_people = findViewById(R.id.recipe_number_people);
        recipe_duration = findViewById(R.id.recipe_duration);
        recyclerViewSteps = findViewById(R.id.array_steps);
        recyclerViewIngredients = findViewById(R.id.array_ingredients);
        imageButtonGoBack = findViewById(R.id.go_back_to_recipes_list);

        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

            recipeIngredientsLayoutManager = new LinearLayoutManager(RecipeDetailsActivity.this);
            recipeStepsLayoutManager = new LinearLayoutManager(RecipeDetailsActivity.this);
            recyclerViewIngredients.setLayoutManager(recipeIngredientsLayoutManager);
            recyclerViewSteps.setLayoutManager(recipeStepsLayoutManager);
            outputRecipeIngredientAdapter = new OutputRecipeIngredientAdapter();
            outputRecipeStepAdapter = new OutputRecipeStepAdapter();
            recyclerViewIngredients.setAdapter(outputRecipeIngredientAdapter);
            recyclerViewSteps.setAdapter(outputRecipeStepAdapter);
            outputRecipeStepAdapter.setRecipeStepsList(recipe.getStepsArrayList());
            outputRecipeIngredientAdapter.setRecipeIngredientsList(recipe.getIngredientsArrayList());
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }
}
