package com.cours.buddepas.ui.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner ingredient_type;
    private EditText ingredient_unit;
    private String type = "";
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

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> kindadapter = ArrayAdapter.createFromResource(this, R.array.select_apport, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        kindadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ingredient_type.setAdapter(kindadapter);

        ingredient_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                type = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                type = "Féculent";
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
                type,
                ingredient_name.getText().toString(),
                Integer.valueOf(ingredient_quantity.getText().toString()),
                ingredient_unit.getText().toString(),
                (float) 0
        );

        boolean fieldsOK = validate(new EditText[]{ingredient_name, ingredient_quantity, ingredient_unit});

        /*if(TextUtils.isEmpty(ingredient_name.getText())){
            ingredient_name.setError("Ce champ ne peut pas être vide");
        }
        else if(TextUtils.isEmpty(ingredient_quantity.getText())){
            ingredient_quantity.setError("Ce champ ne peut pas être vide");
        }
        else if(TextUtils.isEmpty(ingredient_unit.getText())){
            ingredient_unit.setError("Ce champ ne peut pas être vide");
        }
        else if(TextUtils.isEmpty(ingredient_type.getText())){
            ingredient_type.setError("Ce champ ne peut pas être vide");
        }
        else {*/
        if(fieldsOK){
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

}
