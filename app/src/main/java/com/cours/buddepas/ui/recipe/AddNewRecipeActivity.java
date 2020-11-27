package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.InputRecipeIngredientAdapter;
import com.cours.buddepas.adapters.InputRecipeStepAdapter;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.adapters.RecipeIngredientAdapter;
import com.cours.buddepas.adapters.RecipeStepAdapter;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;

import java.util.ArrayList;

public class AddNewRecipeActivity extends AppCompatActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private String TAG = "AddNewRecipeActivity";

    //UI
    private static androidx.recyclerview.widget.RecyclerView recyclerViewIngredients;
    private static androidx.recyclerview.widget.RecyclerView recyclerViewSteps;
    private EditText editTextRecipeName;
    private EditText editTextRecipeKind;
    private com.shawnlin.numberpicker.NumberPicker npPeopleNumber;
    private com.shawnlin.numberpicker.NumberPicker npDuration;
    private Button cancel;
    private Button submitRecipe;
    private ImageButton imageButtonNewIngredient;
    private ImageButton imageButtonNewStep;

    //init form
    private ArrayList<Ingredient> ingredientsArrayList = new ArrayList();
    private ArrayList<Step> stepsArrayList = new ArrayList();
    private InputRecipeIngredientAdapter recipeIngredientAdapter;
    private InputRecipeStepAdapter recipeStepAdapter;
    private RecyclerView.LayoutManager recipeIngredientsLayoutManager;
    private RecyclerView.LayoutManager recipeStepsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initUI();
    }

    private void initUI(){
        //findView
        recyclerViewIngredients = findViewById(R.id.recycler_view_input_recipe_ingredients);
        recyclerViewSteps = findViewById(R.id.recycler_view_input_recipe_steps);
        editTextRecipeName = findViewById(R.id.input_recipe_name);
        editTextRecipeKind = findViewById(R.id.input_recipe_kind);
        npPeopleNumber = findViewById(R.id.input_people_number);
        npDuration = findViewById(R.id.input_recipe_duration);
        cancel = findViewById(R.id.cancel_new_recipe);
        submitRecipe = findViewById(R.id.submit_new_recipe);
        imageButtonNewIngredient = findViewById(R.id.add_new_recipe_ingredient_button);
        imageButtonNewStep = findViewById(R.id.add_new_recipe_step_button);

        //init recyclers view
        recipeIngredientsLayoutManager = new LinearLayoutManager(this);
        recipeStepsLayoutManager = new LinearLayoutManager(this);
        recyclerViewIngredients.setLayoutManager(recipeIngredientsLayoutManager);
        recyclerViewSteps.setLayoutManager(recipeStepsLayoutManager);
        recipeIngredientAdapter = new InputRecipeIngredientAdapter();
        recipeStepAdapter = new InputRecipeStepAdapter();
        recyclerViewIngredients.setAdapter(recipeIngredientAdapter);
        recyclerViewSteps.setAdapter(recipeStepAdapter);

        //init new recipe
        Recipe recipe = new Recipe();
        ingredientsArrayList.add(new Ingredient());
        ingredientsArrayList.add(new Ingredient());
        stepsArrayList.add(new Step());
        stepsArrayList.add(new Step());
        recipeIngredientAdapter.setRecipeIngredientsList(ingredientsArrayList);
        recipeStepAdapter.setRecipeStepsList(stepsArrayList);
        recipe.setIngredientsArrayList(ingredientsArrayList);
        recipe.setStepsArrayList(stepsArrayList);

        //Init buttons
        //cancel action
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //submit recipe action
        submitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "send new recipe");
            }
        });

        imageButtonNewIngredient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recipeIngredientAdapter.AddNewIngredient();
            }
        });

        imageButtonNewStep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recipeStepAdapter.AddNewStep();
            }
        });

    }

    public static void RemoveIngredient(int position){
        recyclerViewIngredients.removeViewAt(position);
    }

    public static void RemoveStep(int position){
        recyclerViewSteps.removeViewAt(position);
    }
}
