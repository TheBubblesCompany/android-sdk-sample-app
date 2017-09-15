package com.mybubbles.sdksample.ui.scenarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.mybubbles.sdk.helpers.UriHelper;
import com.mybubbles.sdk.hybrid.MyBubblesServiceInterface;
import com.mybubbles.sdk.hybrid.MyBubblesServiceLayout;
import com.mybubbles.sdk.utils.MyBubblesException;
import com.mybubbles.sdksample.R;

public class MyBubblesServiceActivity extends Activity implements MyBubblesServiceInterface { // Implement the MyBubbles Interface specific for Services

  MyBubblesServiceLayout serviceLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service);

    // Get the Service ID from the Intent
    String serviceID = getIntent().getStringExtra("myBubblesServiceID");

    // Load the Service
    serviceLayout = (MyBubblesServiceLayout) findViewById(R.id.service_layout);
    try {
      serviceLayout.loadService(serviceID);
    } catch (MyBubblesException e) {
      e.printStackTrace();
    }
  }

  // We catch the Back Button event to handle the Service breadcrumb
  @Override
  public void onBackPressed() {
    if (serviceLayout != null) {
      serviceLayout.onServiceBackPressed();
    } else {
      super.onBackPressed();
    }
  }

  /**
   * The Service has timeout, the Activity should probably be closed now.
   * If service was opened from background, we open default activity.
   */
  @Override
  public void onServiceHasTimeout() {
    finish();

    if (isTaskRoot()) {
      startActivity(UriHelper.getDefaultActivityIntent(getApplicationContext()));
    }
  }

  /**
   * The Service is finished, the Activity should probably be closed now.
   * If service was opened from background, we open default activity.
   */
  @Override
  public void onServiceIsFinished() {
    finish();

    if (isTaskRoot()) {
      startActivity(UriHelper.getDefaultActivityIntent(getApplicationContext()));
    }
  }

  /**
   * The currently opened Service wants to open a new Service
   *
   * @param serviceID Service identifier
   */
  @Override
  public void onNewServiceNeedsToBeStarted(String serviceID) {
    finish();
    startActivity(new Intent(this, MyBubblesServiceActivity.class).putExtra("myBubblesServiceID", serviceID));
  }

}
