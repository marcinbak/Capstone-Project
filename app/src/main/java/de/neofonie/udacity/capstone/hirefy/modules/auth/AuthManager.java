package de.neofonie.udacity.capstone.hirefy.modules.auth;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;
import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;

import javax.inject.Inject;

/**
 * Created by marcinbak on 03/04/2017.
 */
@ApplicationScope
public class AuthManager {

  private final static String TAG = "AuthManager";

  private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

  @Inject
  public AuthManager() {
  }

  public boolean isUserLoggedIn() {
    FirebaseUser user = mFirebaseAuth.getCurrentUser();
    return user != null;
  }

  @Nullable
  public FirebaseUser getUser() {
    return mFirebaseAuth.getCurrentUser();
  }

  /**
   * Gets authentication state change observable.
   * Must subscribe in onStart and dispose in onStop
   */
  public Observable<FirebaseAuth> getAuthStateChangeObservable() {
    return Observable.create(new ObservableOnSubscribe<FirebaseAuth>() {
      @Override
      public void subscribe(final @NonNull ObservableEmitter<FirebaseAuth> e) throws Exception {
        final FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
          @Override
          public void onAuthStateChanged(@android.support.annotation.NonNull FirebaseAuth firebaseAuth) {
            e.onNext(firebaseAuth);
          }
        };
        mFirebaseAuth.addAuthStateListener(listener);
        e.setDisposable(Disposables.fromAction(new Action() {
          @Override
          public void run() throws Exception {
            mFirebaseAuth.removeAuthStateListener(listener);
          }
        }));
      }
    });
  }

  public Completable login(GoogleSignInAccount account) {
    final AuthCredential credentials = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    return Completable.create(new CompletableOnSubscribe() {
      @Override
      public void subscribe(@NonNull final CompletableEmitter e) throws Exception {
        mFirebaseAuth.signInWithCredential(credentials).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@android.support.annotation.NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()) {
              Log.e(TAG, "User login failed", task.getException());
              e.onError(task.getException());
            } else {
              e.onComplete();
            }
          }
        });
      }
    });
  }
}
