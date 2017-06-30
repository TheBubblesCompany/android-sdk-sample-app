package com.mybubbles.sdksample.ui.scenarios;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MyBubblesUriActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {

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

    final String uriDefault = extras.getString("uri_default");
    final String uriFallback = extras.getString("uri_fallback");

    boolean hasSucceededToLaunchURI = true;
    if (uriDefault != null && !uriDefault.isEmpty()) {
      try {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uriDefault)));
      } catch (ActivityNotFoundException e1) {
        try {
          startActivity(new Intent(uriDefault));
        } catch (ActivityNotFoundException e2) {
          hasSucceededToLaunchURI = false;
        }
      }
    } else {
      hasSucceededToLaunchURI = false;
    }

    if (!hasSucceededToLaunchURI && uriFallback != null && !uriFallback.isEmpty()) {
      try {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uriFallback)));
      } catch (ActivityNotFoundException e1) {
        try {
          startActivity(new Intent(uriFallback));
        } catch (ActivityNotFoundException e2) {
        }
      }
    }

    finish();
  }
}
