package com.mohammadkz.bimix.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Adapter.HistoryAdapter;
import com.mohammadkz.bimix.Model.HistoryInsuranceResponse;
import com.mohammadkz.bimix.Model.InsuranceResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {

    View view;
    RecyclerView list;
    private User user;
    ProgressDialog progressDialog;
    ApiConfig request;
//    List<InsuranceResponse> insuranceResponseList;

    public HistoryFragment(User user) {
        // Required empty public constructor
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        request = AppConfig.getRetrofit().create(ApiConfig.class);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading...");

        initViews();
        controllerView();
        getData();

        return view;
    }

    private void initViews() {
        list = view.findViewById(R.id.list);
    }

    private void controllerView() {

    }

    private void getData() {

        Call<List<InsuranceResponse>> getData = request.req_getUserInsurance(user.getID(), user.getAuth());

        getData.enqueue(new Callback<List<InsuranceResponse>>() {
            @Override
            public void onResponse(Call<List<InsuranceResponse>> call, Response<List<InsuranceResponse>> response) {
                Log.e("responce", " " + response.body().size());
                setAdapter(response.body());
                System.out.println();
            }

            @Override
            public void onFailure(Call<List<InsuranceResponse>> call, Throwable t) {
                Log.e("responce", " shiit");
            }
        });

    }

    private void setAdapter(List<InsuranceResponse> insuranceResponseList) {
        HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), insuranceResponseList);
        list.setHasFixedSize(false);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list.setAdapter(historyAdapter);
    }

}