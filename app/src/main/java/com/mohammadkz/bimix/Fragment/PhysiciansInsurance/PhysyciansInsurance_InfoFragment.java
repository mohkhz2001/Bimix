package com.mohammadkz.bimix.Fragment.PhysiciansInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohammadkz.bimix.Model.PhysycianInsurance;
import com.mohammadkz.bimix.R;

public class PhysyciansInsurance_InfoFragment extends Fragment {

    View view;
    PhysycianInsurance physycianInsurance;

    public PhysyciansInsurance_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_physycians_insurance_info, container, false);

        initViews();
        controllerViews();

        return view;
    }

    private void initViews() {

    }

    private void controllerViews() {

    }

}