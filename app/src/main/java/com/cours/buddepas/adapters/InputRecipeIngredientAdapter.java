package com.cours.buddepas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;

import java.util.ArrayList;
import java.util.List;

public class InputRecipeIngredientAdapter extends RecyclerView.Adapter<InputRecipeIngredientAdapter.MyViewHolder> {
    //Instances
    private List<Ingredient> recipeIngredientsList;
    private String TAG = "InputRecipeIngredientAdapter";

    public void setRecipeIngredientsList(List<Ingredient> recipeIngredientsList){
        this.recipeIngredientsList = recipeIngredientsList;
    }
    @NonNull
    @Override
    public InputRecipeIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredient_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIngredient(recipeIngredientsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipeIngredientsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText inputIngredientName;
        com.shawnlin.numberpicker.NumberPicker inputIngredientAmount;
        EditText inputIngredientUnit;
        com.shawnlin.numberpicker.NumberPicker inputIngredientPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //Name
            inputIngredientName = itemView
                    .findViewById(R.id.input_recipe_ingredient_name);

            //Amount
            inputIngredientAmount = itemView
                    .findViewById(R.id.input_recipe_ingredient_amount);

            //Unit
            inputIngredientUnit = itemView
                    .findViewById(R.id.input_recipe_ingredient_unit);

            //Price
            inputIngredientPrice = itemView
                    .findViewById(R.id.input_recipe_ingredient_price);
        }

        public void setIngredient(final Ingredient ingredient, final int position) {
            inputIngredientName.setText(ingredient.getName());
            if (ingredient.getAmount() != null) {
                inputIngredientAmount.setValue(ingredient.getAmount());
            }
            inputIngredientUnit.setText(ingredient.getUnit());
            if (ingredient.getPrice() != null) {
                inputIngredientPrice.setValue(ingredient.getPrice());
            }

            //Name
            inputIngredientName.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    final EditText caption = (EditText) inputIngredientName;
                    if(caption.getText().toString().length()>0){
                        Ingredient newIngredient = ingredient;
                        newIngredient.setName(caption.getText().toString());
                        recipeIngredientsList.set(position, newIngredient);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

            });

            //Amount TO DO

            //Name
            inputIngredientUnit.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    final EditText caption = (EditText) inputIngredientUnit;
                    if(caption.getText().toString().length()>0){
                        Ingredient newIngredient = ingredient;
                        newIngredient.setUnit(caption.getText().toString());
                        recipeIngredientsList.set(position, newIngredient);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

            });

            //Price TO DO

            //Init delete btn
            ImageButton deleteBtn = itemView.findViewById(R.id.delete_recipe_ingredient_button);
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    recipeIngredientsList.remove(position);
                    AddNewRecipeActivity.RemoveIngredient(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, recipeIngredientsList.size());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void AddNewIngredient(){
        recipeIngredientsList.add(new Ingredient());
        notifyDataSetChanged();
    }
}
