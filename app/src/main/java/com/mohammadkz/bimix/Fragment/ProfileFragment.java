package com.mohammadkz.bimix.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    View view;
    User user;
    Button save;
    TextInputEditText name, birthdayDate, idCard, phoneNumber;
    ApiConfig request;
    ImageView idCardImage;
    String mediaPath;
    TextView text_click, Error;
    ProgressDialog progressDialog;
    TextInputLayout date_layout, idCard_layout;
    Bitmap img;

    public ProfileFragment(String auth) {
        // Required empty public constructor
        user = new User();
        user.setAuth(auth);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getContext());

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();

        getData();

        return view;
    }

    private void initViews() {
        name = view.findViewById(R.id.name);
        save = view.findViewById(R.id.save);
        birthdayDate = view.findViewById(R.id.birthdayDate);
        idCard = view.findViewById(R.id.idCard);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        text_click = view.findViewById(R.id.text_click);
        idCardImage = view.findViewById(R.id.idCardImage);
        Error = view.findViewById(R.id.Error);
        date_layout = view.findViewById(R.id.date_layout);
        idCard_layout = view.findViewById(R.id.idCard_layout);
    }

    private void controllerViews() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                user.setIdCard(idCard.getText().toString());
                user.setBirthdayDate(birthdayDate.getText().toString());

                if (user.getIdCard().equals("-") && img == null && user.getBirthdayDate().equals("-")) {
                    progressDialog.dismiss();
                    if (user.getIdCard().equals("-")) {
                        date_layout.setErrorEnabled(true);
                        date_layout.setError("نمیتواند خالی باشد.");
                    } else {
                        date_layout.setErrorEnabled(false);
                    }

                    if (img == null) {
                        Error.setVisibility(View.VISIBLE);
                    } else {
                        Error.setVisibility(View.GONE);
                    }


                    if (user.getBirthdayDate().equals("-")) {
                        idCard_layout.setErrorEnabled(true);
                        idCard_layout.setError("نمیتواند خالی باشد.");
                    } else {
                        idCard_layout.setErrorEnabled(false);
                    }
                    progressDialog.dismiss();
                } else {
//                    DownloadImagesTask downloadImagesTask = new DownloadImagesTask();
//                    user.setIdCardImage(downloadImagesTask.doInBackground(img));
                    sendDate();
                }

            }
        });

        idCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getData() {
        progressDialog.setMessage("درحال دریافت...");
        progressDialog.show();
        Call<User> getUser = request.getUser(user.getAuth());

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.body().getName().equals("")) {
                    response.body().setAuth(user.getAuth());
                    user = response.body();
                    user.setIdCardImage(null); // disable this option
                    setValue(user);

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println();

                progressDialog.dismiss();
            }
        });
    }

    private void setValue(User user) {
        name.setText(user.getName());
        birthdayDate.setText(user.getBirthdayDate());
        idCard.setText(user.getIdCard());
        phoneNumber.setText(user.getPhoneNumber());
        name.setText(user.getName());
        name.setText(user.getName());

    }

    private void sendDate() {
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.show();
        Log.e("auth", " " + user.getAuth());
        Call<LoginResponse> update = request.updateUser(user.getAuth(), "-", user.getIdCard(), user.getBirthdayDate());

        update.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getCode().equals("0")) {
                    System.out.println();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), " اطلاعات شما با موفقیت تغییر کرد ", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), " اشکال ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println();
                progressDialog.dismiss();
                StaticFun.alertDialog_connectionFail(getContext());
            }
        });
    }



}