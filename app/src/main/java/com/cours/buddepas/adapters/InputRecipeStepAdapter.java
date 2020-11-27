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
import com.cours.buddepas.models.Step;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;

import java.util.ArrayList;
import java.util.List;

public class InputRecipeStepAdapter extends RecyclerView.Adapter<InputRecipeStepAdapter.MyViewHolder> {
    //Instances
    private List<Step> recipeStepsList;
    private String TAG = "InputRecipeStepAdapter";

    public void setRecipeStepsList(List<Step> recipeStepsList){
        this.recipeStepsList = recipeStepsList;
    }
    @NonNull
    @Override
    public InputRecipeStepAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_step_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIngredient(recipeStepsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipeStepsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText inputStepDescription;
        TextView recipeStepOrder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inputStepDescription = itemView
                    .findViewById(R.id.input_recipe_step_description);
            recipeStepOrder = itemView
                    .findViewById(R.id.recipe_step_order);
        }

        public void setIngredient(final Step step, final int position) {
            inputStepDescription.setText(step.getDescription());
            recipeStepOrder.setText(String.valueOf(position+1));

            inputStepDescription.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    final EditText caption = (EditText) inputStepDescription;
                    if(caption.getText().toString().length()>0){
                        Step newStep = step;
                        newStep.setDescription(caption.getText().toString());
                        recipeStepsList.set(position, newStep);

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

            //Init delete btn
            ImageButton deleteBtn = itemView.findViewById(R.id.delete_recipe_step_button);
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    recipeStepsList.remove(position);
                    AddNewRecipeActivity.RemoveStep(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, recipeStepsList.size());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void AddNewStep(){
        recipeStepsList.add(new Step());
        notifyDataSetChanged();
    }
}
