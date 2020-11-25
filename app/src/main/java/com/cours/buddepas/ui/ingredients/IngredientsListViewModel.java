package com.cours.buddepas.ui.ingredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IngredientsListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IngredientsListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ingredients list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}