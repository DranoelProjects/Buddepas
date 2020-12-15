package com.cours.buddepas.models;

import android.util.Log;

import java.util.ArrayList;

public class Filter {
    private String name = "";
    public String author = "";
    private String kind = "";
    private Integer minutesDuration = -1;
    private String typeFilter = "";

    public Filter(){}

    public Filter(String name, String author, String kind, String type, Integer minutesDuration) {
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.typeFilter = type;
        this.minutesDuration = minutesDuration;
    }

    public boolean fitFilters(Recipe recipe)
    {
        if (!name.isEmpty() && !recipe.getName().toLowerCase().contains(name.toLowerCase()))
        {
            return false;
        }
        if (!author.isEmpty() && !recipe.getAuthor().toLowerCase().contains(author.toLowerCase()))
        {
            return false;
        }

        if (!kind.isEmpty() && !recipe.getKind().toLowerCase().contains(kind.toLowerCase()))
        {
            return false;
        }
        if (minutesDuration != -1 && (recipe.getMinutesDuration() > minutesDuration+5 || recipe.getMinutesDuration() < minutesDuration-5))
        {
            return false;
        }

        if (!typeFilter.isEmpty())
        {
            ArrayList<String> recipeTypes = recipe.getTypes();
            return foundType(recipeTypes);
        }
        return true;
    }

    private boolean foundType(ArrayList<String> recipeTypes)
    {
        for (String type : recipeTypes)
        {
            if (type != null)
            {
                if (type.equals(typeFilter))
                {
                    return true;
                }
            }

        }
        return false;
    }


}
