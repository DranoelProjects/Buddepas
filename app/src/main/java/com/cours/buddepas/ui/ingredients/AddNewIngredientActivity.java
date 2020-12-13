package com.cours.buddepas.ui.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;

import java.util.ArrayList;

public class AddNewIngredientActivity extends AppCompatActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();

    //UI
    private EditText ingredient_name;
    private EditText ingredient_quantity;
    private EditText ingredient_type;
    private EditText ingredient_unit;
    private Button cancel;
    private Button submitIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        initUI();
    }

    private void initUI() {
        //findView
        ingredient_name = findViewById(R.id.input_stock_ingredient_name);
        ingredient_quantity = findViewById(R.id.input_stock_ingredient_quantity);
        ingredient_type = findViewById(R.id.input_stock_ingredient_type);
        ingredient_unit = findViewById(R.id.input_stock_ingredient_unit);
        cancel = findViewById(R.id.cancel_new_ingredient);
        submitIngredient = findViewById(R.id.submit_new_ingredient);


        //Init buttons
        //cancel action
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //submit recipe action
        submitIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitIngredient();
            }
        });
    }



    private void submitIngredient(){
        String author = "Anonyme";
        if(singleton.getCurrentUserData() != null && singleton.getCurrentUserData().getUsername() != null){
            author = singleton.getCurrentUserData().getUsername();
        }

        Ingredient newIngredient = new Ingredient(
                ingredient_type.getText().toString(),
                ingredient_name.getText().toString(),
                Integer.valueOf(ingredient_quantity.getText().toString()),
                ingredient_unit.getText().toString(),
                0
        );
        apiManager.AddNewIngredient(newIngredient);
        Toast.makeText(AddNewIngredientActivity.this, "Ingrédient ajouté", Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(AddNewIngredientActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        }, 2000);
    }

}