package de.neofonie.udacity.capstone.hirefy.ui;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseUser;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.auth.AuthManager;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CandidatesActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity {

  private Disposable mDisposable = Disposables.disposed();

  @Inject AuthManager mAuthManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    getComponent().inject(this);

    if (mAuthManager.isUserLoggedIn()) {
      CandidatesActivity.start(this);
      finish();
      return;
    }

    startTimer(3);
  }

  private void startTimer(int seconds) {
    mDisposable.dispose();
    mDisposable = Completable.timer(seconds, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action() {
          @Override
          public void run() throws Exception {
            goToNextScreen();
          }
        });
  }

  @Override
  protected void onStop() {
    super.onStop();
    mDisposable.dispose();
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    startTimer(1);
  }

  private void goToNextScreen() {
    FirebaseUser currentUser = mAuthManager.getUser();
    if (currentUser != null) {
      CandidatesActivity.start(this);
    } else {
      LoginActivity.start(this);
    }
    finish();
  }
}
