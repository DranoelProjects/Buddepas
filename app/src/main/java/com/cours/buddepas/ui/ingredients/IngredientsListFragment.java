package com.cours.buddepas.ui.ingredients;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.IngredientAdapter;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;


import java.util.ArrayList;

public class IngredientsListFragment extends Fragment {

    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private IngredientsListViewModel ingredientsListViewModel;
    private String TAG = "IngredientsFragment";

    //UI
    ListView listView;
    TextView textViewLoading;

    //Ingredients
    private boolean loading = true;
    ArrayList<Ingredient> ingredientsArrayList = new ArrayList();
    UserData userData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsListViewModel =
                new ViewModelProvider(this).get(IngredientsListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        textViewLoading = root.findViewById(R.id.ingredient_loading);
        apiManager.GetAllIngredients();
        listView = root.findViewById(R.id.list_view_stock);
        new IngredientsListFragment.LoadingIngredientsListTask().execute();

        //add new ingredient button
        ImageButton addNewIngredientButton = root.findViewById(R.id.add_new_ingredient_button);
        addNewIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddNewIngredientActivity.class);
                startActivity(myIntent);
            }
        });
    }


    class LoadingIngredientsListTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            while(loading){
                loading = singleton.isLoading();
            }
            ingredientsArrayList = singleton.getIngredientsArrayList();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            listView.setAdapter(new IngredientAdapter(getActivity(), ingredientsArrayList));
            textViewLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }

};