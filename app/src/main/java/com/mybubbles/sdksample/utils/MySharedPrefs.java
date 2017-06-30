package com.mybubbles.sdksample.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefs {

  private static final String DEMO_APP_SHARED_PREFS = "Demo_App_Shared_Prefs";

  public static final String USER_ID = "User_Id";

  private static SharedPreferences sharedPrefs = null;

  public static void init(Application app) {
    if (sharedPrefs == null) {
      sharedPrefs = app.getSharedPreferences(DEMO_APP_SHARED_PREFS, Context.MODE_PRIVATE);
    }
  }

  public static void writeStr(final String key, final String message) {
    if (sharedPrefs != null) {
      sharedPrefs.edit().putString(key, message).apply();
    }
  }

  public static String readStr(final String key) {
    String message = null;
    if (sharedPrefs != null) {
      message = sharedPrefs.getString(key, null);
    }
    return message;
  }
}
