package com.mohammadkz.bimix.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohammadkz.bimix.R;


public class IssuanceFragment extends Fragment {

    View view;

    public IssuanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issuance, container, false);

        


        return view;
    }

    private void initViews() {

    }

    private void controllerView() {

    }

}