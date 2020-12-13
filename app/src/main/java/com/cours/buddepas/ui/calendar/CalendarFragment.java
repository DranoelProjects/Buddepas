package com.cours.buddepas.ui.calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.adapters.CalendarSectionPageAdapter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.ParametersActivity;
import com.cours.buddepas.ui.calendar.fragments.DayFragment;
import com.cours.buddepas.ui.calendar.fragments.ProgramFragment;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CalendarFragment extends Fragment {
    //Instances
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private CalendarViewModel calendarViewModel;
    private String TAG = "CalendarFragment";

    //UserData
    private boolean loading = true;
    private UserData userData;
    private ArrayList<ProgrammedRecipe> programmedRecipeArrayList = new ArrayList<>();

    //Fragments
    View fragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    //PopUp
    ArrayList<Recipe> recipesArrayList = new ArrayList<>();
    ArrayList<String> recipesNamesArrayList = new ArrayList<>();
    Recipe recipeToAdd;
    ProgrammedRecipe programmedRecipe;

    //UI
    private TextView textViewLoading;
    private ImageButton programRecipeButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        fragment = inflater.inflate(R.layout.fragment_calendar, container, false);
        //Because this fragment is the first to be load, loading data in Singleton
        apiManager.GetUserData();
        apiManager.GetAllRecipes();
        new LoadingUserDataTask().execute();

        textViewLoading = fragment.findViewById(R.id.calendar_loading_user_data);
        initPopup();
        return fragment;
    }

    private void initPopup(){
        viewPager = fragment.findViewById(R.id.calendar_view_pager);
        tabLayout = fragment.findViewById(R.id.calendar_tab_layout);

        //program recipe btn
        programRecipeButton = fragment.findViewById(R.id.program_recipe_button);
        programRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_program_recipe, null);

                recipesArrayList = singleton.getRecipesArrayList();
                recipesNamesArrayList.clear();
                if(recipesArrayList != null){
                    for(int i=0;i<recipesArrayList.size();i++){
                        recipesNamesArrayList.add(recipesArrayList.get(i).getName());
                    }
                }
                //initalizing views in popup
                final Button btnCancel = popupView.findViewById(R.id.close_calendar_pop_up);
                final Button btnSubmit = popupView.findViewById(R.id.add_recipe_to_calendar_button);
                final EditText inputPeopleNumber = popupView.findViewById(R.id.calendar_input_people_number);

                //AutoComplete Recipes list
                final AutoCompleteTextView atvRecipes = popupView.findViewById(R.id.calendar_input_selected_recipe);
                atvRecipes.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recipesNamesArrayList));
                atvRecipes.setThreshold(1);
                atvRecipes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if (recipesNamesArrayList.size() == 0 ||
                                    recipesNamesArrayList.indexOf(atvRecipes.getText().toString()) == -1) {
                                atvRecipes.setError("Recette invalide.");
                            };
                        }
                    }
                });

                //Time picker
                final Spinner spinner = (Spinner) popupView.findViewById(R.id.calendar_popup_input_time);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.time_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                //DatePicker
                final Calendar myCalendar = Calendar.getInstance();
                final EditText editTextDatePicker= (EditText) popupView.findViewById(R.id.calendar_popup_date_picker);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(editTextDatePicker, myCalendar);
                    }
                };
                editTextDatePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                updateLabel(editTextDatePicker, myCalendar);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.setOutsideTouchable(false);
                // show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //Close the pop up
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSubmit.setEnabled(false);
                        btnCancel.setEnabled(false);
                        for (int i = 0; i < recipesArrayList.size(); i++)
                        {
                            if (atvRecipes.getText().toString().toLowerCase().equals(recipesArrayList.get(i).getName().toLowerCase()))
                            {
                                recipeToAdd = recipesArrayList.get(i);
                                break;
                            }
                        }
                        recipeToAdd.setPeopleNumber(Integer.valueOf(inputPeopleNumber.getText().toString()));

                        //We need to change amount of ingredients before
                        String myFormat = "dd-MM-yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        programmedRecipe = new ProgrammedRecipe(recipeToAdd, sdf.format(myCalendar.getTime()), spinner.getSelectedItem().toString());
                        userData = singleton.getCurrentUserData();

                        if(userData.getProgrammedRecipeArrayList() != null){
                            programmedRecipeArrayList = userData.getProgrammedRecipeArrayList();
                            programmedRecipeArrayList.add(programmedRecipe);
                        } else {
                            programmedRecipeArrayList.add(programmedRecipe);
                        }
                        userData.setProgrammedRecipeArrayList(programmedRecipeArrayList);
                        apiManager.SetUserData(userData);
                        Toast.makeText(getActivity(), "Recette : "+ programmedRecipe.getName()+" ajoutée", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                                startActivity(myIntent);
                            }
                        }, 1000);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        CalendarSectionPageAdapter adapter = new CalendarSectionPageAdapter(getChildFragmentManager(), 1);
        adapter.addFragment(new DayFragment(), "Jour");
        adapter.addFragment(new ProgramFragment(), "Programme");

        viewPager.setAdapter(adapter);
    }

    private void updateLabel(EditText editTextDatePicker, Calendar myCalendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDatePicker.setText(sdf.format(myCalendar.getTime()));
    }

    class LoadingUserDataTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            while(loading){
                loading = singleton.isLoadingUserData();
            }
            userData = singleton.getCurrentUserData();
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            if(userData != null){
            }
            textViewLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }
}
