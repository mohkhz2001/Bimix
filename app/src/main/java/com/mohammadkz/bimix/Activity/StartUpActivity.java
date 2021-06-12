package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Fragment.ChooseSignUp_LoginFragment;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartUpActivity extends AppCompatActivity {

    User user;
    ApiConfig request;
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("در حال ورود خودکار");

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        getUserLogin();

    }

    // get user login info
    // read from share preferences
    private void getUserLogin() {
        SharedPreferences sh = getSharedPreferences("userLogin", MODE_PRIVATE);
        String data = sh.getString("userLogin", null);

        Log.e("user", " " + data);
        if (data != null) {
            progressDialog.show(); // show progress
            try {
                // decode from json
                JSONObject jsonObject = new JSONObject(data);
                user = new User();
                user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                user.setPassword(jsonObject.getString("password"));

                login();
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
                ChooseSignUp_LoginFragment chooseSignUp_loginFragment = new ChooseSignUp_LoginFragment();
                fragmentTransaction.replace(R.id.frameLayout, chooseSignUp_loginFragment).commit();
            }
        } else {// if data is null sholud start choose signup or login page

            ChooseSignUp_LoginFragment chooseSignUp_loginFragment = new ChooseSignUp_LoginFragment();
            fragmentTransaction.replace(R.id.frameLayout, chooseSignUp_loginFragment).commit();

        }

    }

    // login with username & password
    private void login() {
        Call<LoginResponse> get = request.Login(user.getPhoneNumber(), user.getPassword());

        get.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                successfulLogin(response.body());

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                ChooseSignUp_LoginFragment chooseSignUp_loginFragment = new ChooseSignUp_LoginFragment();
                fragmentTransaction.replace(R.id.frameLayout, chooseSignUp_loginFragment).commit();
            }
        });
    }

    // if login is ok
    private void successfulLogin(LoginResponse loginResponse) {
        progressDialog.dismiss();
        if (loginResponse.getCode().equals("1")) {

            Intent intent = new Intent(StartUpActivity.this, MainPageActivity.class);
            finish();
            startActivity(intent);

        } else {

            Toast.makeText(getApplicationContext(), "ورود خودکار موفقیت آمیز نبود.", Toast.LENGTH_LONG).show();
            ChooseSignUp_LoginFragment chooseSignUp_loginFragment = new ChooseSignUp_LoginFragment();
            fragmentTransaction.replace(R.id.frameLayout, chooseSignUp_loginFragment).commit();

        }
    }

}