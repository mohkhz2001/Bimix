package com.mohammadkz.bimix.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohammadkz.bimix.R;

public class TrackingCodeFragment extends Fragment {

    View view;
    private String code;
    TextView trackingCode;

    public TrackingCodeFragment(String code) {
        // Required empty public constructor
        this.code = code;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tracking_code, container, false);
        trackingCode = view.findViewById(R.id.trackingCode);

        trackingCode.setText(code);

        return view;
    }
}