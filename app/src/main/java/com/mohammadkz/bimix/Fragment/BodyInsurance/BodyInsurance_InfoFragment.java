package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;

import com.mohammadkz.bimix.R;

public class Body_InfoFragment extends Fragment {

    View view;
    AutoCompleteTextView useSpinner, historyInsurance, lastCompany;
    NumberPicker yearPicker_ir, yearPicker_us;

    public Body_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_body, container, false);

        initViews();
        controllerViews();
        setValues();
        return view;
    }

    private void initViews() {
        useSpinner = view.findViewById(R.id.useSpinner);
        historyInsurance = view.findViewById(R.id.historyInsurance);
        lastCompany = view.findViewById(R.id.lastCompany);
        yearPicker_ir = view.findViewById(R.id.yearPicker_ir);
        yearPicker_us = view.findViewById(R.id.yearPicker_us);

    }

    private void controllerViews() {

        yearPicker_ir.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearPicker_us.setValue(newVal + 621);
            }
        });

        yearPicker_us.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearPicker_ir.setValue(newVal + 621);
            }
        });

    }

    private void setValues() {
        setValueSpinner();
        setNumPicker();
    }

    private void setValueSpinner() {

        String[] item_use = {"شخصی", "تاکسی", "امور اداری", "بارکش", "تاکسی برون شهری", "آتشنشانی ", "آمبولانس", "عملیات کشاورزی", "عملیات عمرانی"};
        ArrayAdapter<String> useSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_use);
        useSpinner.setAdapter(useSpinnerItem);

        String[] item_history = {"خودرو صفر", "بیمه بدنه ندارم", "بیمه بدنه دارم"};
        ArrayAdapter<String> historySpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_history);
        historyInsurance.setAdapter(historySpinnerItem);


        String[] item_lastInsurance = {"ایران", "کوثر", "ملت", "تجارت", "البرز", "آسیا", "دی", "دانا", "ما", "سرمد", "سامان", "رازی", "دی", "سینا", "معلم", "پاسارگاد", "نوین"};
        ArrayAdapter<String> lastInsuranceSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_lastInsurance);
        lastCompany.setAdapter(lastInsuranceSpinnerItem);

    }

    private void setNumPicker() {
        yearPicker_ir.setMaxValue(1400);
        yearPicker_ir.setMinValue(1350);

        yearPicker_us.setMinValue(1971);
        yearPicker_us.setMaxValue(2021);

    }


}