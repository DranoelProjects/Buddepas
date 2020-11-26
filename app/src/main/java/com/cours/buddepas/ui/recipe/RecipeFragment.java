package com.cours.buddepas.ui.recipe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        /*final TextView textView = root.findViewById(R.id.title_recipe);
        recipeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
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
            Log.d(TAG, "doInBackground");
            while(loading){
                loading = singleton.isLoading();
            }
            recipesArrayList = singleton.getRecipesArrayList();

            for(int i=0; i<recipesArrayList.size();i++){
                Log.d(TAG, "Id: " + recipesArrayList.get(i).getId() + " name: " + recipesArrayList.get(i).getName());
            }
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute");
            listView.setAdapter(new RecipeAdapter(getActivity(), recipesArrayList));
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "onProgressUpdate");
        }
    }
}