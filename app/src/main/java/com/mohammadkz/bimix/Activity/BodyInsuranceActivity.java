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
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.R;
import com.warkiz.widget.IndicatorSeekBar;

public class BodyInsuranceActivity extends AppCompatActivity {

    IndicatorSeekBar seekBar;
    TextView firstInfo, cover, off, visit, send, confirmInfo, trackingCode;
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
        cover = findViewById(R.id.cover);
        off = findViewById(R.id.off);
        visit = findViewById(R.id.visit);
        send = findViewById(R.id.send);
        confirmInfo = findViewById(R.id.confirmInfo);
        trackingCode = findViewById(R.id.trackingCode);
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
            setSeekBar(1);
        });
    }

    public void setSeekBar(int slide) {

        switch (slide){
            case 2:
                seekBar.setProgress(18);
                break;
            case 3:
                seekBar.setProgress(37);
                break;
            case 4:
                seekBar.setProgress(48);
                break;
            case 5:
                seekBar.setProgress(62);
                break;
            case 6:
                seekBar.setProgress(79);
                break;
            case 7:
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
                cover.setEnabled(true);
                break;
            case 3:
                cover.setEnabled(false);
                off.setEnabled(true);
                break;
            case 4:
                off.setEnabled(false);
                visit.setEnabled(true);
                break;
            case 5:
                visit.setEnabled(false);
                send.setEnabled(true);
                break;
            case 6:
                send.setEnabled(false);
                confirmInfo.setEnabled(true);
                break;
            case 7:
                confirmInfo.setEnabled(false);
                trackingCode.setEnabled(true);
                break;
        }
    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BodyInsurance_InfoFragment infoFragment = new BodyInsurance_InfoFragment();
        fragmentTransaction.replace(R.id.frameLayout, infoFragment).commit();
    }
}