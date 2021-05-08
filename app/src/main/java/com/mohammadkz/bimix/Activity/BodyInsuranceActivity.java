package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammadkz.bimix.Fragment.BodyInsurance.InfoFragment;
import com.mohammadkz.bimix.R;
import com.warkiz.widget.IndicatorSeekBar;

public class BodyInsuranceActivity extends AppCompatActivity {

    IndicatorSeekBar seekBar;
    TextView firstInfo;
    ImageView back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_insurance);


        initViews();
        controllerViews();
        start();
        setLevel(seekBar.getProgress());
    }

    private void initViews() {
        seekBar = findViewById(R.id.seekBar);
        firstInfo = findViewById(R.id.firstInfo);
        back_nav = findViewById(R.id.back_nav);

    }

    private void controllerViews() {

        back_nav.setOnClickListener(v -> {
            finish();
        });

        // make thumb disable of any touch
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        firstInfo.setOnClickListener(v -> {
            setSeekBar();
        });
    }

    private void setSeekBar() {
        int n = seekBar.getProgress() + 25;
        seekBar.setProgress(n);
        setLevel(n);
    }

    private void setLevel(int n) {
        switch (n) {
            case 0:
//                firstInfo.setEnabled(true);
                break;
            case 25:
                firstInfo.setEnabled(false);
                break;
            case 50:

                break;

            case 75:

                break;
            case 100:

                break;
        }
    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        InfoFragment infoFragment = new InfoFragment();
        fragmentTransaction.replace(R.id.frameLayout, infoFragment).commit();
    }
}