package com.mohammadkz.bimix.Fragment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mohammadkz.bimix.API.ApiConfig;
import com.mohammadkz.bimix.API.AppConfig;
import com.mohammadkz.bimix.Activity.MainPageActivity;
import com.mohammadkz.bimix.Adapter.HistoryAdapter;
import com.mohammadkz.bimix.Model.HistoryInsuranceResponse;
import com.mohammadkz.bimix.Model.InsuranceResponse;
import com.mohammadkz.bimix.Model.UpdateResponse;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;


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
    TextView no_req;
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
        no_req = view.findViewById(R.id.no_req);
    }

    private void controllerView() {

    }

    private void getData() {

        Call<List<InsuranceResponse>> getData = request.req_getUserInsurance(user.getID(), user.getAuth());

        getData.enqueue(new Callback<List<InsuranceResponse>>() {
            @Override
            public void onResponse(Call<List<InsuranceResponse>> call, Response<List<InsuranceResponse>> response) {
                if (response.body().size() !=0){
                    Log.e("responce", " " + response.body().size());
                    setAdapter(response.body());
                    System.out.println();
                    no_req.setVisibility(View.GONE);
                }else {
                    no_req.setVisibility(View.VISIBLE);
                }

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

        // payed
        historyAdapter.setOnPayedItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {

                updatePayStatus(insuranceResponseList.get(pos));
                refresh();
            }
        });

        // get the download link
        historyAdapter.setOnGetItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {

            }
        });

        // press pay
        historyAdapter.setOnPayItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {

                copy(insuranceResponseList.get(pos).getPayCode());
                Toast.makeText(getContext(), "شناسه پرداخت کپی شد.", Toast.LENGTH_LONG).show();
                payFragment();


            }
        });

    }

    private void copy(String code) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("paycode", code);
        clipboard.setPrimaryClip(clip);
    }

    private void payFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        PayFragment payFragment = new PayFragment(user);
        fragmentTransaction.replace(R.id.frameLayout, payFragment).commit();

        ((MainPageActivity) getActivity()).setTitle("پرداخت بیمه نامه");
        ((MainPageActivity) getActivity()).setNavSelected(R.id.nav_pay);
    }

    private void updatePayStatus(InsuranceResponse insuranceResponse) {

        Call<UpdateResponse> get = request.payStatus(user.getAuth(), "payed", insuranceResponse.getTrackingCode(), insuranceResponse.getKind());

        get.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                Log.e("re", " " + response.body().getCode());
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Log.e("re", " " + t.getMessage());
            }
        });

    }

    private void refresh() {
        getData();
    }

}