package com.mybubbles.sdksample.ui.scenarios;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.mybubbles.sdk.helpers.UriHelper;
import com.mybubbles.sdksample.R;
import com.squareup.picasso.Picasso;

public class MyBubblesImageActivity extends Activity {

  private static final String TYPE_URI = "URI";
  private static final String TYPE_WEBVIEW = "WVW";

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

    final String imageUrl = extras.getString("url");
    if (imageUrl == null || imageUrl.isEmpty()) {
      finish();
      return;
    }

    setContentView(R.layout.activity_image);

    ImageView image = (ImageView) findViewById(R.id.image_layout);
    if (image == null) {
      finish();
      return;
    }

    Picasso.with(this).load(imageUrl).into(image);
    image.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        final String actionType = extras.getString("action_type");
        if (actionType == null || actionType.isEmpty()) {
          return;
        }

        if (actionType.equals(TYPE_URI)) {

          final String actionUriDefault = extras.getString("action_uri_default");
          final String actionUriFallback = extras.getString("action_uri_fallback");

          boolean hasSucceededToLaunchURI = true;
          if (actionUriDefault != null && !actionUriDefault.isEmpty()) {
            try {
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actionUriDefault)));
            } catch (ActivityNotFoundException e1) {
              try {
                startActivity(new Intent(actionUriDefault));
              } catch (ActivityNotFoundException e2) {
                hasSucceededToLaunchURI = false;
              }
            }
          } else {
            hasSucceededToLaunchURI = false;
          }

          if (!hasSucceededToLaunchURI && actionUriFallback != null && !actionUriFallback.isEmpty()) {
            hasSucceededToLaunchURI = true;
            try {
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actionUriFallback)));
            } catch (ActivityNotFoundException e1) {
              try {
                startActivity(new Intent(actionUriFallback));
              } catch (ActivityNotFoundException e2) {
                hasSucceededToLaunchURI = false;
              }
            }
          }

          if (hasSucceededToLaunchURI) {
            finish();
          }

        } else if (actionType.equals(TYPE_WEBVIEW)) {

          final String actionUrl = extras.getString("action_url");

          if (actionUrl != null && !actionUrl.isEmpty()) {
            Intent intent = new Intent(MyBubblesImageActivity.this, MyBubblesWebViewActivity.class);
            intent.putExtra("url", actionUrl);
            startActivity(intent);
            finish();
          }
        }
      }
    });
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
