package com.xy.rcs.goeasy.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xy.rcs.goeasy.IonKeyPress;
import com.xy.rcs.goeasy.MainActivity;
import com.xy.rcs.goeasy.R;
import com.xy.rcs.goeasy.RepertoryActivity;

import java.util.Objects;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static cn.bmob.v3.Bmob.getFilesDir;


public class WebFragment extends Fragment {

    private View mRootView;
    private WebView webView;
    private WebSettings mWebSettings;
    private WebViewClient webViewClient;
    private WebChromeClient webChromeClient;
    private ProgressBar mProgressBar;
    protected Intent mIntent;
    protected TextView textView;

    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_web, container, false);
        initView();
        showWeb();
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);
        ((RepertoryActivity) this.getActivity()).setIonKeyPress(IonKeyPress);
        return mRootView;
    }
    IonKeyPress IonKeyPress=new IonKeyPress() {
        @Override
        public void onKeyDown(int keyCode, KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    webView.goBack();
                    break;
            }
        }
    };

    private void showWeb() {
        webViewClient =new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        };
        webChromeClient=new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {

                textView.setText(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        };

    }

    private void initView() {
        mIntent = Objects.requireNonNull(getActivity()).getIntent();
        url = Objects.requireNonNull(mIntent.getExtras()).getString("title");
        textView = getActivity().findViewById(R.id.fragment_title);
        textView.setTextSize(17);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        mProgressBar=getActivity().findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        webView = mRootView.findViewById(R.id.web_frag_webview);
        mWebSettings=webView.getSettings();
        mWebSettings.setAppCacheEnabled(true);
        //设置支持jS
        mWebSettings.setJavaScriptEnabled(true);
        //设置屏幕适应
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        //缩放操作
        mWebSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
       // mWebSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式4
        //优先使用缓存:
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


    }


}
