package com.cours.buddepas.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Recipe;

import java.util.List;

public class RecipeIngredientAdapter extends BaseAdapter {
    private List<Ingredient> recipeIngredientsList;
    LayoutInflater inflater;
    private String TAG = "RecipeIngredientAdapter";
    private Context context;

    public RecipeIngredientAdapter(Context context, List<Ingredient> recipeIngredientsList){
        this.context = context;
        this.recipeIngredientsList = recipeIngredientsList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() { return recipeIngredientsList.size(); }

    @Override
    public Ingredient getItem(int position) {
        return recipeIngredientsList.get(position);
    }


    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        convertView=null;
        final Ingredient currentIngredient=getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_ingredient_adapter, null);

            //Name
            holder.inputIngredientName = convertView
                    .findViewById(R.id.input_recipe_ingredient_name);
            holder.inputIngredientName.setTag(position);
            holder.inputIngredientName.setText(currentIngredient.getName());

            //Amount
            holder.inputIngredientAmount = convertView
                    .findViewById(R.id.input_recipe_ingredient_amount);
            holder.inputIngredientAmount.setTag(position);
            if(currentIngredient.getAmount() != null){
                holder.inputIngredientAmount.setValue(currentIngredient.getAmount());
            }

            //Unit
            holder.inputIngredientUnit = convertView
                    .findViewById(R.id.input_recipe_ingredient_unit);
            holder.inputIngredientUnit.setTag(position);
            holder.inputIngredientUnit.setText(currentIngredient.getUnit());

            //Price
            holder.inputIngredientPrice = convertView
                    .findViewById(R.id.input_recipe_ingredient_price);
            holder.inputIngredientPrice.setTag(position);
            if(currentIngredient.getPrice() != null) {
                holder.inputIngredientPrice.setValue(currentIngredient.getPrice());
            }

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Name
        int tag_position_name=(Integer) holder.inputIngredientName.getTag();
        holder.inputIngredientName.setId(tag_position_name);

        holder.inputIngredientName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final int position2 = holder.inputIngredientName.getId();
                final EditText caption = (EditText) holder.inputIngredientName;
                if(caption.getText().toString().length()>0){
                    Ingredient newIngredient = currentIngredient;
                    currentIngredient.setName(caption.getText().toString());
                    recipeIngredientsList.set(position2, newIngredient);
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
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
        int tag_position_unit=(Integer) holder.inputIngredientUnit.getTag();
        holder.inputIngredientUnit.setId(tag_position_unit);

        holder.inputIngredientUnit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final int position2 = holder.inputIngredientUnit.getId();
                final EditText caption = (EditText) holder.inputIngredientUnit;
                if(caption.getText().toString().length()>0){
                    Ingredient newIngredient = currentIngredient;
                    currentIngredient.setUnit(caption.getText().toString());
                    recipeIngredientsList.set(position2, newIngredient);
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
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
        ImageButton deleteBtn = convertView.findViewById(R.id.delete_recipe_ingredient_button);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                recipeIngredientsList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        EditText inputIngredientName;
        com.shawnlin.numberpicker.NumberPicker inputIngredientAmount;
        EditText inputIngredientUnit;
        com.shawnlin.numberpicker.NumberPicker inputIngredientPrice;
    }

    public void AddNewIngredient(){
        recipeIngredientsList.add(new Ingredient());
        notifyDataSetChanged();
    }
}
