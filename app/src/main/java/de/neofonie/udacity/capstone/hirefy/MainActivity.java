package de.neofonie.udacity.capstone.hirefy;

import android.os.Bundle;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getComponent().inject(this);
  }
}
