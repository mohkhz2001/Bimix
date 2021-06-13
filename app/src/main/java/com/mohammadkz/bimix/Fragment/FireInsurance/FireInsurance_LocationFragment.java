package com.mohammadkz.bimix.Fragment.FireInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mohammadkz.bimix.Activity.FireInsuranceActivity;
import com.mohammadkz.bimix.Model.FireInsurance;
import com.mohammadkz.bimix.R;

public class FireInsurance_LocationFragment extends Fragment {

    View view;
    FireInsurance fireInsurance;
    TextInputEditText postCode, address;
    TextInputLayout layout_address, layout_postCode;
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
        layout_address = view.findViewById(R.id.layout_address);
        layout_postCode = view.findViewById(R.id.layout_postCode);
        next = view.findViewById(R.id.next);
    }

    // controller for next btn
    private void controllerViews() {

        postCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_postCode.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_address.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValue()) {

                    fireInsurance.setAddress(address.getText().toString());
                    fireInsurance.setPostCode(postCode.getText().toString());

                    ((FireInsuranceActivity) getActivity()).setLevel(3);

                    FireInsurance_ConfirmFragment fireInsurance_confirmFragment = new FireInsurance_ConfirmFragment(fireInsurance);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fireInsurance_confirmFragment).commit();

                } else {
                    setErrorField();
                }
            }
        });

    }

    // check the value in the filed => if empty show error
    private boolean checkValue() {

        if (address.getText().length() < 1 || postCode.getText().length() < 1) {
            return false;
        } else
            return true;

    }

    // if check value return false ==> show error
    private void setErrorField() {

        if (postCode.getText().length() < 1) {
            layout_address.setErrorEnabled(true);
            layout_address.setError("این قسمت نمی تواند خالی باشد.");
        }

        if (address.getText().length() < 1) {
            layout_postCode.setErrorEnabled(true);
            layout_postCode.setError("این قسمت نمی تواند خالی باشد.");
        }
    }
}