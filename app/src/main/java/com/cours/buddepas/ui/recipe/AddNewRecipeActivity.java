package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.R;
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
    private ListView listViewIngredients;
    private ListView listViewSteps;
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
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initUI();
    }

    private void initUI(){
        listViewIngredients = findViewById(R.id.list_view_input_recipe_ingredients);
        listViewSteps = findViewById(R.id.list_view_input_recipe_steps);
        editTextRecipeName = findViewById(R.id.input_recipe_name);
        editTextRecipeKind = findViewById(R.id.input_recipe_kind);
        npPeopleNumber = findViewById(R.id.input_people_number);
        npDuration = findViewById(R.id.input_recipe_duration);
        cancel = findViewById(R.id.cancel_new_recipe);
        submitRecipe = findViewById(R.id.submit_new_recipe);
        imageButtonNewIngredient = findViewById(R.id.add_new_recipe_ingredient_button);
        imageButtonNewStep = findViewById(R.id.add_new_recipe_step_button);

        //init lists view
        ingredientsArrayList.add(new Ingredient());
        ingredientsArrayList.add(new Ingredient());
        stepsArrayList.add(new Step());
        stepsArrayList.add(new Step());
        listViewIngredients.setItemsCanFocus(true);
        listViewSteps.setItemsCanFocus(true);
        recipeIngredientAdapter = new RecipeIngredientAdapter(this, ingredientsArrayList);
        recipeStepAdapter = new RecipeStepAdapter(this, stepsArrayList);
        listViewIngredients.setAdapter(recipeIngredientAdapter);
        listViewSteps.setAdapter(recipeStepAdapter);

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
}
