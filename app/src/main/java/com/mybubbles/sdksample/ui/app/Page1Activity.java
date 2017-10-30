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
public class Page1Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_page1);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
    MyBubblesSDK myBubblesSDK = MyBubblesSDK.getInstance();
    myBubblesSDK.addMagicButton(this, "ab15be9050ce23b6efb7d7c8e4c3858f");
    myBubblesSDK.addMagicButton(this, "0a5b3913cbc9a9092311630e869b4442");
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
