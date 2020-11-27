package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private RecipeViewModel recipeViewModel;
    private String TAG = "RecipeFragment";

    //UI
    ListView listView;

    //Recipes
    private boolean loading = true;
    ArrayList<Recipe> recipesArrayList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        init(root);
        return root;
    }

    private void init(View root){
        apiManager.GetAllRecipes();
        listView = root.findViewById(R.id.list_view_recipes);
        new LoadingRecipesListTask().execute();
    }

    class LoadingRecipesListTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            while(loading){
                loading = singleton.isLoading();
            }
            recipesArrayList = singleton.getRecipesArrayList();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            listView.setAdapter(new RecipeAdapter(getActivity(), recipesArrayList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent myIntent = new Intent(getActivity(), RecipeDetailsActivity.class);
                    myIntent.putExtra("RecipeId", recipesArrayList.get(i).getId());
                    startActivity(myIntent);
                }
            });
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }
}