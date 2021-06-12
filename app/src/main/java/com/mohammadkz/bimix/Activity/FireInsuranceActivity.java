package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammadkz.bimix.Fragment.BodyInsurance.BodyInsurance_InfoFragment;
import com.mohammadkz.bimix.Fragment.FireInsurance.FireInsurance_InfoFragment;
import com.mohammadkz.bimix.R;

public class FireInsuranceActivity extends AppCompatActivity {

    TextView firstInfo, location, confirmInfo, trackingCode;
    ImageView back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_insurance);

        initViews();
        controllerViews();
        start();

    }

    //init views
    private void initViews() {
        firstInfo = findViewById(R.id.firstInfo);
        confirmInfo = findViewById(R.id.confirmInfo);
        trackingCode = findViewById(R.id.trackingCode);
        back_nav = findViewById(R.id.back_nav);
        location = findViewById(R.id.location);
    }

    //controller
    private void controllerViews() {
        back_nav.setOnClickListener(v -> {
            finish();
        });
    }

    //change active
    public void setLevel(int n) {

        switch (n) {
            case 2:
                firstInfo.setEnabled(false);
                location.setEnabled(true);
                break;

            case 3:
                location.setEnabled(false);
                confirmInfo.setEnabled(true);
                break;

            case 4:
                confirmInfo.setEnabled(false);
                trackingCode.setEnabled(true);
                break;
        }
    }

    // start fun ==> info fragment
    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FireInsurance_InfoFragment fireInsurance_infoFragment = new FireInsurance_InfoFragment();
        fragmentTransaction.replace(R.id.frameLayout, fireInsurance_infoFragment).commit();
    }
}