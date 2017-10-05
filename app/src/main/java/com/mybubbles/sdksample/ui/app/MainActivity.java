package com.mybubbles.sdksample.ui.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mybubbles.sdk.instance.MyBubblesSDK;
import com.mybubbles.sdk.objects.MyBubblesService;
import com.mybubbles.sdk.utils.ML;
import com.mybubbles.sdksample.MyApplication;
import com.mybubbles.sdksample.R;
import com.mybubbles.sdksample.observers.ServicesListObservable;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesServiceActivity;
import com.mybubbles.sdksample.utils.MySharedPrefs;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

  private MyApplication app;

  private static ServicesListAdapter servicesListAdapter;
  private static final List<MyBubblesService> localServicesList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Init
    app = (MyApplication) getApplication();
    addObserver();
    initializeMyBubblesSdk();

    MyBubblesSDK myBubblesSDK = MyBubblesSDK.getInstance();
    myBubblesSDK.addMagicButton(this, "8d02065b7848638f44a1313ad889213e");
    myBubblesSDK.addMagicButton(this, "b51ddc997546f116df73b8deb39fcbca");
  }

  private void addObserver() {
    if (app != null) {
      // Add Services' List observer
      app.getServicesListObservable().addObserver(new ServicesListObserver());
    }
  }

  private void initializeMyBubblesSdk() {
    if (app != null) {
      // Init SDK
      app.initializeMyBubblesSdk();
    }
  }

  @Override
  protected void onDestroy() {
    // Clean Observer
    if (app != null) {
      app.getServicesListObservable().deleteObservers();
    }

    // Clean Services' List objects
    localServicesList.clear();
    servicesListAdapter = null;

    super.onDestroy();
  }

  public void onPage1ButtonClick(View view) {
    Intent intent = new Intent(this, Page1Activity.class);
    startActivity(intent);
  }

  public void onPage2ButtonClick(View view) {
    Intent intent = new Intent(this, Page2Activity.class);
    startActivity(intent);
  }

  public void onPage3ButtonClick(View view) {
    Intent intent = new Intent(this, Page3Activity.class);
    startActivity(intent);
  }

  public void onPage4ButtonClick(View view) {
    Intent intent = new Intent(this, Page4Activity.class);
    startActivity(intent);
  }

  private class ServicesListObserver implements Observer {

    @Override
    public void update(Observable observable, Object data) {
      if (observable instanceof ServicesListObservable) {
        // Display Services' List
        onServicesListReceived();
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // Put back UserId in EditText field when re-opening the App
    ((EditText) findViewById(R.id.user_id_edit_text)).setText(MySharedPrefs.readStr(MySharedPrefs.USER_ID));

    // Display Services' List when re-opening the App
    if (app.getServicesList() != null) {
      onServicesListReceived();
    }
  }

  private void onServicesListReceived() {

    // Display number of Services
    int nbServices = app.getServicesList().size();
    Log.e("onServicesListReceived", nbServices + " service(s) received");
    TextView servicesListSizeTextView = (TextView) findViewById(R.id.services_list_size_text_view);
    if (nbServices < 1) {
      servicesListSizeTextView.setText("Services List received : It's empty !");
    } else if (nbServices == 1) {
      servicesListSizeTextView.setText("Services List received : " + nbServices + " service");
    } else {
      servicesListSizeTextView.setText("Services List received : " + nbServices + " services");
    }

    if (servicesListAdapter == null) {
      // First time
      localServicesList.addAll(app.getServicesList());
      servicesListAdapter = new ServicesListAdapter(this, localServicesList);
      ((ListView) findViewById(R.id.services_list_view)).setAdapter(servicesListAdapter);
    } else {
      // Further update
      localServicesList.clear();
      localServicesList.addAll(app.getServicesList());
      servicesListAdapter.notifyDataSetChanged();
    }
  }

  private class ServicesListAdapter extends ArrayAdapter<MyBubblesService> {

    public ServicesListAdapter(Context context, List<MyBubblesService> services) {
      super(context, 0, services);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_services_list, parent, false);
      }

      final Context ctx = getContext();
      final MyBubblesService service = getItem(position);

      // Display Service data
      ML.e("ListView", "Render view of " + service.name + " (" + service.identifier + ")");
      ((TextView) convertView.findViewById(R.id.title)).setText(service.name);
      ((TextView) convertView.findViewById(R.id.desc)).setText(service.description);

      LinearLayout background = (LinearLayout) convertView.findViewById(R.id.services_list_adapter_parent_layout);
      if (position % 2 == 0) {
        background.setBackgroundColor(getResources().getColor(R.color.colorListBackgroundOdd));
      } else {
        background.setBackgroundColor(getResources().getColor(R.color.colorListBackgroundEven));
      }

      // Start Service when clicking the List row
      background.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ctx.startActivity(new Intent(ctx, MyBubblesServiceActivity.class).putExtra("myBubblesServiceID", service.identifier));
        }
      });

      return convertView;
    }
  }

  public void onUserIdButtonClick(View view) {
    closeKeyboard();

    EditText userIdEditText = (EditText) findViewById(R.id.user_id_edit_text);
    String userId = userIdEditText.getText().toString();
    Log.e("onUserIdButtonClick", "User ID : [" + userId + "]");

    // Save User ID for next App opening
    MySharedPrefs.writeStr(MySharedPrefs.USER_ID, userId);

    // Update User ID when clicking `Update` button
    MyBubblesSDK.getInstance().updateUserID(userId);
  }

  // Hide Keyboard when clicking `Update` button
  private void closeKeyboard() {
    View focus = getCurrentFocus();
    if (focus != null) {
      ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }
  }
}
