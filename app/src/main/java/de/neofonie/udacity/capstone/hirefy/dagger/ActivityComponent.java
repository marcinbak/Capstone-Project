package de.neofonie.udacity.capstone.hirefy.dagger;

import dagger.Subcomponent;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ActivityScope;
import de.neofonie.udacity.capstone.hirefy.ui.LoginActivity;
import de.neofonie.udacity.capstone.hirefy.ui.SplashActivity;

/**
 * Created by marcinbak on 03/04/2017.
 */
@Subcomponent(modules = {
    ActivityModule.class
})
@ActivityScope
public interface ActivityComponent {

//  void inject(BaseActivity activity);

  void inject(SplashActivity activity);

  void inject(LoginActivity activity);
}
