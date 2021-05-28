package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.StaticFun;
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
    SpinKitView spin_kit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();
    }

    //init component
    private void initViews() {
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        spin_kit = findViewById(R.id.spin_kit);
    }

    private void controllerViews() {

        login.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                disActiveLayout();
                checkUserInput(phoneNumber.getText().toString(), password.getText().toString());
            } else {
                StaticFun.alertDialog_connectionFail(LoginActivity.this);
            }

        });

    }

    // API request
    private void checkUserInput(String phoneNumber, String password) {

        Call<LoginResponse> getData = request.Login(phoneNumber, password);
//        Call<LoginResponse> getData = request.Login(phoneNumber, password);
        getData.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful())
                    sharedPreferences(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.getMessage();
                StaticFun.alertDialog_connectionFail(LoginActivity.this);
                activeLayout();
            }
        });

    }

    // save user data
    private void sharedPreferences(LoginResponse response) {

        // changed of ==>  response.getCode().equals("1")
        if (response.getCode().equals("1")) {

            User user = new User(response.getID(), response.getAuth(), response.getName(), phoneNumber.getText().toString());
//            User user = new User("1", "RLXhB_UoikPTfD5p8GG8Anp9H1CJnVFN", "محمدمهدی خواجه زاده", "09388209270");
            SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
            Gson gson = new Gson();
            String userToTransfer = gson.toJson(user);
            sh.getString("user_info", userToTransfer);
            SharedPreferences.Editor myEdit = sh.edit();
            myEdit.putString("user_info", userToTransfer);
            myEdit.commit();
            System.out.println();

            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();

        } else {
            StaticFun.alertDialog_error_login(LoginActivity.this);
            activeLayout();
        }

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

    // after push any btn like login || confirm ==> spin kit will be disappear and other will be disable
    private void disActiveLayout() {
        spin_kit.setVisibility(View.VISIBLE);
        phoneNumber.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    // when the work is over => spin kit will be disappear and component will be enable
    private void activeLayout() {
        spin_kit.setVisibility(View.GONE);
        phoneNumber.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

}