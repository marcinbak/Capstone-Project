package de.neofonie.udacity.capstone.hirefy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import de.neofonie.udacity.capstone.hirefy.HirefyApp;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;

/**
 * Created by marcinbak on 03/04/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private ActivityComponent component;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
  }

  protected synchronized ActivityComponent getComponent() {
    if (component == null) {
      component = ((HirefyApp) getApplicationContext()).createActivityComponent(this);
    }
    return component;
  }

}
