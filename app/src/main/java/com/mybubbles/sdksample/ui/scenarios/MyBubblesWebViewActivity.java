package com.mybubbles.sdksample.ui.scenarios;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mybubbles.sdk.helpers.UriHelper;
import com.mybubbles.sdksample.R;

public class MyBubblesWebViewActivity extends Activity {

  @Override
  @SuppressLint("SetJavaScriptEnabled")
  public void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);

    final Intent intent = getIntent();
    if (intent == null) {
      finish();
      return;
    }

    final Bundle extras = intent.getExtras();
    if (extras == null) {
      finish();
      return;
    }

    final String url = extras.getString("url");
    if (url == null || url.isEmpty()) {
      finish();
      return;
    }

    setContentView(R.layout.activity_webview);

    WebView webView = (WebView) findViewById(R.id.webview_layout);
    if (webView == null) {
      finish();
      return;
    }
    webView.loadUrl(url);
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
  }

  /**
   * Opens default activity if this is currently top activity
   */
  @Override
  public void onBackPressed() {
    if (isTaskRoot()) {
      finish();
      startActivity(UriHelper.getDefaultActivityIntent(getApplicationContext()));
    } else {
      super.onBackPressed();
    }
  }
}
