package com.mohammadkz.bimix.Fragment.BodyInsurance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Activity.BodyInsuranceActivity;
import com.mohammadkz.bimix.Activity.MainPageActivity;
import com.mohammadkz.bimix.Fragment.TrackingCodeFragment;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.Model.LoginResponse;
import com.mohammadkz.bimix.Model.RequestResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

import static android.content.Context.MODE_PRIVATE;


public class BodyInsurance_ConfirmFragment extends Fragment {

    View view;
    BodyInsurance bodyInsurance;
    TextView use_txt, history_txt, year_txt, last_txt, number_txt, model_txt, name_txt, idCard_txt, phoneNumber_txt, birthdayDate_txt;
    AnimatedCheckBox another, installments;
    CardView anotherLayout;
    Button send;
    User user;
    ProgressDialog progressDialog;
    ApiConfig request;

    public BodyInsurance_ConfirmFragment(BodyInsurance bodyInsurance) {
        // Required empty public constructor
        this.bodyInsurance = bodyInsurance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_body_insurance_confirm, container, false);

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");

        initViews();
        controllerViews();
        setValue();

        return view;
    }

    private void initViews() {
        use_txt = view.findViewById(R.id.use_txt);
        history_txt = view.findViewById(R.id.history_txt);
        year_txt = view.findViewById(R.id.year_txt);
        last_txt = view.findViewById(R.id.last_txt);
        number_txt = view.findViewById(R.id.number_txt);
        model_txt = view.findViewById(R.id.model_txt);
        another = view.findViewById(R.id.another);
        installments = view.findViewById(R.id.installments);
        name_txt = view.findViewById(R.id.name_txt);
        idCard_txt = view.findViewById(R.id.idCard_txt);
        phoneNumber_txt = view.findViewById(R.id.phoneNumber_txt);
        birthdayDate_txt = view.findViewById(R.id.birthdayDate_txt);
        anotherLayout = view.findViewById(R.id.anotherLayout);
        send = view.findViewById(R.id.send);
    }

    private void controllerViews() {

        another.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                BodyInsurance.AnotherPerson anotherPerson = new BodyInsurance.AnotherPerson();
                if (isChecked) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet, (LinearLayout) view.findViewById(R.id.bottomSheetContainer));

                    bottomSheetView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //init views
                            TextInputEditText name, phoneNumber, birthdayDate, idCard;
                            name = bottomSheetDialog.findViewById(R.id.name);
                            phoneNumber = bottomSheetDialog.findViewById(R.id.phoneNumber);
                            birthdayDate = bottomSheetDialog.findViewById(R.id.birthdayDate);
                            idCard = bottomSheetDialog.findViewById(R.id.idCard);

                            // set values to body insurance
                            anotherPerson.setName(name.getText().toString());
                            anotherPerson.setPhoneNumber(phoneNumber.getText().toString());
                            anotherPerson.setBirthdayDate(birthdayDate.getText().toString());
                            anotherPerson.setIdCard(idCard.getText().toString());
                            anotherPerson.setUse(true);

                            bodyInsurance.setAnotherPerson(anotherPerson);

                            anotherInsurance();

                            bottomSheetDialog.dismiss();
                        }
                    });

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();

                } else {
                    anotherLayout.setVisibility(View.GONE);
                    anotherPerson.setUse(true);
                    bodyInsurance.setAnotherPerson(anotherPerson);
                }
            }
        });

        installments.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    progressDialog.show();
                    api();
                } else
                    StaticFun.alertDialog_connectionFail(getContext());

            }
        });

    }

    // set info to field
    private void setValue() {
        use_txt.setText(bodyInsurance.getUseFor());
        history_txt.setText(bodyInsurance.getHistory());
        year_txt.setText(bodyInsurance.getYear());
        last_txt.setText(bodyInsurance.getLastCompany());
        number_txt.setText(bodyInsurance.getNumberInsurance());
        model_txt.setText(bodyInsurance.getCarModel());
    }

    // wanna send req for another person
    private void anotherInsurance() {
        anotherLayout.setVisibility(View.VISIBLE);
        name_txt.setText(bodyInsurance.getAnotherPerson().getName());
        idCard_txt.setText(bodyInsurance.getAnotherPerson().getIdCard());
        phoneNumber_txt.setText(bodyInsurance.getAnotherPerson().getPhoneNumber());
        birthdayDate_txt.setText(bodyInsurance.getAnotherPerson().getBirthdayDate());
    }

    // send user req to server
    private void api() {
        setUser();

        bodyInsurance.setTrackingCode(generateTrackingCode());
        String cover = toJson(bodyInsurance.getCover());
        String discount = toJson(bodyInsurance.getOff());

        if (bodyInsurance.getAnotherPerson() == null)
            bodyInsurance.setAnotherPerson(new BodyInsurance.AnotherPerson());

        Log.e("tracking code", " " + bodyInsurance.getTrackingCode());

        Call<RequestResponse> getData = request.req_bodyInsurance(user.getID(), user.getAuth(), bodyInsurance.getTrackingCode(), bodyInsurance.getHistory(), bodyInsurance.getNumberInsurance(), bodyInsurance.getUseFor(), bodyInsurance.getCarModel(),
                bodyInsurance.getLastCompany(), bodyInsurance.getYear(), bodyInsurance.getBackOnCarCard(), bodyInsurance.getOnCarCard(), getDate(), cover, discount, bodyInsurance.getVisit().isInPlace(),
                bodyInsurance.getVisit().getTime(), bodyInsurance.getVisit().getDate(), bodyInsurance.getVisit().getAddress(), bodyInsurance.getAnotherPerson().isUse(), bodyInsurance.getAnotherPerson().getName(),
                bodyInsurance.getAnotherPerson().getIdCard(), bodyInsurance.getAnotherPerson().getPhoneNumber(), bodyInsurance.getAnotherPerson().getBirthdayDate(), false);

        getData.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                Log.e("response", " " + response.body().getCode());
                if (!response.body().getCode().equals("5")) {
                    StaticFun.alertDialog_connectionFail(getContext());
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    trackingCode();
                }


            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                Log.e("response", " " + t.getMessage());
                
                progressDialog.dismiss();

            }
        });
    }

    //generate the random number
    private String generateTrackingCode() {
        boolean unique = true;
        int n = 0;
        while (unique) {
            Random random = new Random();
            n = random.nextInt(9999999);
            if (n < 1000000)
                n += 10000000;
//            unique = checkTrackingCode(Integer.toString(n));
            unique = false;
        }

        return Integer.toString(n);
    }

    //get today date
    private String getDate() {
        PersianDate persianDate = new PersianDate();
        int month = persianDate.getShMonth();
        int day = persianDate.getShDay();
        int year = persianDate.getShYear();

        return year + "/" + month + "/" + day;
    }

    // get user info of SharedPreferences
    private void setUser() {
        SharedPreferences sh = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        String data = sh.getString("user_info", null);

        Log.e("user", " " + data);
        if (data != null)
            try {
                JSONObject jsonObject = new JSONObject(data);
                user = new User();
                user.setAuth(jsonObject.getString("auth"));
                user.setID(jsonObject.getString("ID"));
                user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                user.setName(jsonObject.getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    // need to work and check again // this will check is there this tracking code or not
    private boolean checkTrackingCode(String code) {

        Call<RequestResponse> check = request.req_checkTrackingCode(code);

        check.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                Log.e("check", " " + response.body().getCode());
                Log.e("check-1", " " + response.message());
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                System.out.println();
                Log.e("check-t", " " + t.getMessage());
            }
        });

//        if (res[0].equals("1")) {
//            return true;
//        } else
        return true;
    }

    // arraylist to json
    private String toJson(ArrayList<String> json) {
        String j = new Gson().toJson(json);

        return j;
    }

    // check internet connection
    public boolean isNetworkAvailable() {
        // Get Connectivity Manager class object from Systems Service
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network Info from connectivity Manager
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    private void trackingCode() {
        ((BodyInsuranceActivity) getActivity()).setSeekBar(7);
        TrackingCodeFragment trackingCodeFragment = new TrackingCodeFragment(bodyInsurance.getTrackingCode());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, trackingCodeFragment).commit();

    }

}