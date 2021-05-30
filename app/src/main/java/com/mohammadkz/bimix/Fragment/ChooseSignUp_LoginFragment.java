package com.mohammadkz.bimix.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mohammadkz.bimix.Activity.LoginActivity;
import com.mohammadkz.bimix.Activity.SignUpActivity;
import com.mohammadkz.bimix.Activity.StartUpActivity;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;


public class ChooseSignUp_LoginFragment extends Fragment {

    View view;
    Button signUp, login;

    public ChooseSignUp_LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choose_sign_up_login, container, false);

        initViews();
        controllerViews();

        return view;
    }

    // init views
    private void initViews() {
        login = view.findViewById(R.id.login);
        signUp = view.findViewById(R.id.signUp);
    }

    //controller views
    private void controllerViews() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });

    }
}