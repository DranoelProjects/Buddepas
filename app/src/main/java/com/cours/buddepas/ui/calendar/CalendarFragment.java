package com.cours.buddepas.ui.calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.cours.buddepas.MainActivity;
import com.cours.buddepas.R;
import com.cours.buddepas.adapters.CalendarSectionPageAdapter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.ApiManager;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.calendar.fragments.DayFragment;
import com.cours.buddepas.ui.calendar.fragments.ProgramFragment;
import com.cours.buddepas.ui.recipe.AddNewRecipeActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    //Fragments
    View fragment;
    ViewPager viewPager;
    TabLayout tabLayout;

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
        initUI();
        return fragment;
    }

    private void initUI(){
        viewPager = fragment.findViewById(R.id.calendar_view_pager);
        tabLayout = fragment.findViewById(R.id.calendar_tab_layout);

        //program recipe btn
        programRecipeButton = fragment.findViewById(R.id.program_recipe_button);
        programRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), AddProgrammedRecipeActivity.class);
                startActivity(myIntent);
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
