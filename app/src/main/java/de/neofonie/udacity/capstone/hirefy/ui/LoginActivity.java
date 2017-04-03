package de.neofonie.udacity.capstone.hirefy.ui;

import android.app.Activity;
import android.content.Intent;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class LoginActivity extends BaseActivity {

  public static void start(Activity context) {
    Intent i = new Intent(context, LoginActivity.class);
    context.startActivity(i);
  }

}
