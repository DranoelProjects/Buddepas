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
import com.cours.buddepas.models.Step;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import java.util.List;

public class InputRecipeStepAdapter extends RecyclerView.Adapter<InputRecipeStepAdapter.MyViewHolder> {
    private List<Step> recipeStepsList;

    public void setRecipeStepsList(List<Step> recipeStepsList){
        this.recipeStepsList = recipeStepsList;
    }

    public List<Step> getRecipeStepsList(){
        return recipeStepsList;
    }

    @NonNull
    @Override
    public InputRecipeStepAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_step_adapter, parent, false);
        return new MyViewHolder(view, new DescriptionListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.DescriptionListener.updatePosition(position);
        holder.setStep(recipeStepsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipeStepsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText InputStepDescription;
        TextView recipeStepOrder;
        public DescriptionListener DescriptionListener;

        public MyViewHolder(@NonNull View itemView, DescriptionListener dListener) {
            super(itemView);
            DescriptionListener = dListener;
            InputStepDescription = itemView
                    .findViewById(R.id.input_recipe_step_description);
            recipeStepOrder = itemView
                    .findViewById(R.id.recipe_step_order);
            InputStepDescription.addTextChangedListener(DescriptionListener);
        }

        public void setStep(final Step step, final int position) {
            InputStepDescription.setText(step.getDescription());
            recipeStepOrder.setText(String.valueOf(position+1));

            //Init delete btn
            ImageButton deleteBtn = itemView.findViewById(R.id.delete_recipe_step_button);
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
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

    private class DescriptionListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Step newStep = recipeStepsList.get(position);
            newStep.setDescription(charSequence.toString());
            recipeStepsList.set(position, newStep);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
