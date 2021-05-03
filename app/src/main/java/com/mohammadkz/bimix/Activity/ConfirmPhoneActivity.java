package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.ErrorHandler;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPhoneActivity extends AppCompatActivity {

    int codeToConfirm;
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    User user;

    TextInputEditText code1, code2, code3, code4;
    TextView reSend, minute, second;
    Button confirm;
    SpinKitView spin_kit;
    ApiConfig request;
    int timer = 120, m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);

        getDate();
        initViews();
        controllerViews();
        flipTimer();

    }

    // init component
    private void initViews() {
        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        reSend = findViewById(R.id.reSend);
        confirm = findViewById(R.id.confirm);
        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);
        spin_kit = findViewById(R.id.spin_kit);
    }

    /*
    action controller like:
    change focus of edit text to next one
    click action on btn and text view
     */
    private void controllerViews() {

        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getApplicationContext() , (int)s)
                if (code1.length() == 1) {
                    code1.clearFocus();
                    code2.requestFocus();
                    code2.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code1.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //on backspace
                code1.setText("");

            }
            return false;
        });

        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (code2.length() == 1) {
                    code2.clearFocus();
                    code3.requestFocus();
                    code3.setCursorVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code2.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //on backspace
                if (code2.length() == 0) {
                    code2.clearFocus();
                    code1.requestFocus();
                    code1.setCursorVisible(true);
                } else {
                    code2.setText(null);
                }

            }

            return false;
        });

        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code3.length() == 1) {
                    code3.clearFocus();
                    code4.requestFocus();
                    code4.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code3.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //on backspace
                if (code3.length() == 0) {
                    code3.clearFocus();
                    code2.requestFocus();
                    code2.setCursorVisible(true);
                } else {
                    code3.setText(null);
                }

            }

            return false;
        });

        code4.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                //on backspace
                if (code4.length() == 0) {
                    code4.clearFocus();
                    code3.requestFocus();
                    code3.setCursorVisible(true);
                } else {
                    code4.setText(null);
                }

            }

            return false;
        });

        reSend.setOnClickListener(v -> {
                    disActiveLayout();
                    reSendCode();
                }
        );

        confirm.setOnClickListener(v -> {
            disActiveLayout();
            //check internet connection
            if (isNetworkAvailable()) {
                String input = code1.getText().toString() + code2.getText().toString() + code3.getText().toString() + code4.getText().toString(); // save all input ti one

                try {
                    if (input.length() != 4) {  //if length not equal with 4
                        Toast.makeText(getApplicationContext(), "کد وارد شده کامل نمی باشد. لطفا کد دریافتی را وارد کنید", Toast.LENGTH_LONG).show();
                        activeLayout();
                    } else if (codeToConfirm == Integer.parseInt(input)) { // if input correct
                        // if ok
                        generateAuth();
                        completeRegister();
                    } else if (codeToConfirm != Integer.parseInt(input)) { // if input not-correct
                        Toast.makeText(getApplicationContext(), "کد وارد شده اشتباه می باشد. کد دیگری برای شما ارسال شد.", Toast.LENGTH_SHORT).show();
                        reSendCode();
                    }

                } catch (Exception e) {
                    e.getMessage();
                    if (input.equals("")) {
                        Toast.makeText(getApplicationContext(), "لطفا کد دریافتی را وارد کنید", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                ErrorHandler.alertDialog_connectionFail(ConfirmPhoneActivity.this);
                activeLayout();
            }

        });

    }

    private void reSendCode() {
        Toast.makeText(getApplicationContext(), " " + codeToConfirm, Toast.LENGTH_SHORT).show();
        /*

        api to sib sms

         */

        activeLayout();
        flipTimer();
    }// request to send that code

    // get data from sign up page(intent)
    private void getDate() {
        codeToConfirm = getIntent().getIntExtra("code", 0);

        String data = getIntent().getStringExtra("user");
        Log.e("user", " " + data);
        if (data != null)
            try {
                JSONObject jsonObject = new JSONObject(data);
                user = new User();
                user.setPassword(jsonObject.getString("password"));
                user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                user.setName(jsonObject.getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    // send to DB
    private void completeRegister() {
        request = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<LoginResponse> send = request.SignUp(user.getPhoneNumber(), user.getName(), user.getPassword(), user.getAuth());
        send.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("response", " " + response);
                Log.e("code", " " + response.body().getCode());
                sharedPreferences();

                Intent intent = new Intent(ConfirmPhoneActivity.this, SplashScreenConfirmActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ErrorHandler.alertDialog_connectionFail(ConfirmPhoneActivity.this);
                activeLayout();
            }
        });


    }

    // generate auth code
    private void generateAuth() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        user.setAuth(base64Encoder.encodeToString(randomBytes));
    }

    // save user to login auto
    private void sharedPreferences() {
        SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String userToTransfer = gson.toJson(user);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("user_info", userToTransfer);
        myEdit.commit();
    }

    // countdown timer ==> 2 min
    private void flipTimer() {
        reSend.setEnabled(false);
        timer = 120;
        CountDownTimer countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                m = 0;
                int time = timer--;
                if (time <= 60) {
                    m = 0;
                } else
                    while (time >= 60) {
                        m++;
                        time -= 60;
                    }

                minute.setText(String.valueOf(m));
                if (time < 10)
                    second.setText("0" + time);
                else
                    second.setText(String.valueOf(time));
            }

            @Override
            public void onFinish() {
                minute.setText(String.valueOf(00));
                second.setText(String.valueOf(00));
                reSend.setEnabled(true);
            }
        }.start();

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

    // after push any btn like re send code || confirm ==> spin kit will be disappear and other will be disable
    private void disActiveLayout() {
        spin_kit.setVisibility(View.VISIBLE);
        code1.setEnabled(false);
        code2.setEnabled(false);
        code3.setEnabled(false);
        code4.setEnabled(false);
        reSend.setEnabled(false);
        confirm.setEnabled(false);
    }

    // when the work is over => spin kit will be disappear and component will be enable
    private void activeLayout() {
        spin_kit.setVisibility(View.GONE);
        code1.setEnabled(true);
        code2.setEnabled(true);
        code3.setEnabled(true);
        code4.setEnabled(true);
        reSend.setEnabled(true);
        confirm.setEnabled(true);
    }

}