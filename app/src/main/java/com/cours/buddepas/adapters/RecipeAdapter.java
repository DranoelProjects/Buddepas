package com.cours.buddepas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cours.buddepas.R;
import com.cours.buddepas.models.Recipe;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {
    private List<Recipe> recipesList;
    private LayoutInflater inflater;
    private String TAG = "RecipeAdapter";

    public RecipeAdapter(Context context, List<Recipe> recipesList){
        this.recipesList = recipesList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() { return recipesList.size(); }

    @Override
    public Recipe getItem(int position) {
        return recipesList.get(position);
    }


    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.recipe_adapter,null);

        //get information about recipe
        Recipe currentRecipe=getItem(i);
        String recipeName = currentRecipe.getName();
        String recipeKind = currentRecipe.getKind();

        //get and change it
        TextView name = view.findViewById(R.id.text_view_recipe_name);
        TextView kind = view.findViewById(R.id.text_view_recipe_kind);

        name.setText(recipeName);
        kind.setText(recipeKind);
        return view;
    }
}
