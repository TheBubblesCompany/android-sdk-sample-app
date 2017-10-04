package com.mybubbles.sdksample.receivers;

import android.content.Context;
import com.mybubbles.sdk.objects.MyBubblesService;
import com.mybubbles.sdk.receiver.FetchServicesReceiverAbstract;
import com.mybubbles.sdksample.MyApplication;
import java.util.ArrayList;

/**
 * @author Aurélien SEMENCE <aurelien.semence@bubbles-company.com>
 */
public class FetchServicesReceiver extends FetchServicesReceiverAbstract {

  @Override
  protected void onFetchServices(Context applicationContext, ArrayList<MyBubblesService> servicesList) {
    MyApplication myApplication = ((MyApplication) applicationContext);

    if (myApplication != null) {
      myApplication.setServicesList(servicesList);
      myApplication.getServicesListObservable().trigger();
    }
  }
}
