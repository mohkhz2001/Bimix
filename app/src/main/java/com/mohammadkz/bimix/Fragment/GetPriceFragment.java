package com.mohammadkz.bimix.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mohammadkz.bimix.Activity.LoadWebViewActivity;
import com.mohammadkz.bimix.StaticFun;
import com.mohammadkz.bimix.R;


public class GetPriceFragment extends Fragment {

    View view;

    ImageView bodyInsurance, thirdInsurance, docInsurance, personInsurance, elevatorInsurance,
            lifeInsurance, fireInsurance;


    public GetPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_get_price, container, false);

        initViews();
        if (isNetworkAvailable())
            controllerView();
        else
            StaticFun.alertDialog_connectionFail(getContext());

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

    }

    private void controllerView() {

        bodyInsurance.setOnClickListener(v -> {
            choose(1);
        });

        thirdInsurance.setOnClickListener(v -> {
            choose(5);
        });

        docInsurance.setOnClickListener(v -> {
            choose(2);
        });

        personInsurance.setOnClickListener(v -> {
            choose(6);
        });

        elevatorInsurance.setOnClickListener(v -> {
            choose(3);
        });

        lifeInsurance.setOnClickListener(v -> {
            choose(7);
        });

        fireInsurance.setOnClickListener(v -> {
            choose(4);
        });

    }

    public boolean isNetworkAvailable() {
        // Get Connectivity Manager class object from Systems Service
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network Info from connectivity Manager
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void choose(int n) {
        Intent intent = new Intent(getContext(), LoadWebViewActivity.class);
        intent.putExtra("choose", n);
        startActivity(intent);
    }

}