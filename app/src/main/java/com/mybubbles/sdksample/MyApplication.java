package com.mybubbles.sdksample;

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
import java.util.List;

public class MyApplication extends MyBubblesApplication {

  private List<MyBubblesService> servicesList;
  private ServicesListObservable servicesListObservable = new ServicesListObservable();

  private final MyBubblesInterface myBubblesInterface = new MyBubblesInterface() {

    @Override
    public void onNetworkNotAvailable() {
    }

    @Override
    public void onFetchServices(final List<MyBubblesService> _servicesList) {
      servicesList = _servicesList;
      servicesListObservable.trigger();
    }

    @Override
    public Class<?> getDefaultActivityClass() {
      return MainActivity.class;
    }

    @Override
    public Class<?> getImageActivityClass() {
      return MyBubblesImageActivity.class;
    }

    @Override
    public Class<?> getUriActivityClass() {
      return MyBubblesUriActivity.class;
    }

    @Override
    public Class<?> getWebViewActivityClass() {
      return MyBubblesWebViewActivity.class;
    }

    @Override
    public Class<?> getServiceActivityClass() {
      return MyBubblesServiceActivity.class;
    }

  };

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

    final String userID = null;
    final boolean forceLocationPermission = false;

    // Initialize the MyBubbles SDK Singleton Instance
    MyBubblesSDK.createInstance(this, myBubblesInterface, userID, forceLocationPermission);
  }
}
