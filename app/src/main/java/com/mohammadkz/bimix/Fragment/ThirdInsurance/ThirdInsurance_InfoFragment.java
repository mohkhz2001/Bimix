package com.mohammadkz.bimix.Fragment.ThirdInsurance;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Activity.ThirdInsuranceActivity;
import com.mohammadkz.bimix.Fragment.BodyInsurance.BodyInsurance_CoverFragment;
import com.mohammadkz.bimix.Model.ThirdInsurance;
import com.mohammadkz.bimix.R;



public class ThirdInsurance_InfoFragment extends Fragment {

    View view;
    ThirdInsurance thirdInsurance;
    AutoCompleteTextView useSpinner, lastCompany;
    TextInputLayout insuranceID_layout, lastCompany_layout, useSpinner_layout, endDate_layout;
    TextInputEditText insuranceId, endDate;
    Button next;


    public ThirdInsurance_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third_insurance_info, container, false);

        thirdInsurance = new ThirdInsurance();
        initViews();
        controllerViews();
        setValueSpinner();

        return view;
    }

    private void initViews() {
        useSpinner = view.findViewById(R.id.useSpinner);
        insuranceId = view.findViewById(R.id.insuranceID);
        lastCompany = view.findViewById(R.id.lastCompany);
        endDate = view.findViewById(R.id.endDate);
        insuranceID_layout = view.findViewById(R.id.insuranceID_layout);
        lastCompany_layout = view.findViewById(R.id.lastCompany_layout);
        useSpinner_layout = view.findViewById(R.id.useSpinner_layout);
        endDate_layout = view.findViewById(R.id.endDate_layout);
        next = view.findViewById(R.id.next);
    }

    private void controllerViews() {

        useSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                useSpinner_layout.setErrorEnabled(false);
                String selected = (String) parent.getItemAtPosition(position);
                thirdInsurance.setUseFor(selected);
            }

        });

        lastCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastCompany_layout.setErrorEnabled(false);
                String selected = (String) parent.getItemAtPosition(position);
                thirdInsurance.setLastCompany(selected);
            }
        });

        insuranceId.addTextChangedListener(new TextWatcher() {
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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValues()) {
                    ((ThirdInsuranceActivity) getActivity()).setSeekBar(2);
                    thirdInsurance.setEndDate(endDate.getText().toString());
                    thirdInsurance.setInsuranceID(insuranceId.getText().toString());

                    ThirdInsurance_sendFragment thirdInsurance_sendFragment = new ThirdInsurance_sendFragment(thirdInsurance);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, thirdInsurance_sendFragment).commit();
                } else {
                    setErrorField();
                }
            }
        });

        endDate_layout.setOnClickListener(new View.OnClickListener() {
            int year, days;
            String months;

            @Override
            public void onClick(View v) {


            }
        });
    }

    private void setValueSpinner() {

        String[] item_use = {"شخصی", "تاکسی", "امور اداری", "بارکش", "تاکسی برون شهری", "آتشنشانی ", "آمبولانس", "عملیات کشاورزی", "عملیات عمرانی"};
        ArrayAdapter<String> useSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_use);
        useSpinner.setAdapter(useSpinnerItem);


        String[] item_lastInsurance = {"ایران", "کوثر", "ملت", "البرز", "آسیا", "دی", "دانا", "ما", "سرمد", "سامان", "رازی", "دی", "سینا", "معلم", "پاسارگاد", "نوین",
                "پارسیان", "کارآفرین", "آرمان", "میهن", "تعاون", "آسماری", "تجارت نو"};
        ArrayAdapter<String> lastInsuranceSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_lastInsurance);
        lastCompany.setAdapter(lastInsuranceSpinnerItem);

    }

    private boolean checkValues() {
        if (insuranceId.getText().length() < 1 || endDate.getText().length() < 1 || thirdInsurance.getUseFor() == null || thirdInsurance.getLastCompany() == null) {
            return false;
        } else
            return true;
    }

    private void setErrorField() {
        if (thirdInsurance.getUseFor() == null) {
            useSpinner_layout.setError("یک مورد را انتخاب کنید");
            useSpinner_layout.setErrorEnabled(true);
        }

        if (thirdInsurance.getLastCompany() == null) {
            lastCompany_layout.setError("یک مورد را انتخاب کنید");
            lastCompany_layout.setErrorEnabled(true);
        }

        if (insuranceId.getText().length() < 2) {
            insuranceID_layout.setError("این قسمت نمی تواند خالی باشد");
            insuranceID_layout.setErrorEnabled(true);
        }

        if (endDate.getText().length() < 2) {
            endDate_layout.setError("این قسمت نمی تواند خالی باشد");
            endDate_layout.setErrorEnabled(true);
        }
    }

}