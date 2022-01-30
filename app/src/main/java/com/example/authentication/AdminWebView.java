package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class AdminWebView extends AppCompatActivity {
    WebView webView1,webView2;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_webview);

    swipeRefreshLayout = findViewById(R.id.swipeContainer);
    webView1 = findViewById(R.id.web_view1);
    webView2 = findViewById(R.id.web_view2);




        Toolbar toolbar3 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar3.setTitleTextAppearance(this, R.style.Acme);

        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent intent=new Intent(AdminWebView.this, AdminPanelActivity.class);
                startActivity(intent);
                finish();

            }
        });






        WebSettings webSettings1 = webView1.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        String path = "file:android_asset/";
        String name = "webview.html";
        String file = path+name;
        webView1.loadUrl(file);

        WebSettings webSettings2 = webView2.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webView2.loadUrl("https://console.firebase.google.com/project/authentication-fb557/database/authentication-fb557-default-rtdb/data");

        webView1.getSettings().setDomStorageEnabled(true);
        webView2.getSettings().setDomStorageEnabled(true);
        webView2.setWebChromeClient(new WebChromeClient());
        webView2.setWebViewClient(new WebViewClient());
        webView2.getSettings().setSupportZoom(true);
        webView2.getSettings().setBuiltInZoomControls(true);
        webView2.getSettings().setDisplayZoomControls(false);
        webView2.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView2.getSettings().setCacheMode(webSettings2.LOAD_CACHE_ELSE_NETWORK);
        webView2.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings2.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings2.setUseWideViewPort(true);
        webSettings2.setSaveFormData(true);
        webSettings2.setSavePassword(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb();
            }
        });
        LoadWeb();
    }
    public void LoadWeb(){
        webView2 = findViewById(R.id.web_view2);
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setAppCacheEnabled(true);
        webView2.loadUrl("https://console.firebase.google.com/project/authentication-fb557/database/authentication-fb557-default-rtdb/data");

        webView2.getSettings().setSupportZoom(true);
        webView2.getSettings().setBuiltInZoomControls(true);
        webView2.getSettings().setDisplayZoomControls(false);

        webView2.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);;
        webView2.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);




        swipeRefreshLayout.setRefreshing(true);
        webView2.setWebViewClient(new WebViewClient(){

            public void onPageFinished(WebView view, String url){
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(webView1.canGoBack()){
            webView1.goBack();
        }else{
            finish();
        }
    }
}
