package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
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
    TextInputLayout insuranceID_layout, lastCompany_layout, useSpinner_layout, historyInsurance_layout, carModel_layout;
    BodyInsurance bodyInsurance;
    LinearLayout year_layout;

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
        useSpinner_layout = view.findViewById(R.id.useSpinner_layout);
        historyInsurance_layout = view.findViewById(R.id.historyInsurance_layout);
        year_layout = view.findViewById(R.id.year_layout);
        carModel_layout = view.findViewById(R.id.carModel_layout);

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
                useSpinner_layout.setErrorEnabled(false);
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setUseFor(selected);
            }

        });

        historyInsurance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historyInsurance_layout.setErrorEnabled(false);
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setHistory(selected);
                if (parent.getItemAtPosition(position).equals("خودرو صفر")) {
                    lastCompany_layout.setVisibility(View.GONE);
                    insuranceID_layout.setVisibility(View.GONE);
                    year_layout.setVisibility(View.GONE);
                } else if (parent.getItemAtPosition(position).equals("بیمه بدنه ندارم")) {
                    year_layout.setVisibility(View.VISIBLE);
                } else {
                    lastCompany_layout.setVisibility(View.VISIBLE);
                    insuranceID_layout.setVisibility(View.VISIBLE);
                    year_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        lastCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastCompany_layout.setErrorEnabled(false);
                String selected = (String) parent.getItemAtPosition(position);
                bodyInsurance.setLastCompany(selected);
            }
        });

        insuranceID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                insuranceID_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        carModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                carModel_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyInsurance.setYear(Integer.toString(yearPicker_ir.getValue()));
                bodyInsurance.setNumberInsurance(insuranceID.getText().toString());
                bodyInsurance.setCarModel(carModel.getText().toString());

                if (checkValues()) {
//                if (true) {
                    ((BodyInsuranceActivity) getActivity()).setSeekBar(2);

                    BodyInsurance_CoverFragment bodyInsurance_coverFragment = new BodyInsurance_CoverFragment(bodyInsurance);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_coverFragment).commit();
                } else {
                    setErrorField();
                }

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


        String[] item_lastInsurance = {"ایران", "کوثر", "ملت", "البرز", "آسیا", "دی", "دانا", "ما", "سرمد", "سامان", "رازی", "دی", "سینا", "معلم", "پاسارگاد", "نوین",
                "پارسیان", "کارآفرین", "آرمان", "میهن", "تعاون", "آسماری", "تجارت نو"};
        ArrayAdapter<String> lastInsuranceSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_lastInsurance);
        lastCompany.setAdapter(lastInsuranceSpinnerItem);

    }

    private void setNumPicker() {
        yearPicker_ir.setMaxValue(1400);
        yearPicker_ir.setMinValue(1350);

        yearPicker_us.setMinValue(1971);
        yearPicker_us.setMaxValue(2021);

    }

    private boolean checkValues() {
        if (bodyInsurance.getHistory() == null || !bodyInsurance.getHistory().equals("بیمه بدنه ندارم") || !bodyInsurance.getHistory().equals("خودرو صفر")) {
            if (bodyInsurance.getHistory() == null || bodyInsurance.getUseFor() == null || carModel.getText().length() < 1) {
                return false;
            } else
                return true;
        } else if (!bodyInsurance.getHistory().equals("بیمه بدنه دارم")) {
            if (bodyInsurance.getHistory() == null || bodyInsurance.getUseFor() == null || carModel.getText() == null || insuranceID.getText().length() < 1 || bodyInsurance.getLastCompany() == null) {
                return false;
            } else
                return true;
        } else {
            return false;
        }

    }

    private void setErrorField() {
        if (bodyInsurance.getUseFor() == null) {
            useSpinner_layout.setError("یک مورد را انتخاب کنید");
            useSpinner_layout.setErrorEnabled(true);
        }


        if (bodyInsurance.getHistory() == null) {
            historyInsurance_layout.setError("یک مورد را انتخاب کنید");
            historyInsurance_layout.setErrorEnabled(true);
        } else if ((!bodyInsurance.getHistory().equals("خودرو صفر") && !bodyInsurance.getHistory().equals("بیمه بدنه ندارم") &&
                (bodyInsurance.getHistory().equals("بیمه بدنه دارم") && (bodyInsurance.getNumberInsurance().equals("") || bodyInsurance.getLastCompany().equals(""))))) {

            if (bodyInsurance.getNumberInsurance().equals("")) {
                insuranceID_layout.setErrorEnabled(true);
                insuranceID_layout.setError("این قسمت نمی تواند خالی بماند.");
            }

            if (bodyInsurance.getLastCompany() == null) {
                lastCompany_layout.setErrorEnabled(true);
                lastCompany_layout.setError("یک مورد را انتخاب کنید");
            }

        }


        if (bodyInsurance.getCarModel().equals("")) {
            carModel_layout.setError("نمی تواند خالی باشد");
            carModel_layout.setErrorEnabled(true);
        }


    }

}