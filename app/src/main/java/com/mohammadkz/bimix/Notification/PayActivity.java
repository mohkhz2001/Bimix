package com.mohammadkz.bimix.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.mohammadkz.bimix.Fragment.PayFragment;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import org.json.JSONObject;

public class PayActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        setUser();
        start();

    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PayFragment payFragment = new PayFragment(user);
        fragmentTransaction.replace(R.id.frameLayout, payFragment).commit();
    }

    private void setUser() {
        user = new User();
        SharedPreferences sh = getSharedPreferences("user_info", MODE_PRIVATE);
        String data = sh.getString("user_info", null);

        Log.e("user", " " + data);
        if (data != null)
            try {
                JSONObject jsonObject = new JSONObject(data);
                user = new User();
                user.setAuth(jsonObject.getString("auth"));
                user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                user.setName(jsonObject.getString("name"));
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}