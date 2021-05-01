package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumber, password;
    Button login;
    ApiConfig request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();
    }

    private void initViews() {
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }

    private void controllerViews() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserInput(phoneNumber.getText().toString(), password.getText().toString());

            }
        });

    }

    private void checkUserInput(String phoneNumber, String password) {

        Call<LoginResponse> getData = request.Login("09388209270", "admin");
//        Call<LoginResponse> getData = request.Login(phoneNumber, password);
        getData.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful())
                    transferData(response.body());
                else {

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.getMessage();
            }
        });

    }

    private void transferData(LoginResponse response) {

        if (response.getCode().equals("1")) {


        } else {

        }

    }

    private void sharedPreferences(User user) {
        SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String userToTransfer = gson.toJson(user);
        sh.getString("user_info", userToTransfer);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("user_info", userToTransfer);
        myEdit.commit();
        System.out.println();
    }
}