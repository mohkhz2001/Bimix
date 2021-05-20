package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammadkz.bimix.Fragment.PhysiciansInsurance.PhysyciansInsurance_InfoFragment;
import com.mohammadkz.bimix.R;

public class PhysiciansInsuranceActivity extends AppCompatActivity {

    TextView firstInfo, confirmInfo, trackingCode;
    ImageView back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicians_insurance);

        initViews();
        controllerViews();
        start();
    }

    private void initViews() {
        firstInfo = findViewById(R.id.firstInfo);
        confirmInfo = findViewById(R.id.confirmInfo);
        trackingCode = findViewById(R.id.trackingCode);
        back_nav = findViewById(R.id.back_nav);
    }

    private void controllerViews() {
        back_nav.setOnClickListener(v -> {
            finish();
        });
    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PhysyciansInsurance_InfoFragment physyciansInsurance_infoFragment = new PhysyciansInsurance_InfoFragment();
        fragmentTransaction.replace(R.id.frameLayout, physyciansInsurance_infoFragment).commit();
    }

    //change active
    public void setLevel(int n) {

        switch (n) {
            case 2:
                firstInfo.setEnabled(false);
                confirmInfo.setEnabled(true);
                break;
            case 3:
                confirmInfo.setEnabled(false);
                trackingCode.setEnabled(true);
                break;

        }
    }
}