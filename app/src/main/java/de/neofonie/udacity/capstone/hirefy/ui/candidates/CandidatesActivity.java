package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.auth.AuthManager;
import de.neofonie.udacity.capstone.hirefy.ui.LoginActivity;

import javax.inject.Inject;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class CandidatesActivity extends BaseActivity {

  public static void start(Activity context) {
    Intent i = new Intent(context, CandidatesActivity.class);
    context.startActivity(i);
  }

  @Inject AuthManager mAuthManager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.candidates_activity);
    getComponent().inject(this);
  }

  void showCandidateDetails() {

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.candidates_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        mAuthManager.logout();
        finishAffinity();
        LoginActivity.start(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
