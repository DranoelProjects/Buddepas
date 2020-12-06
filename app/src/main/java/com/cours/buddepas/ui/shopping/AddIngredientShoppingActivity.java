package com.cours.buddepas.ui.shopping;

import android.widget.Button;
import android.widget.EditText;

import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;

public class AddIngredientShoppingActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();

    //UI
    //UI
    private EditText ingredient_quantity;
    private EditText ingredient_unit;
    private EditText ingredient_price;
    private EditText ingredient_name;
    private Button cancel;
    private Button submitRecipe;
}
