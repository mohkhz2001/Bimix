package com.mohammadkz.bimix.Fragment.FireInsurance;

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
import com.mohammadkz.bimix.Fragment.TrackingCodeFragment;
import com.mohammadkz.bimix.Model.BodyInsurance;
import com.mohammadkz.bimix.Model.FireInsurance;
import com.mohammadkz.bimix.Model.RequestResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;
import com.mohammadkz.bimix.StaticFun;

import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FireInsurance_ConfirmFragment extends Fragment {

    View view;
    FireInsurance fireInsurance;
    TextView buildingType, typeOfStructure, lifeTime, price, area, postCode, address;
    Button confirm;
    ApiConfig request;
    User user;

    public FireInsurance_ConfirmFragment(FireInsurance fireInsurance) {
        // Required empty public constructor
        this.fireInsurance = fireInsurance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fire_insurance_confirm, container, false);

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        initViews();
        setValue();
        controllerViews();

        return view;
    }

    //init views
    private void initViews() {
        buildingType = view.findViewById(R.id.buildingType);
        typeOfStructure = view.findViewById(R.id.typeOfStructure);
        lifeTime = view.findViewById(R.id.lifeTime);
        price = view.findViewById(R.id.price);
        postCode = view.findViewById(R.id.postCode);
        address = view.findViewById(R.id.address);
        confirm = view.findViewById(R.id.confirm);

    }

    private void controllerViews() {

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api();
            }
        });

    }

    // set value to textView
    private void setValue() {
        buildingType.setText(fireInsurance.getBuildingType());
        typeOfStructure.setText(fireInsurance.getTypeOfStructure());
        lifeTime.setText(fireInsurance.getLifeTime());
        price.setText(fireInsurance.getPrice());
        postCode.setText(fireInsurance.getPostCode());
        address.setText(fireInsurance.getAddress());
        area.setText(fireInsurance.getArea());
    }

    // send inputted info to server
    private void Api() {
        setUser();

        fireInsurance.setTrackingCode(generateTrackingCode());

        Call<RequestResponse> api = request.req_fireInsurance(user.getID(), user.getAuth(), fireInsurance.getTypeOfStructure(), fireInsurance.getBuildingType(), fireInsurance.getPrice(),
                fireInsurance.getArea(), fireInsurance.getLifeTime(), fireInsurance.getPostCode(), fireInsurance.getAddress(), fireInsurance.getTrackingCode());

        api.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                Log.e("check", " " + response.message());
                Log.e("check", " " + response.body().getCode());

                if (response.body().getCode().equals("5")) {
                    TrackingCodeFragment trackingCodeFragment = new TrackingCodeFragment(fireInsurance.getTrackingCode());
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, trackingCodeFragment).commit();
                } else {
                    StaticFun.alertDialog_connectionFail(getContext());
                }

            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                StaticFun.alertDialog_connectionFail(getContext());
            }
        });

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

    //generate tracking code
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
}