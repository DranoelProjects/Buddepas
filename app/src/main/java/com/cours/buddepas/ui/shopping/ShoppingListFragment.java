package com.cours.buddepas.ui.shopping;

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
import com.cours.buddepas.adapters.ShoppingAdapter;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;


import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    //Instances
    private static ApiManager apiManager = ApiManager.getInstance();
    private static Singleton singleton = Singleton.getInstance();
    private ShoppingListViewModel shoppingListViewModel;
    private String TAG = "ShoppingFragment";

    //UI
    ListView listView;
    TextView textViewLoading;

    //Ingredients
    private boolean loading = true;
    static ArrayList<Ingredient> ingredientsArrayList = new ArrayList();
    static ShoppingAdapter shoppingAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        textViewLoading = root.findViewById(R.id.ingredient_loading);
        listView = root.findViewById(R.id.list_view_shopping);
        new ShoppingListFragment.LoadingIngredientsListTask().execute();

        //add new ingredient button
        ImageButton addIngredientShoppingButton = root.findViewById(R.id.add_new_ingredient_shopping_button);
        addIngredientShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddIngredientShoppingActivity.class);
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
            ingredientsArrayList = singleton.getShoppingArrayList();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            if(ingredientsArrayList != null) {
                shoppingAdapter = new ShoppingAdapter(getActivity(), ingredientsArrayList);
                listView.setAdapter(shoppingAdapter);
            }
            textViewLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }

    public static void RemoveIngredient(int position){
        ingredientsArrayList.remove(position);
        shoppingAdapter.notifyDataSetChanged();
        UserData userData = singleton.getCurrentUserData();
        userData.setShoppingArrayList(ingredientsArrayList);
        apiManager.SetUserData(userData);
    }
};