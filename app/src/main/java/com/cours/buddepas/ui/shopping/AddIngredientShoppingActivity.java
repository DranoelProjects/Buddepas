package com.cours.buddepas.ui.shopping;

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
import com.cours.buddepas.models.Step;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.ingredients.AddNewIngredientActivity;

public class AddIngredientShoppingActivity extends AppCompatActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();

    //UI
    private EditText ingredient_name;
    private EditText ingredient_quantity;
    private EditText ingredient_unit;
    private EditText ingredient_type;
    private EditText ingredient_price;
    private Button cancel;
    private Button submitIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient_courses);
        initUI();
    }

    private void initUI() {
        //findView
        ingredient_name = findViewById(R.id.input_shopping_ingredient_name);
        ingredient_quantity = findViewById(R.id.input_shopping_ingredient_quantity);
        ingredient_unit = findViewById(R.id.input_shopping_ingredient_unit);
        ingredient_type = findViewById(R.id.input_shopping_ingredient_type);
        ingredient_price = findViewById(R.id.input_shopping_ingredient_price);
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

    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                currentField.setError("Ce champ ne peut pas être vide");
                return false;
            }
        }
        return true;
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
                Float.valueOf(ingredient_price.getText().toString().replace(",","."))
        );

        boolean fieldsOK = validate(new EditText[]{ingredient_name, ingredient_quantity, ingredient_unit, ingredient_price});
        if(fieldsOK) {
            apiManager.AddIngredientShopping(newIngredient);
            Toast.makeText(AddIngredientShoppingActivity.this, "Ingrédient ajouté à la liste de courses", Toast.LENGTH_SHORT).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent myIntent = new Intent(AddIngredientShoppingActivity.this, MainActivity.class);
                    startActivity(myIntent);
                }
            }, 2000);
        }
    }

}
