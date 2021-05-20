package com.mohammadkz.bimix.Fragment.FireInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.mohammadkz.bimix.Activity.FireInsuranceActivity;
import com.mohammadkz.bimix.Model.FireInsurance;
import com.mohammadkz.bimix.R;

public class FireInsurance_LocationFragment extends Fragment {

    View view;
    FireInsurance fireInsurance;
    TextInputEditText postCode, address;
    Button next;

    public FireInsurance_LocationFragment(FireInsurance fireInsurance) {
        // Required empty public constructor
        this.fireInsurance = fireInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fire_insurance_location, container, false);

        initViews();
        controllerViews();

        return view;
    }

    //init views
    private void initViews() {
        address = view.findViewById(R.id.address);
        postCode = view.findViewById(R.id.postCode);
        next = view.findViewById(R.id.next);
    }

    // controller for next btn
    private void controllerViews() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireInsurance.setAddress(address.getText().toString());
                fireInsurance.setPostCode(postCode.getText().toString());

                FireInsurance_ConfirmFragment fireInsurance_confirmFragment = new FireInsurance_ConfirmFragment(fireInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fireInsurance_confirmFragment).commit();
                ((FireInsuranceActivity) getActivity()).setLevel(3);
            }
        });

    }
}