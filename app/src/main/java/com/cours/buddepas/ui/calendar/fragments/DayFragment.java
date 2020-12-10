package com.cours.buddepas.ui.calendar.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cours.buddepas.R;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

public class DayFragment extends Fragment implements DatePickerListener {
    public DayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day, container, false);
        // Inflate the layout for this fragment
        // find the picker
        HorizontalPicker picker = (HorizontalPicker) root.findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker
                .setListener(this)
                .setDays(60)
                .setOffset(7)
                .setDateSelectedColor(getContext().getResources().getColor(R.color.colorPrimary))
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(getContext().getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(getContext().getResources().getColor(R.color.disable))
                .setUnselectedDayTextColor(getContext().getResources().getColor(R.color.disable))
                .showTodayButton(false)
                .init();
        picker.setDate(new DateTime());
        return root;
    }

    @Override
    public void onDateSelected(@NonNull final DateTime dateSelected) {
        // log it for demo
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
    }
}