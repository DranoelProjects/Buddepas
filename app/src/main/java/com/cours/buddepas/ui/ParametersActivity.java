package com.cours.buddepas.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;

public class ParametersActivity extends AppCompatActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private boolean loading = true;

    //UI
    private EditText editTextUsername;
    private EditText editTextBudget;
    private Button buttonSubmitParameters;
    private ImageButton imageButtonGoBack;
    private TextView textViewLoading;

    //UserData
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        initUI();
        singleton.setLoading(true);
        apiManager.GetUserData();
        new LoadingUserDataTask().execute();
    }

    private void initUI(){
        editTextUsername = findViewById(R.id.input_username);
        editTextBudget = findViewById(R.id.input_budget);
        textViewLoading = findViewById(R.id.loading_user_data);
        imageButtonGoBack = findViewById(R.id.go_back_to_main_activity);
        buttonSubmitParameters = findViewById(R.id.submit_parameters);

        buttonSubmitParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiManager.SetUserData(new UserData(editTextUsername.getText().toString(), Integer.parseInt(editTextBudget.getText().toString())));
                Toast.makeText(ParametersActivity.this, "Modification effectuée", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class LoadingUserDataTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            while(loading){
                loading = singleton.isLoading();
            }
            userData = singleton.getCurrentUserData();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            if(userData != null){
                if(userData.getBudget() != 0){
                    editTextBudget.setText(String.valueOf(userData.getBudget()));
                }
                if(userData.getUsername() != null){
                    editTextUsername.setText(userData.getUsername());
                }
            }
            textViewLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }
}