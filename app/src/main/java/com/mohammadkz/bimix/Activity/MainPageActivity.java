package com.mohammadkz.bimix.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.mohammadkz.bimix.Fragment.GetPriceFragment;
import com.mohammadkz.bimix.Fragment.IssuanceFragment;
import com.mohammadkz.bimix.R;

public class MainPageActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    TextView title;
    View header;
    NavigationView nav_view;
    TextView userName, userPoint;
    MaterialToolbar topAppBar;
    DrawerLayout drawer_layout;
    ImageView nav_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initViews();
        controllerViews();
        start();

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

                        break;
                    case R.id.nav_issuance:
                        setTitle("درخواست صدور");
                        IssuanceFragment issuanceFragment = new IssuanceFragment();
                        fragmentTransaction.replace(R.id.frameLayout, issuanceFragment);
                        fragmentTransaction.commit();

                        break;
                }

                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void start() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        IssuanceFragment issuanceFragment = new IssuanceFragment();
        fragmentTransaction.replace(R.id.frameLayout, issuanceFragment);
        fragmentTransaction.commit();

    }

    // set title from this activity & fragment
    public void setTitle(String title) {
        this.title.setText(title);
    }

    //set value to the navigation
    private void setValue() {

    }

}