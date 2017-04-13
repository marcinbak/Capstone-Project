package de.neofonie.udacity.capstone.hirefy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by marcinbak on 11.04.17.
 */

public class AndroidUtils {

  public static void hideKeyboardFrom(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void openLink(Activity activity, String url) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    activity.startActivity(browserIntent);
  }

}
