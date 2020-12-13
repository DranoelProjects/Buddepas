package com.cours.buddepas.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

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
        return new MyViewHolder(view, new NameListener(), new UnitListener(), new AmountListener(), new PriceListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.NameListener.UpdatePosition(position);
        holder.UnitListener.UpdatePosition(position);
        holder.PriceListener.UpdatePosition(position);
        holder.AmountListener.UpdatePosition(position);
        holder.pos = position;
        holder.setIngredient(recipeIngredientsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return recipeIngredientsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText InputIngredientName;
        public EditText InputIngredientAmount;
        public EditText InputIngredientPrice;
        public EditText InputIngredientUnit;
        public Spinner InputIngredientKind;
        public NameListener NameListener;
        public UnitListener UnitListener;
        public AmountListener AmountListener;
        public PriceListener PriceListener;
        public int pos;
        private String type;

        public MyViewHolder(@NonNull View itemView, NameListener nListener, UnitListener uListener, AmountListener aListener, PriceListener pListener) {
            super(itemView);
            this.NameListener = nListener;
            this.UnitListener = uListener;
            this.AmountListener = aListener;
            this.PriceListener = pListener;
            this.InputIngredientKind = itemView.findViewById(R.id.input_recipe_ingredient_kind);

            //Kind
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> kindadapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.select_apport, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            kindadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            InputIngredientKind.setAdapter(kindadapter);

            InputIngredientKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int p, long id) {
                    type = (String) parent.getItemAtPosition(p);
                    Ingredient newIngredient = recipeIngredientsList.get(pos);
                    newIngredient.setKind(type);
                    recipeIngredientsList.set(pos, newIngredient);

                }

                public void onNothingSelected(AdapterView<?> parent) {
                    type = "Féculent";
                    Ingredient newIngredient = recipeIngredientsList.get(pos);
                    newIngredient.setKind(type);
                    recipeIngredientsList.set(pos, newIngredient);
                }
            });

            //Name
            InputIngredientName = itemView
                    .findViewById(R.id.input_recipe_ingredient_name);
            InputIngredientName.addTextChangedListener(NameListener);

            //Amount
            InputIngredientAmount = itemView
                    .findViewById(R.id.input_recipe_ingredient_amount);
            InputIngredientAmount.addTextChangedListener(AmountListener);


            //Unit
            InputIngredientUnit = itemView
                    .findViewById(R.id.input_recipe_ingredient_unit);
            InputIngredientUnit.addTextChangedListener(UnitListener);

            //Price
            InputIngredientPrice = itemView
                    .findViewById(R.id.input_recipe_ingredient_price);
            InputIngredientPrice.addTextChangedListener(PriceListener);

        }

        public void setIngredient(final Ingredient ingredient, final int position) {
            InputIngredientName.setText(ingredient.getName());
            InputIngredientAmount.setText(String.valueOf(ingredient.getAmount()));
            InputIngredientUnit.setText(ingredient.getUnit());
            InputIngredientPrice.setText(String.valueOf(ingredient.getPrice()));

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
        recipeIngredientsList.add(new Ingredient("Féculent","",2,"kg",2));
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

    private class PriceListener implements TextWatcher {
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
            if(charSequence != null && !charSequence.toString().isEmpty()){
                newIngredient.setPrice(Integer.valueOf(charSequence.toString()));
            }
            recipeIngredientsList.set(position, newIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class AmountListener implements TextWatcher {
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
            if(charSequence != null && !charSequence.toString().isEmpty()){
                newIngredient.setAmount(Integer.valueOf(charSequence.toString()));
            }
            recipeIngredientsList.set(position, newIngredient);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
