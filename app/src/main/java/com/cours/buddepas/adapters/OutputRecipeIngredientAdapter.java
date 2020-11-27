package com.cours.buddepas.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Step;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import java.util.List;

public class OutputRecipeIngredientAdapter extends RecyclerView.Adapter<OutputRecipeIngredientAdapter.MyViewHolder> {
    private List<Ingredient> recipeIngredientsList;

    public void setRecipeIngredientsList(List<Ingredient> recipeIngredientsList){
        this.recipeIngredientsList = recipeIngredientsList;
    }

    @NonNull
    @Override
    public OutputRecipeIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_details_ingredient_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIngredient(recipeIngredientsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(recipeIngredientsList != null){
            return recipeIngredientsList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView outputIngredientAmount;
        private TextView outputIngredientUnit;
        private TextView outputIngredientName;
        private TextView outputIngredientPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            outputIngredientAmount = itemView
                    .findViewById(R.id.text_view_output_ingredient_amount);
            outputIngredientUnit = itemView
                    .findViewById(R.id.text_view_output_ingredient_unit);
            outputIngredientName = itemView
                    .findViewById(R.id.text_view_output_ingredient_name);
            outputIngredientPrice = itemView
                    .findViewById(R.id.text_view_output_ingredient_price
                    );
        }

        public void setIngredient(final Ingredient ingredient, final int position) {
            outputIngredientAmount.setText(String.valueOf(ingredient.getAmount()));
            outputIngredientUnit.setText(ingredient.getUnit());
            outputIngredientName.setText(ingredient.getName());
            outputIngredientPrice.setText(String.valueOf(ingredient.getPrice()));
        }
    }
}
