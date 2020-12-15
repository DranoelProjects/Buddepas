package com.cours.buddepas.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cours.buddepas.R;
import com.cours.buddepas.models.ProgrammedRecipe;
import java.util.List;

public class ProgrammedRecipeAdapter extends RecyclerView.Adapter<ProgrammedRecipeAdapter.MyViewHolder> {
    private List<ProgrammedRecipe> programmedRecipeList;

    public void setProgrammedRecipeList(List<ProgrammedRecipe> programmedRecipeList){
        this.programmedRecipeList = programmedRecipeList;
    }

    @NonNull
    @Override
    public ProgrammedRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setRecipe(programmedRecipeList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(programmedRecipeList != null){
            return programmedRecipeList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView kind;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_view_recipe_name);
            kind = itemView.findViewById(R.id.text_view_recipe_kind);
        }

        public void setRecipe(final ProgrammedRecipe programmedRecipe, final int position) {
            name.setText(programmedRecipe.getName());
            kind.setText(programmedRecipe.getKind());
        }
    }
}
