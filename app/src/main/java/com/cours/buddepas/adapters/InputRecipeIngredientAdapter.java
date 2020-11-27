package com.cours.buddepas.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import java.util.List;

public class InputRecipeIngredientAdapter extends RecyclerView.Adapter<InputRecipeIngredientAdapter.MyViewHolder> {
    private List<Ingredient> recipeIngredientsList;

    public void setRecipeIngredientsList(List<Ingredient> recipeIngredientsList){
        this.recipeIngredientsList = recipeIngredientsList;
    }

    public List<Ingredient> getRecipeIngredientsList(){
        return recipeIngredientsList;
    }

    @NonNull
    @Override
    public InputRecipeIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_ingredient_adapter, parent, false);
        return new MyViewHolder(view, new NameListener(), new UnitListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.NameListener.UpdatePosition(position);
        holder.UnitListener.UpdatePosition(position);
        holder.pos = position;
        holder.setIngredient(recipeIngredientsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipeIngredientsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText InputIngredientName;
        com.shawnlin.numberpicker.NumberPicker inputIngredientAmount;
        public EditText InputIngredientUnit;
        com.shawnlin.numberpicker.NumberPicker inputIngredientPrice;
        public NameListener NameListener;
        public UnitListener UnitListener;
        public int pos;

        public MyViewHolder(@NonNull View itemView, NameListener nListener, UnitListener uListener) {
            super(itemView);
            this.NameListener = nListener;
            this.UnitListener = uListener;

            //Name
            InputIngredientName = itemView
                    .findViewById(R.id.input_recipe_ingredient_name);
            InputIngredientName.addTextChangedListener(NameListener);

            //Amount
            inputIngredientAmount = itemView
                    .findViewById(R.id.input_recipe_ingredient_amount);
            inputIngredientAmount.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                    Ingredient newIngredient = recipeIngredientsList.get(pos);
                    newIngredient.setAmount(picker.getValue());
                    recipeIngredientsList.set(pos, newIngredient);
                }
            });

            //Unit
            InputIngredientUnit = itemView
                    .findViewById(R.id.input_recipe_ingredient_unit);
            InputIngredientUnit.addTextChangedListener(UnitListener);

            //Price
            inputIngredientPrice = itemView
                    .findViewById(R.id.input_recipe_ingredient_price);
            inputIngredientPrice.setOnValueChangedListener(new com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(com.shawnlin.numberpicker.NumberPicker picker, int oldVal, int newVal) {
                    Ingredient newIngredient = recipeIngredientsList.get(pos);
                    newIngredient.setPrice(picker.getValue());
                    recipeIngredientsList.set(pos, newIngredient);
                }
            });
        }

        public void setIngredient(final Ingredient ingredient, final int position) {
            InputIngredientName.setText(ingredient.getName());
            if (ingredient.getAmount() != null) {
                inputIngredientAmount.setValue(ingredient.getAmount());
            }
            InputIngredientUnit.setText(ingredient.getUnit());
            if (ingredient.getPrice() != null) {
                inputIngredientPrice.setValue(ingredient.getPrice());
            }

            //Init delete btn
            ImageButton deleteBtn = itemView.findViewById(R.id.delete_recipe_ingredient_button);
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
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
        recipeIngredientsList.add(new Ingredient("",2,"kg",2));
        notifyDataSetChanged();
    }

    private class NameListener implements TextWatcher {
        private int position;

        public void UpdatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Ingredient newIngredient = recipeIngredientsList.get(position);
            newIngredient.setName(charSequence.toString());
            recipeIngredientsList.set(position, newIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class UnitListener implements TextWatcher {
        private int position;

        public void UpdatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Ingredient newIngredient = recipeIngredientsList.get(position);
            newIngredient.setUnit(charSequence.toString());
            recipeIngredientsList.set(position, newIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}