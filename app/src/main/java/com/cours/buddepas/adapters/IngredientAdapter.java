package com.cours.buddepas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;

import java.util.List;

public class IngredientAdapter extends BaseAdapter {
    private List<Ingredient> ingredientsList;
    private LayoutInflater inflater;

    public IngredientAdapter(Context context, List<Ingredient> ingredientsList){
        this.ingredientsList = ingredientsList;
        this.inflater = LayoutInflater.from(context);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.ingredient_adapter,null);

        //get information about ingredient
        Ingredient currentIngredient=getItem(i);
        String ingredientName = currentIngredient.getName();
        String ingredientKind = currentIngredient.getKind();
        Integer ingredientQuantity = currentIngredient.getAmount();

        //get and change it
        TextView name = view.findViewById(R.id.text_view_ingredient_name);
        TextView kind = view.findViewById(R.id.text_view_ingredient_kind);
        TextView quantity = view.findViewById(R.id.text_view_ingredient_quantity);


        name.setText(ingredientName);
        kind.setText(ingredientKind);
        quantity.setText(Integer.toString(ingredientQuantity));
        return view;
    }
};
