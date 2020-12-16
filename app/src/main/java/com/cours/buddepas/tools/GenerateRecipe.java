package com.cours.buddepas.tools;

import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cours.buddepas.R;
import com.cours.buddepas.models.Filter;
import com.cours.buddepas.models.ProgrammedRecipe;
import com.cours.buddepas.models.Recipe;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GenerateRecipe {
    private ApiManager apiManager = ApiManager.getInstance();
    private Singleton singleton = Singleton.getInstance();
    private String date;
    private String kind;

    public GenerateRecipe(String date, String kind)
    {
        this.date = date;
        this.kind = kind;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Recipe> generate()
    {
        String[] tempdate = date.split("-");
        int day = Integer.valueOf(tempdate[0]);
        int month = Integer.valueOf(tempdate[1]);
        int year = Integer.valueOf(tempdate[2]);
        //Taking all the dates for the week
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        Date time = c.getTime();

        ArrayList<String> dates = new ArrayList<String>();
        c.set(year,month,day);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        for (int i=0; i < 7;i++)
        {
            c.add(Calendar.DATE, -1);
            dates.add(sdf.format(c.getTime()));
        }
        c.setTime(time);
        //Gathering all the recipes for the week as well as their price
        ArrayList<ProgrammedRecipe> weekrecipes = new ArrayList<ProgrammedRecipe>();
        ArrayList<ProgrammedRecipe> recipes = singleton.getCalendarCopy();
        int halfprice = 0;
        int normalprice = 0;
        float totalprice = 0;
        HashMap<String,Integer> types= new HashMap<String,Integer>();

        //Taking the types for the new recipe
        for (ProgrammedRecipe r: recipes) {
            if (dates.contains(r.getDate()))
            {
                weekrecipes.add(r);
                if (r.getKind() == "Petit déjeuner" || r.getKind() == "Goûter")
                {
                    halfprice ++;
                }
                else
                {
                    normalprice ++;
                }
                ArrayList<String> kinds = r.getTypes();
                for (String k:kinds) {
                    if (types.containsKey(k))
                    {
                        types.put(k,types.get(k)+1);
                    }
                    else
                    {
                        types.put(k,1);
                    }
                }
                normalprice++;
            }
        }
        ArrayList<String> missingtypes = new ArrayList<String>();
        int minquantity = 7;
        ArrayList<String> res = new ArrayList<String>(Arrays.asList(Resources.getSystem().getStringArray(R.array.apport_recette)));
        res.remove("");
        for (String type:res) {
            if (types.get(res) <7*4/res.size())
            {
                missingtypes.add(type);
            }
        }

        ArrayList<Recipe> recipeslist = new ArrayList<Recipe>();
        Filter oldfilter = singleton.getFilter();
        Filter newfilter;
        for (String type: res) {

            newfilter = new Filter("", "",kind, type, -1);
            singleton.setFilter(newfilter);
            recipeslist.addAll(singleton.getRecipesArrayList());
        }
        singleton.setFilter(oldfilter);
        float nbhalf = 7*2 - halfprice;
        if (nbhalf < 0)
        {
            nbhalf = 0;
        }
        float nbnormal = 7*2- normalprice;
        if (nbnormal < 0)
        {
            nbnormal = 0;
        }
        int budget = singleton.getCurrentUserData().getBudget();
        float budgetleft = (budget-totalprice)/(nbhalf/2 + nbnormal);
        ArrayList<Recipe> finallist = new ArrayList<Recipe>();

        for (Recipe recipe:recipeslist) {
            if (recipe.getPrice() <= budgetleft)
            {
                finallist.add(recipe);
            }
        }
        return finallist;
    }


}
