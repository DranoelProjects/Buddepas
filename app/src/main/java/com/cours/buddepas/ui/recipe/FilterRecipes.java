package com.cours.buddepas.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Filter;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;

import java.util.ArrayList;

public class FilterRecipes extends AppCompatActivity {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private Button cancel;
    private Button applyfilters;
    private EditText filtername;
    private EditText filterauthor;
    private String filtertype;
    private String filterkind;
    private EditText filtertime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_fragment);
       initUI();
    }

    private void initUI(){
        filtername = findViewById(R.id.filtername);
        filterauthor = findViewById(R.id.filterauthor);
        filtertype = "";
        filterkind = "";
        //findViewById(R.id.recipe_type);
        filtertime = findViewById(R.id.filtertime);
        Spinner kind = findViewById(R.id.filterkind);
        Spinner type = findViewById(R.id.filtertype);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> kindadapter = ArrayAdapter.createFromResource(this, R.array.type_recette, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        kindadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        kind.setAdapter(kindadapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(this, R.array.apport_recette, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(kindadapter);


        kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                filterkind = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                filterkind = "";
            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                filtertype = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                filtertype = "";
            }
        });


        cancel = (Button) findViewById(R.id.filtercancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(FilterRecipes.this, "Filtre non appliqué "+filterkind, Toast.LENGTH_SHORT).show();

                singleton.setFilter(new Filter());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(FilterRecipes.this, MainActivity.class);
                        startActivity(myIntent);
                    }
                }, 2000);
            }
        });

        applyfilters = (Button) findViewById(R.id.applyfilter);
        applyfilters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(FilterRecipes.this, "Filtre appliqué " +filterkind, Toast.LENGTH_SHORT).show();

                applyFilters();
            }
        });
    }

    public void applyFilters(){
        int time;
        if (filtertime.getText().toString().equals(""))
        {
            time = -1;
        }
        else
        {
            time = Integer.valueOf(filtertime.getText().toString());
        }
        Filter newfilter = new Filter(
                filtername.getText().toString(),
                filterauthor.getText().toString(),
                filterkind,
                filtertype,
                time
        );
        singleton.setFilter(newfilter);
        ///Toast.makeText(FilterRecipes.this, "Filtre appliqué " +filterkind, Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(FilterRecipes.this, MainActivity.class);
                startActivity(myIntent);
            }
        }, 2000);
    }
}
