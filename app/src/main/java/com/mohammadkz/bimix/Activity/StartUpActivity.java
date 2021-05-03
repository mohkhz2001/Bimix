package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mohammadkz.bimix.R;

public class StartUpActivity extends AppCompatActivity {

    Button signUp, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        initViews();
        controllerViews();

    }

    private void initViews() {
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
    }

    private void controllerViews() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUpActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}