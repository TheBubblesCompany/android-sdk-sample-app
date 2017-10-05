package com.mybubbles.sdksample.ui.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.mybubbles.sdk.instance.MyBubblesSDK;
import com.mybubbles.sdksample.R;

/**
 * @author Aurélien SEMENCE <aurelien.semence@bubbles-company.com>
 */
public class Page4Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_page4);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    MyBubblesSDK myBubblesSDK = MyBubblesSDK.getInstance();
    myBubblesSDK.addMagicButton(this, "e6273f28cf55cafffcf5a4890c288e3c");
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

  public void closePage(View view) {
    finish();
  }
}
