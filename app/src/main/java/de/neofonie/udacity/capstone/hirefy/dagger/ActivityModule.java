package de.neofonie.udacity.capstone.hirefy.dagger;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import dagger.Module;
import dagger.Provides;
import de.neofonie.udacity.capstone.hirefy.dagger.qualifier.ForActivity;

/**
 * Created by marcinbak on 03/04/2017.
 */
@Module
public class ActivityModule {

  private FragmentActivity mFragmentActivity;

  public ActivityModule(FragmentActivity fragmentActivity) {
    this.mFragmentActivity = fragmentActivity;
  }

  @Provides
  @ForActivity
  public Context provideActivityContext() {
    return mFragmentActivity;
  }
}
