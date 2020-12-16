package com.cours.buddepas.ui.calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.cours.buddepas.ui.recipe.RecipeProposedActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddProgrammedRecipeActivity extends AppCompatActivity {
    //Instances
    private Singleton singleton = Singleton.getInstance();
    private String TAG = "AddProgrammedRecipeActivity";
    private ApiManager apiManager = ApiManager.getInstance();

    //UI
    ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    ArrayList<String> recipesNamesArrayList = new ArrayList<>();
    Recipe recipeToAdd;
    ProgrammedRecipe programmedRecipe;
    DatePickerDialog datePickerDialog;

    //UserData
    private UserData userData;
    private ArrayList<ProgrammedRecipe> programmedRecipeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_program_recipe);
        initUI();
    }

    private void initUI(){
        recipesArrayList = singleton.getRecipesArrayList();
        recipesNamesArrayList.clear();
        if(recipesArrayList != null){
            for(int i=0;i<recipesArrayList.size();i++){
                recipesNamesArrayList.add(recipesArrayList.get(i).getName());
            }
        }
        //initalizing views
        final Button btnCancel = findViewById(R.id.close_calendar_pop_up);
        final Button btnSubmit = findViewById(R.id.add_recipe_to_calendar_button);
        final Button btnPropose = findViewById(R.id.generaterecipe);
        final EditText inputPeopleNumber = findViewById(R.id.calendar_input_people_number);

        //AutoComplete Recipes list
        final AutoCompleteTextView atvRecipes = findViewById(R.id.calendar_input_selected_recipe);
        atvRecipes.setAdapter(new ArrayAdapter<String>(AddProgrammedRecipeActivity.this, android.R.layout.simple_list_item_1, recipesNamesArrayList));
        atvRecipes.setThreshold(1);
        atvRecipes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recipesNamesArrayList.size() == 0 ||
                            recipesNamesArrayList.indexOf(atvRecipes.getText().toString()) == -1) {
                        atvRecipes.setError("Recette invalide.");
                    };
                }
            }
        });

        //Time picker
        final Spinner spinner = (Spinner) findViewById(R.id.calendar_popup_input_time);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddProgrammedRecipeActivity.this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //DatePicker
        final Calendar myCalendar = Calendar.getInstance();
        final EditText editTextDatePicker= (EditText) findViewById(R.id.calendar_popup_date_picker);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editTextDatePicker, myCalendar);
            }
        };

        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(AddProgrammedRecipeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
        updateLabel(editTextDatePicker, myCalendar);

        //Close
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!atvRecipes.getText().toString().isEmpty() && !inputPeopleNumber.getText().toString().isEmpty()){
                    btnSubmit.setEnabled(false);
                    btnCancel.setEnabled(false);
                    for (int i = 0; i < recipesArrayList.size(); i++)
                    {
                        if (atvRecipes.getText().toString().toLowerCase().equals(recipesArrayList.get(i).getName().toLowerCase()))
                        {
                            recipeToAdd = recipesArrayList.get(i);
                            break;
                        }
                    }
                    int number_people_initial = recipeToAdd.getPeopleNumber();

                    //We need to change amount of ingredients before
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    programmedRecipe = new ProgrammedRecipe(recipeToAdd, sdf.format(myCalendar.getTime()), spinner.getSelectedItem().toString());
                    programmedRecipe.setPeopleNumber(Integer.valueOf(inputPeopleNumber.getText().toString()));

                    ArrayList<Ingredient> ingredientArrayList = programmedRecipe.getIngredientsArrayList();
                    for(int i = 0; i<programmedRecipe.getIngredientsArrayList().size(); i++){
                        Ingredient ingredient = ingredientArrayList.get(i);
                        ingredient.setAmount(ingredient.getAmount() * programmedRecipe.getPeopleNumber()/number_people_initial);
                        ingredientArrayList.set(i, ingredient);
                    }
                    programmedRecipe.setIngredientsArrayList(ingredientArrayList);

                    userData = singleton.getCurrentUserData();

                    ArrayList<Ingredient> stockIngredients = userData.getStockArrayList();
                    ArrayList<Ingredient> shoppingIngredients = userData.getShoppingArrayList();

                    if(shoppingIngredients == null){
                        shoppingIngredients = new ArrayList<Ingredient>();
                    }

                    ingredientArrayList = programmedRecipe.getIngredientsArrayList();
                    for(int i=0; i<ingredientArrayList.size(); i++){
                        if(stockIngredients != null){
                            if(stockIngredients.contains(ingredientArrayList.get(i).getName())) {
                                Ingredient currentIngredient = stockIngredients.get(i);

                                if (currentIngredient.getAmount() < ingredientArrayList.get(i).getAmount()) {
                                    currentIngredient.setAmount(ingredientArrayList.get(i).getAmount() - currentIngredient.getAmount());
                                }
                                shoppingIngredients.add(currentIngredient);
                            } else {
                                shoppingIngredients.add(ingredientArrayList.get(i));
                            }
                        }
                        else{
                            shoppingIngredients.add(ingredientArrayList.get(i));
                        }
                    }

                    if(userData == null){
                        userData = new UserData();
                    }
                    else if(userData.getProgrammedRecipeArrayList() != null){
                        programmedRecipeArrayList = userData.getProgrammedRecipeArrayList();
                        programmedRecipeArrayList.add(programmedRecipe);
                    } else {
                        programmedRecipeArrayList.add(programmedRecipe);
                    }
                    userData.setProgrammedRecipeArrayList(programmedRecipeArrayList);
                    userData.setShoppingArrayList(shoppingIngredients);
                    apiManager.SetUserData(userData);

                    Toast.makeText(AddProgrammedRecipeActivity.this, "Recette : "+ programmedRecipe.getName()+" ajoutée", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            Intent myIntent = new Intent(AddProgrammedRecipeActivity.this, MainActivity.class);
                            startActivity(myIntent);
                        }
                    }, 1000);
                }else {
                    Toast.makeText(AddProgrammedRecipeActivity.this , "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPropose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(AddProgrammedRecipeActivity.this, RecipeProposedActivity.class);
                        startActivity(myIntent);
                    }
                }, 1000);
            }
        });




    }

    private void updateLabel(EditText editTextDatePicker, Calendar myCalendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDatePicker.setText(sdf.format(myCalendar.getTime()));
    }
}
