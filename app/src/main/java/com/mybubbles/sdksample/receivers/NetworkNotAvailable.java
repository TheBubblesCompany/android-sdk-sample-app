package com.mybubbles.sdksample.receivers;

import android.content.Context;
import com.mybubbles.sdk.receiver.NetworkNotAvailableAbstract;
import com.mybubbles.sdksample.MyApplication;

/**
 * @author Aurélien SEMENCE <aurelien.semence@bubbles-company.com>
 */
public class NetworkNotAvailable extends NetworkNotAvailableAbstract {

  @Override
  protected void onNetworkNotAvailable(Context applicationContext) {
    MyApplication myApplication = ((MyApplication) applicationContext);

    if (myApplication != null) {
      myApplication.onNetworkNotAvailable();
    }
  }
}
