package com.cours.buddepas.ui.ingredients;

import android.widget.Button;
import android.widget.EditText;

import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;

public class AddNewIngredientActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();

    //UI
    private EditText ingredient_quantity;
    private EditText ingredient_unit;
    private EditText ingredient_name;
    private Button cancel;
    private Button submitRecipe;
}
