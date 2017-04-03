package de.neofonie.udacity.capstone.hirefy.dagger;

import dagger.Component;
import de.neofonie.udacity.capstone.hirefy.HirefyApp;
import de.neofonie.udacity.capstone.hirefy.dagger.modules.AppModule;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;

/**
 * Created by marcinbak on 03/04/2017.
 */
@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent {

  ActivityComponent plusActivity(ActivityModule activityModule);

  void inject(HirefyApp app);

}
