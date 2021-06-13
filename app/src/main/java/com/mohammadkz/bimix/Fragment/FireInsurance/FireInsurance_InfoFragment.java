package com.mohammadkz.bimix.Fragment.FireInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mohammadkz.bimix.Activity.FireInsuranceActivity;
import com.mohammadkz.bimix.Model.FireInsurance;
import com.mohammadkz.bimix.R;


public class FireInsurance_InfoFragment extends Fragment {

    View view;
    private FireInsurance fireInsurance;
    AutoCompleteTextView buildingType, typeOfStructure;
    TextInputEditText lifeTime, price, area;
    Button next;
    TextInputLayout layout_buildingType, layout_typeOfStructure, layout_lifeTime, layout_price, layout_area;

    public FireInsurance_InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fire_insurance_info, container, false);

        initViews();
        controllerViews();
        setSpinners();

        return view;
    }

    //    init view
    private void initViews() {
        buildingType = view.findViewById(R.id.buildingType);
        typeOfStructure = view.findViewById(R.id.typeOfStructure);
        lifeTime = view.findViewById(R.id.lifeTime);
        price = view.findViewById(R.id.price);
        area = view.findViewById(R.id.area);
        next = view.findViewById(R.id.next);
        layout_buildingType = view.findViewById(R.id.layout_buildingType);
        layout_typeOfStructure = view.findViewById(R.id.layout_typeOfStructure);
        layout_lifeTime = view.findViewById(R.id.layout_lifeTime);
        layout_price = view.findViewById(R.id.layout_price);
        layout_area = view.findViewById(R.id.layout_area);

        fireInsurance = new FireInsurance();
    }

    //controller
    private void controllerViews() {

        buildingType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                fireInsurance.setBuildingType(selected);
                layout_buildingType.setErrorEnabled(false);
            }
        });

        typeOfStructure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                fireInsurance.setTypeOfStructure(selected);
                layout_typeOfStructure.setErrorEnabled(false);
            }
        });

        lifeTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_lifeTime.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_area.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_price.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValue()) {
                    fireInsurance.setPrice(price.getText().toString());
                    fireInsurance.setLifeTime(lifeTime.getText().toString());
                    fireInsurance.setArea(area.getText().toString());

                    ((FireInsuranceActivity) getActivity()).setLevel(2);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    FireInsurance_LocationFragment fireInsurance_locationFragment = new FireInsurance_LocationFragment(fireInsurance);
                    fragmentTransaction.replace(R.id.frameLayout, fireInsurance_locationFragment).commit();

                } else {
                    setErrorField();
                }

            }
        });

    }

    // set number picker date
    private void setSpinners() {

        String[] item_build = {"آجری", "اسکلت بتنی", "اسکلت فلزی"};
        ArrayAdapter<String> buildSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_build);
        typeOfStructure.setAdapter(buildSpinnerItem);

        String[] item_type = {"ویلایی", "آپارتمان"};
        ArrayAdapter<String> structureSpinnerItem = new ArrayAdapter<>(getContext(), R.layout.spinner_item, item_type);
        buildingType.setAdapter(structureSpinnerItem);

    }

    // check the value in the filed => if empty show error
    private boolean checkValue() {

        if (fireInsurance.getBuildingType() == null || fireInsurance.getTypeOfStructure() == null || lifeTime.getText().length() < 1 || price.getText().length() < 1 || area.getText().length() < 1) {
            return false;
        } else
            return true;

    }

    // if check value return false ==> show error
    private void setErrorField() {
        if (fireInsurance.getBuildingType() == null) {
            layout_buildingType.setErrorEnabled(true);
            layout_buildingType.setError("یک مورد را انتخاب کنید.");
        }

        if (fireInsurance.getTypeOfStructure() == null) {
            layout_typeOfStructure.setErrorEnabled(true);
            layout_typeOfStructure.setError("یک مورد را انتخاب کنید.");
        }

        if (lifeTime.getText().length() < 1) {
            layout_lifeTime.setErrorEnabled(true);
            layout_lifeTime.setError("یک مورد را انتخاب کنید.");
        }

        if (price.getText().length() < 1) {
            layout_price.setErrorEnabled(true);
            layout_price.setError("یک مورد را انتخاب کنید.");
        }

        if (area.getText().length() < 1) {
            layout_area.setErrorEnabled(true);
            layout_area.setError("یک مورد را انتخاب کنید.");
        }
    }
}