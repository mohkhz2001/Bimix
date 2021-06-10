package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mohammadkz.bimix.StaticFun;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    EditText firstName, lastName, phoneNumber, password, re_password;
    Button sendCode;

    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        controllerViews();

    }

    // init component
    private void initViews() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        phoneNumber = findViewById(R.id.phoneNumber);
        sendCode = findViewById(R.id.sendCode);
    }

    private void controllerViews() {
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setValues();//will be remove and used just for test

                // check re-pass & pass are the same and is check the connection
                if (password.getText().toString().equals(re_password.getText().toString()) && isNetworkAvailable() && checkValue()) {
                    sendCode();
                    transferData(firstName.getText().toString() + " " + lastName.getText().toString(), phoneNumber.getText().toString(), password.getText().toString());
                } else {
                    if (checkValue()) {
                        Toast.makeText(SignUpActivity.this, "وارد کردن همه ی موارد الزامی است", Toast.LENGTH_SHORT).show();
                    } else if (isNetworkAvailable())
                        StaticFun.alertDialog_connectionFail(SignUpActivity.this);
                    else if (password.getText().toString().equals(re_password.getText().toString()))
                        Toast.makeText(SignUpActivity.this, "پسوورد های وارد شده یکی نمی باشند", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // convert inputed data to json code then send it to another activity
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

    // generate the code for send sms to confirm phone number
    public int getRandomNumberString() {
        // It will generate 4 digit random Number.
        // from 0 to 9999
        String number = "";
        while (number.length() != 4) {
            Random rnd = new Random();
            number = Integer.toString(rnd.nextInt(9999));
        }
        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
        // this will convert any number sequence into 6 character.

        return Integer.valueOf(String.format("%04d", Integer.parseInt(number)));
    }

    // check internet connection
    public boolean isNetworkAvailable() {
        // Get Connectivity Manager class object from Systems Service
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network Info from connectivity Manager
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean checkValue() {
        if (firstName.getText().length() < 1 || lastName.getText().length() < 1 || phoneNumber.getText().length() < 1 || password.getText().length() < 1 || re_password.getText().length() < 1)
            return false;
        else
            return true;
    }

}