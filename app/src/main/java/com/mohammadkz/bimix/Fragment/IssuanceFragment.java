package com.mohammadkz.bimix.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Activity.FireInsuranceActivity;
import com.mohammadkz.bimix.Activity.MainPageActivity;
import com.mohammadkz.bimix.Activity.ThirdInsuranceActivity;
import com.mohammadkz.bimix.Model.ThirdInsurance;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IssuanceFragment extends Fragment {

    View view;
    User user;
    ProgressDialog progressDialog;
    ApiConfig request;

    ImageView bodyInsurance, thirdInsurance, docInsurance, personInsurance, elevatorInsurance,
            lifeInsurance, fireInsurance, damageInsurance;

    public IssuanceFragment(User user) {
        // Required empty public constructor
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issuance, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("منتظر باشید...");

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerView();
        getData();

        return view;
    }

    private void initViews() {
        bodyInsurance = view.findViewById(R.id.bodyInsurance);
        thirdInsurance = view.findViewById(R.id.thirdInsurance);
        docInsurance = view.findViewById(R.id.docInsurance);
        personInsurance = view.findViewById(R.id.personInsurance);
        elevatorInsurance = view.findViewById(R.id.elevatorInsurance);
        lifeInsurance = view.findViewById(R.id.lifeInsurance);
        fireInsurance = view.findViewById(R.id.fireInsurance);
        damageInsurance = view.findViewById(R.id.damageInsurance);

    }

    private void controllerView() {

        bodyInsurance.setOnClickListener(v -> {
            if (checkProfile()) {
                progressDialog.dismiss();
                Intent intent = new Intent(getActivity(), BodyInsuranceActivity.class);
                startActivity(intent);
            } else
                alertDialog();
        });

        thirdInsurance.setOnClickListener(v -> {
            if (checkProfile()) {
                progressDialog.dismiss();
                Intent intent = new Intent(getActivity(), ThirdInsuranceActivity.class);
                startActivity(intent);
            } else
                alertDialog();
        });

        docInsurance.setOnClickListener(v -> {

        });

        personInsurance.setOnClickListener(v -> {

        });

        elevatorInsurance.setOnClickListener(v -> {

        });

        lifeInsurance.setOnClickListener(v -> {

        });

        fireInsurance.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity() , FireInsuranceActivity.class);
//            startActivity(intent);
        });

        damageInsurance.setOnClickListener(v -> {

        });

    }

    private boolean checkProfile() {
        progressDialog.show();
        boolean check = false;
        if (user != null)
            if (user.getIdCard().equals("-")) {
                check = false;
            } else
                check = true;

        return check;
    }

    private void getData() {
        Call<User> getUser = request.getUser(user.getAuth());

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("test", " " + response.message());
                user.setBirthdayDate(response.body().getBirthdayDate());
                user.setIdCard(response.body().getIdCard());
                user.setIdCardImage(response.body().getIdCardImage());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("test", " " + t.getMessage());
                System.out.println();
                StaticFun.alertDialog_connectionFail(getContext());
                progressDialog.dismiss();
            }
        });
    }

    private void alertDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("حساب کاربری");
        builder.setMessage("قبل از درخواست صدور باید پروفایل خود را تکمیل نمایید.");
        String positive = "بستن";
        String openProfile = "رفتن به پروفایل";

        // have one btn ==> close
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(openProfile, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((MainPageActivity) getActivity()).profile();
            }
        });

        builder.show();
    }

}