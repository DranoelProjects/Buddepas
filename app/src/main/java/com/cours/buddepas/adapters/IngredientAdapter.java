package com.cours.buddepas.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.ui.ingredients.IngredientsListFragment;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.google.android.gms.common.api.Api;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {
    private List<Ingredient> ingredientsList;
    private LayoutInflater inflater;
    private ApiManager apiManager = ApiManager.getInstance();
    private Context context;

    public IngredientAdapter(Context context, List<Ingredient> ingredientsList){
        this.ingredientsList = ingredientsList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() { return ingredientsList.size(); }

    @Override
    public Ingredient getItem(int position) {
        return ingredientsList.get(position);
    }


    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.ingredient_adapter,null);

        //get information about ingredient
        Ingredient currentIngredient=getItem(i);
        final String ingredientName = currentIngredient.getName();
        Integer ingredientQuantity = currentIngredient.getAmount();
        String ingredientUnit = currentIngredient.getUnit();

        //get and change it
        TextView name = view.findViewById(R.id.text_view_ingredient_name);
        TextView quantity = view.findViewById(R.id.text_view_ingredient_quantity);
        TextView unit = view.findViewById(R.id.text_view_ingredient_unit);


        name.setText(ingredientName);
        quantity.setText(Integer.toString(ingredientQuantity));
        unit.setText(ingredientUnit);

        //Init delete btn
        ImageButton deleteBtn = view.findViewById(R.id.button_remove_stock_ingredient);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                IngredientsListFragment.RemoveIngredient(i);
            }
        });

        return view;
    }
};
