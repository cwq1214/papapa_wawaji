package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by v7 on 2017/11/9.
 */

public class VersionInfoActivity extends BaseActivity {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version_info;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                int currentWidth = right-left;
                webView.setLayoutParams(new LinearLayout.LayoutParams(currentWidth,currentWidth*300/375));

                webView.removeOnLayoutChangeListener(this);
            }
        });

        webView.loadUrl("http://60.205.222.162:8081/front/wap/productInfoOfKData");
//        webView.setInitialScale(50);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(new WebViewClient(){
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                                         return true;
                                     }


                                 }
        );
    }

    @OnClick(R.id.start)
    public void setStart() {

    }
}
