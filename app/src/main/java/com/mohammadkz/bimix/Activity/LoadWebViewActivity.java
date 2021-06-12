package com.mohammadkz.bimix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.appbar.MaterialToolbar;
import com.mohammadkz.bimix.R;

public class LoadWebViewActivity extends AppCompatActivity {

    BridgeWebView webView;
    SwipeRefreshLayout swipeContainer;
    MaterialToolbar topAppBar;
    SpinKitView spin_kit;
    ImageView nav_image;
    int choose;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web_view);

        // get message from another activity ==> transfer choosed item
        Bundle bundle = getIntent().getExtras();
        choose = bundle.getInt("choose");

        initViews();
        controllerViews();
        setUrl();
        webSetting();

    }

    private void initViews() {
        webView = findViewById(R.id.webView);
        swipeContainer = findViewById(R.id.swipeContainer);
        topAppBar = findViewById(R.id.topAppBar);
        spin_kit = findViewById(R.id.spin_kit);
        nav_image = findViewById(R.id.nav_image);
    }

    private void controllerViews() {
        nav_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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


            Bundle bl = getIntent().getExtras();
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
            finish();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
    }

    private void setUrl() {
        switch (choose) {
            case 1:
                url = "https://my.iraninsurance.ir/car/body/rate.html";
                break;
            case 2:
                url = "https://my.iraninsurance.ir/car/body/rate.html";
                break;
            case 3:
                url = "https://my.iraninsurance.ir/life-new/responsibility/lift/rate.html";
                break;

            case 4:
                url = "https://my.iraninsurance.ir/fire/hami.html";
                break;

            case 5:
                url = "https://my.iraninsurance.ir/car/thirdparty/rate.html";
                break;

            case 6:
                url = "https://my.iraninsurance.ir/life-new/accident/individual/rate.html";
                break;

            case 7:
                url = "https://my.iraninsurance.ir/life-new/life/maan/rate.html";
                break;

        }
    }


}