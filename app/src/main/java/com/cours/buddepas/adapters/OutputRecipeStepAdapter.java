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

public class OutputRecipeStepAdapter extends RecyclerView.Adapter<OutputRecipeStepAdapter.MyViewHolder> {
    private List<Step> recipeStepsList;

    public void setRecipeStepsList(List<Step> recipeStepsList){
        this.recipeStepsList = recipeStepsList;
    }

    @NonNull
    @Override
    public OutputRecipeStepAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_details_step_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setStep(recipeStepsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(recipeStepsList != null){
            return recipeStepsList.size();
        } else {
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView outputStepOrder;
        private TextView outputStepDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            outputStepDescription = itemView
                    .findViewById(R.id.text_view_output_step_description);
            outputStepOrder = itemView
                    .findViewById(R.id.text_view_output_step_order);
        }

        public void setStep(final Step step, final int position) {
            outputStepDescription.setText(step.getDescription());
            outputStepOrder.setText(String.valueOf(position+1));
        }
    }
}
