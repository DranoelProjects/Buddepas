package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.adapters.InputRecipeIngredientAdapter;
import com.cours.buddepas.adapters.InputRecipeStepAdapter;
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

    //UI
    private static androidx.recyclerview.widget.RecyclerView recyclerViewIngredients;
    private static androidx.recyclerview.widget.RecyclerView recyclerViewSteps;
    private EditText editTextRecipeName;
    private Spinner recipeKind;
    private String kind;
    private EditText npPeopleNumber;
    private EditText npDuration;
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
        apiManager.InitRecipesMaxId();
    }

    private void initUI(){
        //findView
        recyclerViewIngredients = findViewById(R.id.recycler_view_input_recipe_ingredients);
        recyclerViewSteps = findViewById(R.id.recycler_view_input_recipe_steps);
        editTextRecipeName = findViewById(R.id.input_recipe_name);
        recipeKind = findViewById(R.id.input_recipe_kind);
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
        ingredientsArrayList.add(new Ingredient("Viande", "",2,"kg",2));
        ingredientsArrayList.add(new Ingredient("Féculent","",2,"kg",2));
        stepsArrayList.add(new Step());
        stepsArrayList.add(new Step());
        recipeIngredientAdapter.setRecipeIngredientsList(ingredientsArrayList);
        recipeStepAdapter.setRecipeStepsList(stepsArrayList);
        recipe.setIngredientsArrayList(ingredientsArrayList);
        recipe.setStepsArrayList(stepsArrayList);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> kindadapter = ArrayAdapter.createFromResource(this, R.array.select_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        kindadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        recipeKind.setAdapter(kindadapter);

        recipeKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                kind = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                kind = "Plat";
            }
        });

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
                submitRecipe();
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

    private void submitRecipe(){
        String author = "Anonyme";
        if(singleton.getCurrentUserData() != null && singleton.getCurrentUserData().getUsername() != null){
            author = singleton.getCurrentUserData().getUsername();
        }

        Recipe newRecipe = new Recipe(
                0,
                editTextRecipeName.getText().toString(),
                author,
                kind,
                Integer.valueOf(npPeopleNumber.getText().toString()),
                Integer.valueOf(npDuration.getText().toString()),
                (ArrayList) recipeIngredientAdapter.getRecipeIngredientsList(),
                (ArrayList) recipeStepAdapter.getRecipeStepsList()
        );
        apiManager.AddNewRecipe(newRecipe);
        Toast.makeText(AddNewRecipeActivity.this, "Recette ajoutée", Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(AddNewRecipeActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        }, 2000);
    }
}
