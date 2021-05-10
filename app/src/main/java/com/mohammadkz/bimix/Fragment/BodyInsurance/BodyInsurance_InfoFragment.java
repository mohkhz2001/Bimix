package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.R;

public class BodyInsurance_InfoFragment extends Fragment {

    View view;
    AutoCompleteTextView useSpinner, historyInsurance, lastCompany;
    NumberPicker yearPicker_ir, yearPicker_us;
    Button next;
    TextInputEditText insuranceID, carModel;
    TextInputLayout insuranceID_layout, lastCompany_layout;
    BodyInsurance bodyInsurance;

    public BodyInsurance_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info_body_insurance, container, false);

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
        next = view.findViewById(R.id.next);
        insuranceID = view.findViewById(R.id.insuranceID);
        carModel = view.findViewById(R.id.carModel);
        insuranceID_layout = view.findViewById(R.id.insuranceID_layout);
        lastCompany_layout = view.findViewById(R.id.lastCompany_layout);

        bodyInsurance = new BodyInsurance();
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

        useSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setUseFor(selected);
            }

        });

        historyInsurance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setHistory(selected);
                if (parent.getItemAtPosition(position).equals("خودرو صفر") || parent.getItemAtPosition(position).equals("بیمه بدنه ندارم")) {
                    lastCompany_layout.setVisibility(View.GONE);
                    insuranceID_layout.setVisibility(View.GONE);
                } else {
                    lastCompany_layout.setVisibility(View.VISIBLE);
                    insuranceID_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        lastCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setLastCompany(selected);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyInsurance.setYear(Integer.toString(yearPicker_ir.getValue()));
                bodyInsurance.setNumberInsurance(insuranceID.getText().toString());
                bodyInsurance.setCarModel(carModel.getText().toString());
                ((BodyInsuranceActivity) getActivity()).setSeekBar(2);

                BodyInsurance_CoverFragment bodyInsurance_coverFragment = new BodyInsurance_CoverFragment(bodyInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_coverFragment).commit();
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