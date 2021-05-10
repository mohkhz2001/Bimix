package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.R;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;


public class BodyInsurance_VisitFragment extends Fragment {

    View view;
    BodyInsurance bodyInsurance;
    TextInputEditText address;
    AnimatedCheckBox ourPlace, yourPlace;
    NumberPicker day, hour;
    ArrayList<String> days = new ArrayList<>();
    LinearLayout rootInfo;
    Button next;
    String[] hour_array = {"10-12", "12-14", "14-16"};


    public BodyInsurance_VisitFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_visit, container, false);

        initViews();
        controllerViews();
        setNumberPickerValue();

        return view;
    }

    private void initViews() {
        ourPlace = view.findViewById(R.id.ourPlace);
        yourPlace = view.findViewById(R.id.yourPlace);
        address = view.findViewById(R.id.address);
        day = view.findViewById(R.id.day);
        hour = view.findViewById(R.id.hour);
        rootInfo = view.findViewById(R.id.rootInfo);
        next = view.findViewById(R.id.next);
    }

    private void controllerViews() {

        ourPlace.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    if (yourPlace.isChecked())
                        yourPlace.setChecked(false);
                    rootInfo.setVisibility(View.GONE);
                }

            }
        });

        yourPlace.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    if (ourPlace.isChecked())
                        ourPlace.setChecked(false);
                    rootInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BodyInsuranceActivity) getActivity()).setSeekBar(5);
                BodyInsurance.Visit visit = new BodyInsurance.Visit(yourPlace.isChecked());
                visit.setAddress(address.getText().toString());
                visit.setDate(days.get(day.getValue()));
                visit.setTime(hour_array[hour.getValue()]);
                bodyInsurance.setVisit(visit);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                BodyInsurance_SendFragment bodyInsurance_sendFragment = new BodyInsurance_SendFragment(bodyInsurance);
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_sendFragment).commit();

            }
        });

    }

    private void setNumberPickerValue() {

        hour.setMinValue(0);
        hour.setMaxValue(hour_array.length - 1);
        hour.setWrapSelectorWheel(true);
        hour.setDisplayedValues(hour_array);

        String[] date = time();
        this.day.setMinValue(0);
        this.day.setMaxValue(date.length - 1);
        this.day.setWrapSelectorWheel(true);
        this.day.setDisplayedValues(date);
    }

    private String[] time() {

        PersianDate persianDate = new PersianDate();
        int month = persianDate.getShMonth();
        int day = persianDate.getShDay();
        String day_name = persianDate.dayName();
        String month_name = persianDate.monthName();

        for (int i = 0; i < 7; i++) {
            if (day > 29) {
                if (day == 31 && month <= 6 || day == 30 && month > 6) {
                    days.add("" + month_name + " " + day);
                } else if (day > 31 && month <= 6) {
                    day -= 31;
                    month++;
                    month_name = persianDate.monthName(month);
                    days.add("" + month_name + " " + day);
                } else if (day > 30 && month > 6) {
                    day -= 30;
                    month++;
                    month_name = persianDate.monthName(month);
                    days.add("" + month_name + " " + day);
                }
            } else {
                days.add("" + month_name + " " + day);
            }
            day++;
        }
        String[] c = days.toArray(new String[0]);

        return c;
    }

}
