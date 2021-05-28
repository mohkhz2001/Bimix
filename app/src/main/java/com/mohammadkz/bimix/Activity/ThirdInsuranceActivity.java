package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohammadkz.bimix.Fragment.BodyInsurance.BodyInsurance_InfoFragment;
import com.mohammadkz.bimix.Fragment.ThirdInsurance.ThirdInsurance_InfoFragment;
import com.mohammadkz.bimix.R;
import com.warkiz.widget.IndicatorSeekBar;

public class ThirdInsuranceActivity extends AppCompatActivity {

    IndicatorSeekBar seekBar;
    TextView firstInfo, send, confirmInfo, trackingCode;
    ImageView back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_insurance);

        initViews();
        controllerView();
        start();

    }

    private void initViews() {
        seekBar = findViewById(R.id.seekBar);
        firstInfo = findViewById(R.id.firstInfo);
        send = findViewById(R.id.send);
        confirmInfo = findViewById(R.id.confirmInfo);
        trackingCode = findViewById(R.id.trackingCode);
        back_nav = findViewById(R.id.back_nav);
    }

    private void controllerView() {
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
    }

    public void setSeekBar(int slide) {

        switch (slide) {
            case 2:
                seekBar.setProgress(32);
                break;
            case 3:
                seekBar.setProgress(65);
                break;
            case 4:
                seekBar.setProgress(100);
                break;
        }

        setLevel(slide);
    }

    private void setLevel(int n) {

        switch (n) {
            case 1:
                Log.e("level", "1");
                break;
            case 2:
                firstInfo.setEnabled(false);
                send.setEnabled(true);
                break;
            case 3:
                send.setEnabled(false);
                confirmInfo.setEnabled(true);
                break;
            case 4:
                confirmInfo.setEnabled(false);
                trackingCode.setEnabled(true);
                break;
        }
    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ThirdInsurance_InfoFragment thirdInsurance_infoFragment = new ThirdInsurance_InfoFragment();
        fragmentTransaction.replace(R.id.frameLayout, thirdInsurance_infoFragment).commit();
    }

}