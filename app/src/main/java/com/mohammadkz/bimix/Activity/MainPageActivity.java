package com.mohammadkz.bimix.Activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Fragment.AboutFragment;
import com.mohammadkz.bimix.Fragment.ContactFragment;
import com.mohammadkz.bimix.Fragment.GetPriceFragment;
import com.mohammadkz.bimix.Fragment.HistoryFragment;
import com.mohammadkz.bimix.Fragment.IssuanceFragment;
import com.mohammadkz.bimix.Fragment.PayFragment;
import com.mohammadkz.bimix.Fragment.ProfileFragment;
import com.mohammadkz.bimix.Model.CheckResult;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.Notification.NotificationReceiver;
import com.mohammadkz.bimix.Notification.PayActivity;
import com.mohammadkz.bimix.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mohammadkz.bimix.Notification.App.CHANNEL_1_ID;

public class MainPageActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    TextView title;
    View header;
    NavigationView nav_view;
    TextView userName, userPoint, header_point;
    MaterialToolbar topAppBar;
    DrawerLayout drawer_layout;
    ImageView nav_image;
    private User user;
    private NotificationManagerCompat notificationManager;
    ApiConfig request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        notificationManager = NotificationManagerCompat.from(this);
        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();
        setUser();
        setValue();
        start();
        checkServer();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.openDrawerContentRes, R.string.closeDrawerContentRes);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViews() {
        frameLayout = findViewById(R.id.frameLayout);
        title = findViewById(R.id.title);
        nav_view = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        topAppBar = findViewById(R.id.topAppBar);
        nav_image = findViewById(R.id.nav_image);

        // header
        header = nav_view.getHeaderView(0);
        userName = header.findViewById(R.id.user_name);
        userPoint = header.findViewById(R.id.user_point);
        header_point = header.findViewById(R.id.header_point);

    }

    private void controllerViews() {

        title.setOnClickListener(v -> {
            Log.e("test", "shiit");
        });

        topAppBar.setNavigationOnClickListener(v -> {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START);
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }

        });

        nav_image.setOnClickListener(v -> {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START);
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_price:
                        setTitle("نرخ دهی آنلاین");
                        GetPriceFragment getPriceFragment = new GetPriceFragment();
                        fragmentTransaction.replace(R.id.frameLayout, getPriceFragment);
                        fragmentTransaction.commit();

                        break;
                    case R.id.nav_profile:
                        profile();

                        break;

                    case R.id.nav_pay:
                        payFragment();

                        break;
                    case R.id.history:
                        setTitle("تاریخچه خرید");
                        HistoryFragment historyFragment = new HistoryFragment(user);
                        fragmentTransaction.replace(R.id.frameLayout, historyFragment).commit();

                        break;
                    case R.id.nav_issuance:
                        setTitle("درخواست صدور");
                        IssuanceFragment issuanceFragment = new IssuanceFragment(user);
                        fragmentTransaction.replace(R.id.frameLayout, issuanceFragment);
                        fragmentTransaction.commit();

                        break;

                    case R.id.about:
                        setTitle("درباره ما");
                        AboutFragment aboutFragment = new AboutFragment();
                        fragmentTransaction.replace(R.id.frameLayout, aboutFragment).commit();
                        break;

                    case R.id.contact:
                        setTitle("ارتباط با ما");
                        ContactFragment contactFragment = new ContactFragment();
                        fragmentTransaction.replace(R.id.frameLayout, contactFragment).commit();
                        break;

                    case R.id.logout:
                        removeSharedPreferences();
                        finish();
                        Intent intent = new Intent(MainPageActivity.this, StartUpActivity.class);
                        startActivity(intent);
                        break;
                }

                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        IssuanceFragment issuanceFragment = new IssuanceFragment(user);
        fragmentTransaction.replace(R.id.frameLayout, issuanceFragment);
        fragmentTransaction.commit();

    }

    private void setUser() {
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

    // set title from this activity & fragment
    public void setTitle(String title) {
        this.title.setText(title);
    }

    //set value to the navigation
    private void setValue() {
        userName.setText(user.getName());
        header_point.setText(user.getPoint());
        header_point.setVisibility(View.VISIBLE);
    }

    public void profile() {
        setTitle("حساب کاربری");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment(user.getAuth());
        fragmentTransaction.replace(R.id.frameLayout, profileFragment).commit();
        nav_view.setCheckedItem(R.id.nav_profile);
    }

    public void setNavSelected(@IdRes int id) {
        nav_view.setCheckedItem(id);
    }

    private void sendOnChannel1(String code, String message) {


        Intent intent = new Intent(MainPageActivity.this, PayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("payCode", code);


        PendingIntent actionIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_done)
                .setContentTitle("آماده ی پرداخت")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

    private void checkServer() {
        Call<List<CheckResult>> get = request.checkResult(user.getAuth());

        get.enqueue(new Callback<List<CheckResult>>() {
            @Override
            public void onResponse(Call<List<CheckResult>> call, Response<List<CheckResult>> response) {
                resultSearch(response.body());
            }

            @Override
            public void onFailure(Call<List<CheckResult>> call, Throwable t) {
                t.getMessage();
            }
        });

    }

    private void resultSearch(List<CheckResult> New) {

        for (int i = 0; i < New.size(); i++) {
            sendOnChannel1(New.get(i).getPayCode(), "کد پیگیری:" + New.get(i).getTrackingCode());
        }

    }

    public void payFragment() {
        setTitle("پرداخت بیمه نامه");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PayFragment payFragment = new PayFragment(user);
        fragmentTransaction.replace(R.id.frameLayout, payFragment).commit();
    }

    private void removeSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}