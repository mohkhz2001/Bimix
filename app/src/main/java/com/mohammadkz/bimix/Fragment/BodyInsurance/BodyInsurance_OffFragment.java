package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.R;

import java.util.ArrayList;

public class BodyInsurance_OffFragment extends Fragment {

    View view;
    BodyInsurance bodyInsurance;

    AnimatedCheckBox one, two, there, four, five, six;
    TextView one_txt, two_txt, three_txt, four_txt, five_txt, six_txt;
    ArrayList<String> offList = new ArrayList<>();
    Button next;

    public BodyInsurance_OffFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_off, container, false);

        initViews();
        controllerViews();

        return view;
    }

    private void initViews() {
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        there = view.findViewById(R.id.three);
        four = view.findViewById(R.id.four);
        five = view.findViewById(R.id.five);
        six = view.findViewById(R.id.six);
        six.setChecked(true);
        six.setEnabled(false);

        one_txt = view.findViewById(R.id.one_txt);
        two_txt = view.findViewById(R.id.two_txt);
        three_txt = view.findViewById(R.id.three_txt);
        four_txt = view.findViewById(R.id.four_txt);
        five_txt = view.findViewById(R.id.five_txt);
        six_txt = view.findViewById(R.id.six_txt);

        next = view.findViewById(R.id.next);

    }

    private void controllerViews() {
        one.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(one_txt.getText().toString());
                else {
                    int n = offList.indexOf(one_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        two.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(two_txt.getText().toString());
                else {
                    int n = offList.indexOf(two_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        there.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(three_txt.getText().toString());
                else {
                    int n = offList.indexOf(three_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        four.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(four_txt.getText().toString());
                else {
                    int n = offList.indexOf(four_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        five.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(five_txt.getText().toString());
                else {
                    int n = offList.indexOf(five_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        six.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    offList.add(six_txt.getText().toString());
                else {
                    int n = offList.indexOf(six_txt.getText().toString());
                    offList.remove(n);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BodyInsuranceActivity) getActivity()).setSeekBar(4);
                bodyInsurance.setOff(offList);
                BodyInsurance_VisitFragment bodyInsurance_visistFragment = new BodyInsurance_VisitFragment(bodyInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_visistFragment).commit();
            }
        });

    }

}