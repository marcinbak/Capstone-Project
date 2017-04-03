package de.neofonie.udacity.capstone.hirefy.dagger.modules;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.neofonie.udacity.capstone.hirefy.HirefyApp;
import de.neofonie.udacity.capstone.hirefy.dagger.qualifier.ForApplication;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;

/**
 * Created by marcinbak on 03/04/2017.
 */
@Module
public class AppModule {

  private HirefyApp mApp;

  public AppModule(HirefyApp mApp) {this.mApp = mApp;}

  @Provides
  @ApplicationScope
  public Application provideApplication() {
    return mApp;
  }

  @Provides
  @ApplicationScope
  @ForApplication
  public Context provideApplicationContext() {
    return mApp;
  }


}
