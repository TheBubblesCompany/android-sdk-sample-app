package com.mybubbles.sdksample.ui.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.mybubbles.sdk.instance.MyBubblesSDK;
import com.mybubbles.sdk.magic_button.exception.MagicButtonException;
import com.mybubbles.sdksample.R;

/**
 * @author Aurélien SEMENCE <aurelien.semence@bubbles-company.com>
 */
public class Page2Activity extends AppCompatActivity {
  private boolean magicButtonDisplayed = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_page2);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    final MyBubblesSDK myBubblesSDK = MyBubblesSDK.getInstance();
    myBubblesSDK.registerMagicButton(this, "b51ddc997546f116df73b8deb39fcbca");

    Button toggleButton = (Button) findViewById(R.id.page2_toggle_magic_button);
    toggleButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          if (!magicButtonDisplayed) {
            myBubblesSDK.displayMagicButton(Page2Activity.this, "b51ddc997546f116df73b8deb39fcbca");
          } else {
            myBubblesSDK.hideMagicButton(Page2Activity.this, "b51ddc997546f116df73b8deb39fcbca");
          }
          magicButtonDisplayed = !magicButtonDisplayed;
        } catch (MagicButtonException exception) {
          showErrorMessage(exception.getMessage());
        }
      }
    });
  }

  private void showErrorMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
