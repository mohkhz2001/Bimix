package com.mohammadkz.bimix.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Activity.MainPageActivity;
import com.mohammadkz.bimix.R;


public class IssuanceFragment extends Fragment {

    View view;

    ImageView bodyInsurance, thirdInsurance, docInsurance, personInsurance, elevatorInsurance,
            lifeInsurance, fireInsurance, damageInsurance;

    public IssuanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issuance, container, false);

        initViews();
        controllerView();


        return view;
    }

    private void initViews() {
        bodyInsurance = view.findViewById(R.id.bodyInsurance);
        thirdInsurance = view.findViewById(R.id.thirdInsurance);
        docInsurance = view.findViewById(R.id.docInsurance);
        personInsurance = view.findViewById(R.id.personInsurance);
        elevatorInsurance = view.findViewById(R.id.elevatorInsurance);
        lifeInsurance = view.findViewById(R.id.lifeInsurance);
        fireInsurance = view.findViewById(R.id.fireInsurance);
        damageInsurance = view.findViewById(R.id.damageInsurance);

    }

    private void controllerView() {

        bodyInsurance.setOnClickListener(v -> {
            ((MainPageActivity) getActivity()).setTitle("بیمه بدنه خودرو");
            Intent intent = new Intent(getActivity(), BodyInsuranceActivity.class);
            startActivity(intent);

        });

        thirdInsurance.setOnClickListener(v -> {

        });

        docInsurance.setOnClickListener(v -> {

        });

        personInsurance.setOnClickListener(v -> {

        });

        elevatorInsurance.setOnClickListener(v -> {

        });

        lifeInsurance.setOnClickListener(v -> {

        });

        fireInsurance.setOnClickListener(v -> {

        });

        damageInsurance.setOnClickListener(v -> {

        });

    }


}