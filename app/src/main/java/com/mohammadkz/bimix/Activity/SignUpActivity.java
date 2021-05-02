package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    EditText name, phoneNumber, password, re_password;
    Button sendCode;

    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        controllerViews();

    }

    private void initViews() {
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        phoneNumber = findViewById(R.id.phoneNumber);
        sendCode = findViewById(R.id.sendCode);
    }

    private void controllerViews() {
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
                if (password.getText().toString().equals(re_password.getText().toString())) {
                    sendCode();
                    transferData(name.getText().toString(), phoneNumber.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    private void transferData(String name, String phoneNumber, String password) {

        User user = new User(name, password, phoneNumber);
        Gson gson = new Gson();
        String userToTransfer = gson.toJson(user);
        Log.e("user", " " + userToTransfer);
        Intent intent = new Intent(SignUpActivity.this, ConfirmPhoneActivity.class);
        intent.putExtra("user", userToTransfer);
        intent.putExtra("code", code);
        startActivity(intent);
        finish();
    }

    private void sendCode() {
        code = getRandomNumberString();

    }// send code to phone number

    public int getRandomNumberString() {
        // It will generate 4 digit random Number.
        // from 0 to 9999
        String number = "";
        while (number.length() != 4) {
            Random rnd = new Random();
            number = Integer.toString(rnd.nextInt(9999));
        }
        Toast.makeText(getApplicationContext() , number , Toast.LENGTH_LONG).show();
        // this will convert any number sequence into 6 character.

        return Integer.valueOf(String.format("%04d", Integer.parseInt(number)));
    }

    private void setValues(){
        name.setText("mehdi");
        password.setText("admin");
        re_password.setText("admin");
        phoneNumber.setText("09388209270");
    }

}