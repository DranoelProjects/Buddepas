package com.cours.buddepas.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cours.buddepas.R;
import com.cours.buddepas.models.ProgrammedRecipe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarProgramAdapter extends RecyclerView.Adapter<CalendarProgramAdapter.MyViewHolder> {
    //All programmed recipe
    private List<List<ProgrammedRecipe>> calendarFiltered = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> breakFast = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> lunch = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> afternoonSnack = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> snacks = new ArrayList<>();
    private ArrayList<ProgrammedRecipe> dinner = new ArrayList<>();

    public void setProgrammedRecipeList(List<List<ProgrammedRecipe>> calendarFiltered){
        this.calendarFiltered = calendarFiltered;
    }

    @NonNull
    @Override
    public CalendarProgramAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.programmed_recipe_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setRecipesList(calendarFiltered.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(calendarFiltered != null){
            return calendarFiltered.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView titleBreakfast;
        private TextView titleLunch;
        private TextView titleAfternoonSnack;
        private TextView titleDinner;
        private TextView titleSnacks;
        private androidx.recyclerview.widget.RecyclerView recyclerBreakfast;
        private androidx.recyclerview.widget.RecyclerView recyclerLunch;
        private androidx.recyclerview.widget.RecyclerView recyclerAfternoonSnack;
        private androidx.recyclerview.widget.RecyclerView recyclerSnacks;
        private androidx.recyclerview.widget.RecyclerView recyclerDinner;

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.programmed_recipe_date);
            titleBreakfast = itemView.findViewById(R.id.programmed_recipe_breakfast);
            titleLunch = itemView.findViewById(R.id.programmed_recipe_lunch);
            titleAfternoonSnack = itemView.findViewById(R.id.programmed_recipe_afternoon_snack);
            titleDinner = itemView.findViewById(R.id.programmed_recipe_dinner);
            titleSnacks = itemView.findViewById(R.id.programmed_recipe_snacks);

            //Breakfast
            recyclerBreakfast = itemView.findViewById(R.id.programmed_recipe_array_breakfast);
            breakfastLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerBreakfast.setLayoutManager(breakfastLayoutManager);
            breakfastAdapter = new ProgrammedRecipeAdapter();
            recyclerBreakfast.setAdapter(breakfastAdapter);
            //Lunch
            recyclerLunch = itemView.findViewById(R.id.programmed_recipe_array_lunch);
            lunchLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerLunch.setLayoutManager(lunchLayoutManager);
            lunchAdapter = new ProgrammedRecipeAdapter();
            recyclerLunch.setAdapter(lunchAdapter);
            //Afternoon snack
            recyclerAfternoonSnack = itemView.findViewById(R.id.programmed_recipe_array_afternoon_snack);
            afternoonSnackLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerAfternoonSnack.setLayoutManager(afternoonSnackLayoutManager);
            afternoonSnackAdapter = new ProgrammedRecipeAdapter();
            recyclerAfternoonSnack.setAdapter(afternoonSnackAdapter);
            //Dinner
            recyclerDinner = itemView.findViewById(R.id.programmed_recipe_array_dinner);
            dinnerLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerDinner.setLayoutManager(dinnerLayoutManager);
            dinnerAdapter = new ProgrammedRecipeAdapter();
            recyclerDinner.setAdapter(dinnerAdapter);
            //Snacks
            recyclerSnacks = itemView.findViewById(R.id.programmed_recipe_array_snacks);
            snacksLayoutManager = new LinearLayoutManager(itemView.getContext());
            recyclerSnacks.setLayoutManager(snacksLayoutManager);
            snacksAdapter = new ProgrammedRecipeAdapter();
            recyclerSnacks.setAdapter(snacksAdapter);
        }

        public void setRecipesList(final List<ProgrammedRecipe> dayRecipesList, final int position) {
            //Today date
            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String todayDate = sdf.format(new Date());

            //Tomorrow date
            Calendar tomorrow =new GregorianCalendar();
            tomorrow.add(Calendar.DATE, 1);
            String tomorrowDate = sdf.format(tomorrow.getTime());

            if(dayRecipesList.size() !=0){
                if (dayRecipesList.get(0).getDate().equals(todayDate)){
                    date.setText("Aujourd'hui");
                } else if (dayRecipesList.get(0).getDate().equals(tomorrowDate)) {
                    date.setText("Demain");
                } else{
                    date.setText(dayRecipesList.get(0).getDate());
                }
            } else {
                date.setVisibility(View.GONE);
            }

            breakFast.clear();
            lunch.clear();
            afternoonSnack.clear();
            snacks.clear();
            dinner.clear();
            for (int i=0;i<dayRecipesList.size();i++){
                ProgrammedRecipe currentProgrammedRecipe = dayRecipesList.get(i);
                //Breakfast
                if(currentProgrammedRecipe.getTime().equals("Petit déjeuner")){
                    breakFast.add(currentProgrammedRecipe);
                }
                //Lunch
                if(currentProgrammedRecipe.getTime().equals("Déjeuner")){
                    lunch.add(currentProgrammedRecipe);
                }
                //Afternoon snack
                if(currentProgrammedRecipe.getTime().equals("Goûter")){
                    afternoonSnack.add(currentProgrammedRecipe);
                }
                //Snacks
                if(currentProgrammedRecipe.getTime().equals("Autres")){
                    snacks.add(currentProgrammedRecipe);
                }
                //Dinner
                if(currentProgrammedRecipe.getTime().equals("Dîner")){
                    dinner.add(currentProgrammedRecipe);
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

            if(breakFast.size() == 0){
                titleBreakfast.setVisibility(View.GONE);
            }
            if(lunch.size() == 0){
                titleLunch.setVisibility(View.GONE);
            }
            if(afternoonSnack.size() == 0){
                titleAfternoonSnack.setVisibility(View.GONE);
            }
            if(snacks.size() == 0){
                titleSnacks.setVisibility(View.GONE);
            }
            if(dinner.size() == 0){
                titleDinner.setVisibility(View.GONE);
            }
        }
    }

}

