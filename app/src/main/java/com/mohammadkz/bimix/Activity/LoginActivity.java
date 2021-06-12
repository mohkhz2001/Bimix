package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumber, password;
    Button login;
    ApiConfig request;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("منتظر باشید...");

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();
    }

    //init component
    private void initViews() {
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }

    private void controllerViews() {

        login.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                Log.e("hash", md5(password.getText().toString()));
                disActiveLayout();
                checkUserInput(phoneNumber.getText().toString(), md5(password.getText().toString()));
            } else {
                StaticFun.alertDialog_connectionFail(LoginActivity.this);
            }

        });

    }

    // API request
    private void checkUserInput(String phoneNumber, String password) {

        Call<LoginResponse> getData = request.Login(phoneNumber, password);

        getData.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getCode().equals("1")) {
                    setUserLogin(password);
                    sharedPreferences(response.body());

                } else {
                    StaticFun.alertDialog_error_login(LoginActivity.this);
                    activeLayout();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                sharedPreferences(new LoginResponse());
                t.getMessage();
                StaticFun.alertDialog_connectionFail(LoginActivity.this);
                activeLayout();
            }
        });

    }

    // save user data
    private void sharedPreferences(LoginResponse response) {

        User user = new User(response.getID(), response.getAuth(), response.getName(), phoneNumber.getText().toString());
        SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String userToTransfer = gson.toJson(user);
        sh.getString("user_info", userToTransfer);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("user_info", userToTransfer);
        myEdit.commit();
        System.out.println();
        activeLayout();
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish();
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
        progressDialog.show();
        phoneNumber.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    // when the work is over => spin kit will be disappear and component will be enable
    private void activeLayout() {
        progressDialog.dismiss();
        phoneNumber.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    //set user login info to share preferences
    private void setUserLogin(String pass) {
        SharedPreferences sh = getSharedPreferences("userLogin", MODE_PRIVATE);
        Gson gson = new Gson();
        User user = new User();
        user.setPassword(pass);
        user.setPhoneNumber(phoneNumber.getText().toString());
        String userToTransfer = gson.toJson(user);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("userLogin", userToTransfer);
        myEdit.commit();
    }

    // hashing password
    public String md5(String password) {
        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();

        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

}