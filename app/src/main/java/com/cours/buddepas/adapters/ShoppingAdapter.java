package com.cours.buddepas.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.cours.buddepas.ui.shopping.ShoppingListFragment;

import java.util.List;

public class ShoppingAdapter extends BaseAdapter{
    private List<Ingredient> ingredientsList;
    private LayoutInflater inflater;
    private Context context;
    private ApiManager apiManager = ApiManager.getInstance();

    public ShoppingAdapter(Context context, List<Ingredient> ingredientsList){
        this.ingredientsList = ingredientsList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() { return ingredientsList.size(); }

    @Override
    public Ingredient getItem(int position) {
        return ingredientsList.get(position);
    }


    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.shopping_adapter,null);

        //get information about ingredient
        Ingredient currentIngredient=getItem(i);
        final String ingredientName = currentIngredient.getName();
        String ingredientKind = currentIngredient.getKind();
        String ingredientUnit = currentIngredient.getUnit();
        Integer ingredientQuantity = currentIngredient.getAmount();
        Float ingredientPrice = currentIngredient.getPrice();

        //get and change it
        TextView name = view.findViewById(R.id.text_view_ingredient_name);
        TextView kind = view.findViewById(R.id.text_view_ingredient_kind);
        TextView quantity = view.findViewById(R.id.text_view_ingredient_quantity);
        TextView unit = view.findViewById(R.id.text_view_ingredient_unit);
        TextView price = view.findViewById(R.id.text_view_ingredient_price);


        name.setText(ingredientName);
        kind.setText(ingredientKind);
        quantity.setText(Integer.toString(ingredientQuantity));
        unit.setText(ingredientUnit);
        price.setText(Float.toString(ingredientPrice)); 


        //Init delete btn
        ImageButton deleteBtn = view.findViewById(R.id.button_remove_shopping_ingredient);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                apiManager.DeleteIngredientShopping(ingredientName);
                Toast.makeText(context, ingredientName + " : ingrédient supprimé", Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(context, MainActivity.class);
                        context.startActivity(myIntent);
                    }
                }, 200);

            }
        });

        return view;

    }
};
