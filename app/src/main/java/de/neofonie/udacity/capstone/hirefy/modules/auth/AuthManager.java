package de.neofonie.udacity.capstone.hirefy.modules.auth;

import android.support.annotation.Nullable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by marcinbak on 03/04/2017.
 */
@ApplicationScope
public class AuthManager {

  @Inject
  public AuthManager() {
  }

  public boolean isUserLoggedIn() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    return user != null;
  }

  @Nullable
  public FirebaseUser getUser() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }
}
