package com.mohammadkz.bimix.Fragment.ThirdInsurance;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Activity.ThirdInsuranceActivity;
import com.mohammadkz.bimix.Fragment.TrackingCodeFragment;
import com.mohammadkz.bimix.Model.RequestResponse;
import com.mohammadkz.bimix.Model.ThirdInsurance;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

import static android.content.Context.MODE_PRIVATE;


public class ThirdInsurance_ConfirmFragment extends Fragment {

    View view;
    ThirdInsurance thirdInsurance;
    TextView useFor, lastCompany, endDate, insuranceID;
    Button send;
    User user;
    ProgressDialog progressDialog;
    ApiConfig request;

    public ThirdInsurance_ConfirmFragment(ThirdInsurance thirdInsurance) {
        // Required empty public constructor
        this.thirdInsurance = thirdInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third_insurance_confirm, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        controllerViews();
        setValue();

        return view;
    }

    private void initViews() {
        lastCompany = view.findViewById(R.id.lastCompany);
        useFor = view.findViewById(R.id.useFor);
        endDate = view.findViewById(R.id.endDate);
        insuranceID = view.findViewById(R.id.insuranceID);
        send = view.findViewById(R.id.send);
    }

    private void controllerViews() {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api();
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

    private void Api() {
        progressDialog.show();

        setUser();
        thirdInsurance.setTrackingCode(generateTrackingCode());
        thirdInsurance.setDate(getDate());

//        Call<RequestResponse> get = request.req_thirdInsurance(user.getID(), thirdInsurance.getEndDate(), thirdInsurance.getLastCompany(), thirdInsurance.getUseFor(), thirdInsurance.getInsurancePic(), thirdInsurance.getOnCarCardPic(),
//                thirdInsurance.getBackCarCardPic(), thirdInsurance.getTrackingCode(), user.getAuth(), thirdInsurance.getInsuranceID(), thirdInsurance.getBackCertificatePic(), thirdInsurance.getOnCertificatePic(), thirdInsurance.getDate());
        Call<RequestResponse> get = request.req_thirdInsurance(user.getID(), thirdInsurance.getEndDate(), thirdInsurance.getLastCompany(), thirdInsurance.getUseFor(), thirdInsurance.getInsurancePic(), thirdInsurance.getOnCarCardPic(),
                thirdInsurance.getBackCarCardPic(), thirdInsurance.getTrackingCode(), user.getAuth(), thirdInsurance.getInsuranceID(), thirdInsurance.getBackCertificatePic(), thirdInsurance.getOnCertificatePic(), thirdInsurance.getDate());

        get.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                if (response.isSuccessful() && response.body().getCode().equals("5")) {
                    progressDialog.dismiss();
                    Log.e("error", " " + response.body().getCode());
                    trackingCode();
                } else {
                    Log.e("error", " " + response.body().getCode());
                    progressDialog.dismiss();
                    StaticFun.alertDialog_connectionFail(getContext());
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                t.getMessage();
                progressDialog.dismiss();
                StaticFun.alertDialog_connectionFail(getContext());
            }
        });

    }

    //get today date
    private String getDate() {
        PersianDate persianDate = new PersianDate();
        int month = persianDate.getShMonth();
        int day = persianDate.getShDay();
        int year = persianDate.getShYear();

        return year + "/" + month + "/" + day;
    }

    private void setValue() {
        lastCompany.setText(thirdInsurance.getLastCompany());
        useFor.setText(thirdInsurance.getUseFor());
        endDate.setText(thirdInsurance.getEndDate());
        insuranceID.setText(thirdInsurance.getInsuranceID());
    }

    // show the tracking code page
    private void trackingCode() {

        ((ThirdInsuranceActivity) getActivity()).setSeekBar(4);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        TrackingCodeFragment trackingCodeFragment = new TrackingCodeFragment(thirdInsurance.getTrackingCode());
        fragmentTransaction.replace(R.id.frameLayout, trackingCodeFragment).commit();
    }

}