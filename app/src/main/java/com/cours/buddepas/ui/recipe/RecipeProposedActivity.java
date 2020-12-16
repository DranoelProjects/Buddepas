package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.GenerateRecipe;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.calendar.AddProgrammedRecipeActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecipeProposedActivity extends AppCompatActivity {

    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private RecipeViewModel recipeViewModel;
    private String TAG = "RecipeProposedActivity";
    private ImageButton imageButtonGoBack;
    private String kind= "Plat";
    private String time = "Petit déjeuner";

    //UI
    private ListView listView;

    private ArrayList<Recipe> recipesArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_recipe);
        initUI();
    }

    private void initUI(){

        listView = findViewById(R.id.recipes_proposed);

        listView.setAdapter(new RecipeAdapter(this, recipesArrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Calendar c = Calendar.getInstance();
                ProgrammedRecipe programmedRecipe = new ProgrammedRecipe(recipesArrayList.get(i), sdf.format(c.getTime()), time);
                UserData userData = singleton.getCurrentUserData();
                if(userData == null){
                    userData = new UserData();
                }
                ArrayList<ProgrammedRecipe> programmedRecipeArrayList = new ArrayList<>();
                if(userData.getProgrammedRecipeArrayList() != null){
                    programmedRecipeArrayList = userData.getProgrammedRecipeArrayList();
                    programmedRecipeArrayList.add(programmedRecipe);
                } else {
                    programmedRecipeArrayList.add(programmedRecipe);
                }
                userData.setProgrammedRecipeArrayList(programmedRecipeArrayList);
                apiManager.SetUserData(userData);
                Toast.makeText(RecipeProposedActivity.this, "Recette ajoutée", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(RecipeProposedActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        imageButtonGoBack = findViewById(R.id.go_back_to_calendar);
        imageButtonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Spinner typespinner = findViewById(R.id.generatetype);
        Spinner timespinner = findViewById(R.id.generatetime);

        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(this, R.array.select_type, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(typeadapter);

        ArrayAdapter<CharSequence> timeadapter = ArrayAdapter.createFromResource(this, R.array.time_array, android.R.layout.simple_spinner_item);
        timeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        timespinner.setAdapter(timeadapter);


        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                kind = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                kind = "Plat";
            }
        });

        timespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                time = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                time = "Petit déjeuner";
            }
        });

        Button proposebutton = (Button) findViewById(R.id.actuallygenerate);
        proposebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                singleton.cancelFilter();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Calendar c = Calendar.getInstance();
                Resources r = getResources();
                recipesArrayList = (new GenerateRecipe(sdf.format(c.getTime()),kind)).generate(r);
                Toast.makeText(RecipeProposedActivity.this, "Recipes : "+recipesArrayList.size(), Toast.LENGTH_SHORT).show();
                listView.setAdapter(new RecipeAdapter(RecipeProposedActivity.this, recipesArrayList));
            }
        });


        ImageButton cancel = findViewById(R.id.go_back_to_calendar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
