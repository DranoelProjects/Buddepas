package com.cours.buddepas.ui.calendar.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cours.buddepas.R;
import com.cours.buddepas.adapters.OutputRecipeIngredientAdapter;
import com.cours.buddepas.adapters.OutputRecipeStepAdapter;
import com.cours.buddepas.adapters.ProgrammedRecipeAdapter;
import com.cours.buddepas.adapters.RecipeAdapter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.Singleton;
import com.cours.buddepas.ui.calendar.CalendarFragment;
import com.cours.buddepas.ui.recipe.RecipeDetailsActivity;
import com.firebase.ui.auth.data.model.Resource;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DayFragment extends Fragment implements DatePickerListener {
    //Instances
    private Singleton singleton = Singleton.getInstance();
    private String TAG = "DayFragment";

    //UserData
    private boolean loading = true;
    private UserData userData;
    String pickedDate = "";

    //ArrayList
    private ArrayList<ProgrammedRecipe> calendar = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> breakFast = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> lunch = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> afternoonSnack = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> snacks = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> dinner = new ArrayList<>();

    //RecyclerView adapters and layout managers
    //Breakfast
    private ProgrammedRecipeAdapter breakfastAdapter;
    private RecyclerView.LayoutManager breakfastLayoutManager;
    //Lunch
    private ProgrammedRecipeAdapter lunchAdapter;
    private RecyclerView.LayoutManager lunchLayoutManager;
    //Afternoon snack
    private ProgrammedRecipeAdapter afternoonSnackAdapter;
    private RecyclerView.LayoutManager afternoonSnackLayoutManager;
    //Snacks
    private ProgrammedRecipeAdapter snacksAdapter;
    private RecyclerView.LayoutManager snacksLayoutManager;
    //Dinner
    private ProgrammedRecipeAdapter dinnerAdapter;
    private RecyclerView.LayoutManager dinnerLayoutManager;

    //UI
    private androidx.recyclerview.widget.RecyclerView recyclerBreakfast;
    private androidx.recyclerview.widget.RecyclerView recyclerLunch;
    private androidx.recyclerview.widget.RecyclerView recyclerAfternoonSnack;
    private androidx.recyclerview.widget.RecyclerView recyclerSnacks;
    private androidx.recyclerview.widget.RecyclerView recyclerDinner;

    HorizontalPicker picker;

    public DayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day, container, false);

        initUi(root);
        new LoadingUserDataTask().execute();

        return root;
    }

    void initUi(View view){
        Resources resource = getContext().getResources();
        //Picker
        picker = view.findViewById(R.id.datePicker);
        picker
                .setListener(this)
                .setDays(60)
                .setOffset(0)
                .setDateSelectedColor(resource.getColor(R.color.colorPrimary))
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(resource.getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(resource.getColor(R.color.white))
                .setTodayDateTextColor(resource.getColor(R.color.colorPrimary))
                .setUnselectedDayTextColor(resource.getColor(R.color.disable))
                .showTodayButton(false)
                .init();
        picker.setDate(new DateTime());

        //Breakfast
        recyclerBreakfast = view.findViewById(R.id.array_breakfast);
        breakfastLayoutManager = new LinearLayoutManager(getActivity());
        recyclerBreakfast.setLayoutManager(breakfastLayoutManager);
        breakfastAdapter = new ProgrammedRecipeAdapter();
        recyclerBreakfast.setAdapter(breakfastAdapter);
        //Lunch
        recyclerLunch = view.findViewById(R.id.array_lunch);
        lunchLayoutManager = new LinearLayoutManager(getActivity());
        recyclerLunch.setLayoutManager(lunchLayoutManager);
        lunchAdapter = new ProgrammedRecipeAdapter();
        recyclerLunch.setAdapter(lunchAdapter);
        //Afternoon snack
        recyclerAfternoonSnack = view.findViewById(R.id.array_afternoon_snack);
        afternoonSnackLayoutManager = new LinearLayoutManager(getActivity());
        recyclerAfternoonSnack.setLayoutManager(afternoonSnackLayoutManager);
        afternoonSnackAdapter = new ProgrammedRecipeAdapter();
        recyclerAfternoonSnack.setAdapter(afternoonSnackAdapter);
        //Snacks
        recyclerSnacks = view.findViewById(R.id.array_snacks);
        snacksLayoutManager = new LinearLayoutManager(getActivity());
        recyclerSnacks.setLayoutManager(snacksLayoutManager);
        snacksAdapter = new ProgrammedRecipeAdapter();
        recyclerSnacks.setAdapter(snacksAdapter);
        //Dinner
        recyclerDinner = view.findViewById(R.id.array_dinner);
        dinnerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerDinner.setLayoutManager(dinnerLayoutManager);
        dinnerAdapter = new ProgrammedRecipeAdapter();
        recyclerDinner.setAdapter(dinnerAdapter);
    }

    @Override
    public void onDateSelected(@NonNull final DateTime dateSelected) {
        // log it for demo
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pickedDate = sdf.format(dateSelected.toDate());
        UpdateCalendarDayUI();
    }

    public void UpdateCalendarDayUI(){
        userData = singleton.getCurrentUserData();
        if(userData != null){
            calendar = userData.getProgrammedRecipeArrayList();
            if(calendar != null){
                breakFast.clear();
                lunch.clear();
                afternoonSnack.clear();
                snacks.clear();
                dinner.clear();
                for (int i=0;i<calendar.size();i++){
                    ProgrammedRecipe currentProgrammedRecipe = calendar.get(i);
                    //Breakfast
                    if(currentProgrammedRecipe.getDate().equals(pickedDate) && currentProgrammedRecipe.getTime().equals("Petit déjeuner")){
                        breakFast.add(currentProgrammedRecipe);
                    }
                    //Lunch
                    if(currentProgrammedRecipe.getDate().equals(pickedDate) && currentProgrammedRecipe.getTime().equals("Déjeuner")){
                        lunch.add(currentProgrammedRecipe);
                    }
                    //Afternoon snack
                    if(currentProgrammedRecipe.getDate().equals(pickedDate) && currentProgrammedRecipe.getTime().equals("Goûter")){
                        afternoonSnack.add(currentProgrammedRecipe);
                    }
                    //Snacks
                    if(currentProgrammedRecipe.getDate().equals(pickedDate) && currentProgrammedRecipe.getTime().equals("Autres")){
                        snacks.add(currentProgrammedRecipe);
                    }
                    //Dinner
                    if(currentProgrammedRecipe.getDate().equals(pickedDate) && currentProgrammedRecipe.getTime().equals("Dîner")){
                        dinner.add(currentProgrammedRecipe);
                    }
                }
            }
        }
        //Breakfast
        breakfastAdapter.setProgrammedRecipeList(breakFast);
        breakfastAdapter.notifyDataSetChanged();
        //Lunch
        lunchAdapter.setProgrammedRecipeList(lunch);
        lunchAdapter.notifyDataSetChanged();
        //Afternoon snack
        afternoonSnackAdapter.setProgrammedRecipeList(afternoonSnack);
        afternoonSnackAdapter.notifyDataSetChanged();
        //Snacks
        snacksAdapter.setProgrammedRecipeList(snacks);
        snacksAdapter.notifyDataSetChanged();
        //Dinner
        dinnerAdapter.setProgrammedRecipeList(dinner);
        dinnerAdapter.notifyDataSetChanged();
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
                UpdateCalendarDayUI();
            }
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }
}