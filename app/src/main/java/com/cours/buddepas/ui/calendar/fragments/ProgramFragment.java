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
import com.cours.buddepas.adapters.CalendarProgramAdapter;
import com.cours.buddepas.adapters.ProgrammedRecipeAdapter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.UserData;
import com.cours.buddepas.tools.Singleton;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ProgramFragment extends Fragment {
    public ProgramFragment() {
        // Required empty public constructor
    }

    //Instances
    private Singleton singleton = Singleton.getInstance();
    private String TAG = "ProgramFragment";

    //UserData
    private boolean loading = true;
    private UserData userData;

    //ArrayList
    private ArrayList<ProgrammedRecipe> calendar = new ArrayList<>();
    private List<List<ProgrammedRecipe>> calendarFiltered = new ArrayList<>();

    //RecyclerView adapters and layout managers
    private CalendarProgramAdapter calendarAdapter;
    private RecyclerView.LayoutManager calendarLayoutManager;

    //UI
    private androidx.recyclerview.widget.RecyclerView calendarRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_program, container, false);

        initUi(root);
        new LoadingUserDataTask().execute();

        return root;
    }

    void initUi(View view){
        //Breakfast
        calendarRecycler = view.findViewById(R.id.array_programmed_recipe);
        calendarLayoutManager = new LinearLayoutManager(getActivity());
        calendarRecycler.setLayoutManager(calendarLayoutManager);
        calendarAdapter = new CalendarProgramAdapter();
        calendarRecycler.setAdapter(calendarAdapter);
    }


    private void UpdateCalendarUI(){
        userData = singleton.getCurrentUserData();
        if(userData != null){
            calendar = userData.getProgrammedRecipeArrayList();
            if(calendar != null){
                arrangeProgrammedRecipes();
                /*for (int i=0;i<calendar.size();i++){
                    ProgrammedRecipe currentProgrammedRecipe = calendar.get(i);
                }*/
            }
        }
        calendarAdapter.setProgrammedRecipeList(calendarFiltered);
        calendarAdapter.notifyDataSetChanged();
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
                UpdateCalendarUI();
            }
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Integer... values) { }
    }

    private void arrangeProgrammedRecipes(){
        calendarFiltered.clear();
        Collections.sort(calendar, new Comparator<ProgrammedRecipe>(){
            @Override
            public int compare(ProgrammedRecipe recipe, ProgrammedRecipe r1) {
                return recipe.getDate().compareToIgnoreCase(r1.getDate());
            }
        });
        deletePassedRecipe();
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String lastDate = sdf.format(new Date());
        List<ProgrammedRecipe> temp = new ArrayList<>();

        //calendarFiltered
        for (int i=0;i<calendar.size();i++){
            ProgrammedRecipe currentProgrammedRecipe = calendar.get(i);
            if(currentProgrammedRecipe.getDate().equals(lastDate)) {
                //adding only in temp
            }
            else
            {
                calendarFiltered.add(temp);
                temp = new ArrayList<>();
                lastDate = currentProgrammedRecipe.getDate();
            }
            temp.add(currentProgrammedRecipe);
            if(i == calendar.size()-1 && temp.size() > 0){
                calendarFiltered.add(temp);
            }
        }
        for (int i=0;i<calendarFiltered.size();i++) {
            for (int j=0;j<calendarFiltered.get(i).size();j++){
                ProgrammedRecipe currentProgrammedRecipe = calendarFiltered.get(i).get(j);
                Log.d(TAG, "i: " + i + " j: " + j + " " + currentProgrammedRecipe.getDate() + " " + currentProgrammedRecipe.getName());
            }
        }
        Log.d(TAG, "calendar : " + calendar.toString());
        Log.d(TAG, "calendarFiltered : " + calendarFiltered.toString());
    }

    private void deletePassedRecipe() {
        Iterator<ProgrammedRecipe> itr = calendar.iterator();
        while (itr.hasNext()) {
            ProgrammedRecipe recipe = itr.next();
            Date date1 = null;
            Date date2 = new Date();
            try {
                date1 = new SimpleDateFormat("dd-MM-yyyy").parse(recipe.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DateTimeComparator.getDateOnlyInstance().compare(date1, date2) < 0) {
                itr.remove();
            }
        }
    }
}