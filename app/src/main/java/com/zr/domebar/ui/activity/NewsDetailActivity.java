package com.zr.domebar.ui.activity;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zr.domebar.R;
import com.zr.domebar.bean.News;


public class NewsDetailActivity extends Activity {
    private WebView wv_detail;
    private Intent intent;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        findViews();
        setEvent();
    }

    private void setEvent() {
        intent = getIntent();
        news = intent.getExtras().getParcelable("news");
        wv_detail.loadUrl(news.getUrl());
        WebSettings setting = wv_detail.getSettings();
        setting.setJavaScriptEnabled(true);
        wv_detail.setWebViewClient(new WebViewClient());
        wv_detail.setWebChromeClient(new WebChromeClient());

    }

    private void findViews() {
        wv_detail = (WebView) findViewById(R.id.wv_detail);
    }
}
