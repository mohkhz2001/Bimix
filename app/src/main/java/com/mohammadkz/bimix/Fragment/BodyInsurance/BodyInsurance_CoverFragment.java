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
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class BodyInsurance_CoverFragment extends Fragment {

    private BodyInsurance bodyInsurance;
    View view;
    AnimatedCheckBox one, two, there, four, five, six, seven, eight;
    TextView one_txt, two_txt, three_txt, four_txt, five_txt, six_txt, seven_txt, eight_txt;
    ArrayList<String> coverList = new ArrayList<>();
    Button next;

    public BodyInsurance_CoverFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_cover, container, false);

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
        eight = view.findViewById(R.id.eight);
        seven = view.findViewById(R.id.seven);

        one_txt = view.findViewById(R.id.one_txt);
        two_txt = view.findViewById(R.id.two_txt);
        three_txt = view.findViewById(R.id.three_txt);
        four_txt = view.findViewById(R.id.four_txt);
        five_txt = view.findViewById(R.id.five_txt);
        six_txt = view.findViewById(R.id.six_txt);
        seven_txt = view.findViewById(R.id.seven_txt);
        eight_txt = view.findViewById(R.id.eight_txt);

        next = view.findViewById(R.id.next);
    }

    private void controllerViews() {
        one.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(one_txt.getText().toString());
                else {
                    int n = coverList.indexOf(one_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        two.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(two_txt.getText().toString());
                else {
                    int n = coverList.indexOf(two_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        there.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(three_txt.getText().toString());
                else {
                    int n = coverList.indexOf(three_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        four.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(four_txt.getText().toString());
                else {
                    int n = coverList.indexOf(four_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        five.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(five_txt.getText().toString());
                else {
                    int n = coverList.indexOf(five_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        six.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(six_txt.getText().toString());
                else {
                    int n = coverList.indexOf(six_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        seven.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(seven_txt.getText().toString());
                else {
                    int n = coverList.indexOf(seven_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        eight.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    coverList.add(eight_txt.getText().toString());
                else {
                    int n = coverList.indexOf(eight_txt.getText().toString());
                    coverList.remove(n);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BodyInsuranceActivity) getActivity()).setSeekBar(3);
                bodyInsurance.setCover(coverList);
                BodyInsurance_OffFragment bodyInsurance_offFragment = new BodyInsurance_OffFragment(bodyInsurance);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, bodyInsurance_offFragment).commit();
            }
        });

    }

}