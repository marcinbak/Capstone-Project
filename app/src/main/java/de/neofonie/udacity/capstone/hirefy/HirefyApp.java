package de.neofonie.udacity.capstone.hirefy;

import android.app.Application;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityModule;
import de.neofonie.udacity.capstone.hirefy.dagger.AppComponent;
import de.neofonie.udacity.capstone.hirefy.dagger.DaggerAppComponent;
import de.neofonie.udacity.capstone.hirefy.dagger.modules.AppModule;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class HirefyApp extends Application {

  private AppComponent mAppComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initDagger2();
  }

  private void initDagger2() {
    mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    mAppComponent.inject(this);
  }

  public ActivityComponent createActivityComponent(BaseActivity activity) {
    return mAppComponent.plusActivity(new ActivityModule(activity));
  }
}
