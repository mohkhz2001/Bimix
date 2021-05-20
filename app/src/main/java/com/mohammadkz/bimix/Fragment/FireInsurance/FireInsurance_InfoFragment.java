package com.mohammadkz.bimix.Fragment.FireInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.mohammadkz.bimix.Activity.FireInsuranceActivity;
import com.mohammadkz.bimix.Model.FireInsurance;
import com.mohammadkz.bimix.R;


public class FireInsurance_InfoFragment extends Fragment {

    View view;
    private FireInsurance fireInsurance;
    AutoCompleteTextView buildingType, typeOfStructure;
    TextInputEditText lifeTime, price, area;
    Button next;

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

        fireInsurance = new FireInsurance();
    }

    //controller
    private void controllerViews() {

        buildingType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                fireInsurance.setBuildingType(selected);
            }
        });

        typeOfStructure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                fireInsurance.setTypeOfStructure(selected);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireInsurance.setPrice(price.getText().toString());
                fireInsurance.setLifeTime(lifeTime.getText().toString());
                fireInsurance.setArea(area.getText().toString());
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                FireInsurance_LocationFragment fireInsurance_locationFragment = new FireInsurance_LocationFragment(fireInsurance);
                fragmentTransaction.replace(R.id.frameLayout, fireInsurance_locationFragment).commit();

                ((FireInsuranceActivity) getActivity()).setLevel(2);
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

}