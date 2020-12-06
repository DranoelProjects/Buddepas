package com.cours.buddepas.ui.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cours.buddepas.R;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.cours.buddepas.ui.recipe.RecipeFragment;
import com.cours.buddepas.ui.recipe.RecipeViewModel;

public class IngredientsListFragment extends Fragment {

    private IngredientsListViewModel ingredientsListViewModel;

    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private RecipeViewModel recipeViewModel;
    private String TAG = "IngredientsFragment";

    //UI
    ListView listView;
    TextView textViewLoading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsListViewModel =
                new ViewModelProvider(this).get(IngredientsListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
        final TextView textView = root.findViewById(R.id.title_ingredients_list);
        ingredientsListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void init(View root) {
        //textViewLoading = root.findViewById(R.id.recipe_loading);
        //apiManager.GetAllRecipes();
        //listView = root.findViewById(R.id.list_view_recipes);
        //new RecipeFragment.LoadingRecipesListTask().execute();

        //add new ingredient button
        ImageButton addNewIngredientButton = root.findViewById(R.id.add_new_ingredient_button);
        addNewIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddNewRecipeActivity.class);
                startActivity(myIntent);
            }
        });
    }

};