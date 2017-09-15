package com.mybubbles.sdksample;

import android.util.Log;
import com.mybubbles.sdk.instance.MyBubblesApplication;
import com.mybubbles.sdk.instance.MyBubblesInterface;
import com.mybubbles.sdk.instance.MyBubblesSDK;
import com.mybubbles.sdk.objects.MyBubblesService;
import com.mybubbles.sdksample.observers.ServicesListObservable;
import com.mybubbles.sdksample.ui.app.MainActivity;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesImageActivity;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesServiceActivity;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesUriActivity;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesWebViewActivity;
import com.mybubbles.sdksample.utils.MySharedPrefs;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends MyBubblesApplication {

  private static final String TAG = MyApplication.class.getName();

  private List<MyBubblesService> servicesList;
  private ServicesListObservable servicesListObservable = new ServicesListObservable();

  @Override
  public void onCreate() {
    super.onCreate();

    MySharedPrefs.init(this);
  }

  public List<MyBubblesService> getServicesList() {
    return servicesList;
  }

  public ServicesListObservable getServicesListObservable() {
    return servicesListObservable;
  }

  public void initializeMyBubblesSdk() {

    final String userID = "#test";
    final boolean forceLocationPermission = true;

    // Initialize the MyBubbles SDK Singleton Instance
    MyBubblesSDK.createInstance(this, userID, forceLocationPermission);
  }

  public void setServicesList(ArrayList<MyBubblesService> servicesList) {
    this.servicesList = servicesList;
  }

  public void onNetworkNotAvailable() {
    Log.d(TAG, "onNetworkNotAvailable");
  }
}
