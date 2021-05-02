package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
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
    TextView reSend;
    Button confirm;
    ApiConfig request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);

        getDate();
        initViews();
        controllerViews();

    }

    private void initViews() {
        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        reSend = findViewById(R.id.reSend);
        confirm = findViewById(R.id.confirm);
    }

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

        code1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //on backspace
                    code1.setText("");

                }
                return false;
            }
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

        code2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
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

        code3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
        });

        code4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
        });

        reSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSendCode();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = code1.getText().toString() + code2.getText().toString() + code3.getText().toString() + code4.getText().toString();


                if (codeToConfirm == Integer.parseInt(input)) {
                    // if ok
                    generateAuth();
                    completeRegister();
                }
            }
        });

    }

    private void reSendCode() {
        Toast.makeText(getApplicationContext(), " " + codeToConfirm, Toast.LENGTH_SHORT).show();

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

                Intent intent = new Intent(ConfirmPhoneActivity.this, SplashScreeanConfirmActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    // generate auth code
    private void generateAuth() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        user.setAuth(base64Encoder.encodeToString(randomBytes));
    }

    private void sharedPreferences() {
        SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String userToTransfer = gson.toJson(user);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("user_info", userToTransfer);
        myEdit.commit();
    }

}