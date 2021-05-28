package com.mohammadkz.bimix.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.appbar.MaterialToolbar;
import com.mohammadkz.bimix.Model.User;
import com.mohammadkz.bimix.R;

public class PayFragment extends Fragment {

    View view;
    BridgeWebView webView;
    SwipeRefreshLayout swipeContainer;
    SpinKitView spin_kit;
    User user;
    private final String url = "https://epay.iraninsurance.ir/";

    public PayFragment(User user) {
        // Required empty public constructor
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pay, container, false);

        initViews();
        controllerViews();
        webSetting();

        return view;
    }

    private void initViews() {
        webView = view.findViewById(R.id.webView);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        spin_kit = view.findViewById(R.id.spin_kit);
    }

    private void controllerViews() {

    }

    private void webSetting() {
        webView.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 24) {
            WebSettings settings = webView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settings.setSaveFormData(false);
            settings.setSupportZoom(true);
            settings.setJavaScriptEnabled(true);
            settings.setSaveFormData(false);
            settings.setSavePassword(false);


            webView.setWebChromeClient(new WebChromeClient());

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100) {
                        spin_kit.setVisibility(ProgressBar.VISIBLE);
                    } else if (progress == 100) {
                        webView.setVisibility(View.VISIBLE);
                        spin_kit.setVisibility(ProgressBar.GONE);
                    }
                }
            });
//
            webView.loadUrl(url);


            webView.registerHandler("submitFromWeb", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    Log.i("1", "handler = submitFromWeb, data from web = " + data);
                    function.onCallBack("submitFromWeb exe, response data from Java");
                }
            });

            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeContainer.setRefreshing(false);
                    webSetting();
                }
            });

        } else {
            fragmentChanger();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
    }

    private void fragmentChanger(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        IssuanceFragment issuanceFragment = new IssuanceFragment(user);
        fragmentTransaction.replace(R.id.frameLayout, issuanceFragment).commit();
    }
}