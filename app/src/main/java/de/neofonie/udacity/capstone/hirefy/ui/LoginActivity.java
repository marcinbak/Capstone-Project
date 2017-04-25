package de.neofonie.udacity.capstone.hirefy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.auth.AuthManager;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CandidatesActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

  private final static int    RC_SIGN_IN = 1912;
  private final static String TAG        = "LoginActivity";

  public static void start(Activity context) {
    Intent i = new Intent(context, LoginActivity.class);
    context.startActivity(i);
  }

  @Inject AuthManager mAuthManager;

  private GoogleApiClient mGoogleApiClient;

  private Disposable mLoginDisposable     = Disposables.disposed();
  private Disposable mAuthStateDisposable = Disposables.disposed();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    getComponent().inject(this);

    setupGooglePlus();
  }

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseDatabase.getInstance().getReference().child("test").addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
      }
    });
    mAuthStateDisposable = mAuthManager.getAuthStateChangeObservable().subscribe(new Consumer<FirebaseAuth>() {
      @Override
      public void accept(@io.reactivex.annotations.NonNull FirebaseAuth firebaseAuth) throws Exception {
        if (mAuthManager.isUserLoggedIn()) {
          Toast.makeText(LoginActivity.this, R.string.login_success_msg, Toast.LENGTH_SHORT).show();
//          loadingLayout.isLoadingVisible = false
          CandidatesActivity.start(LoginActivity.this);
        } else {
//          loadingLayout.isLoadingVisible = false
        }
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
        Log.e(TAG, "Error in Firebase Auth state observable.", throwable);

        final String msg = getString(R.string.exception_msg, throwable.getMessage());

        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//        loadingLayout.isLoadingVisible = false
      }
    });
  }

  @Override
  protected void onStop() {
    super.onStop();
    mAuthStateDisposable.dispose();
    mLoginDisposable.dispose();
  }

  private void setupGooglePlus() {
    // G+
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(getString(R.string.default_web_client_id))
        .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @OnClick(R.id.googleButton)
  void loginGoogle(View v) {
//    loadingLayout.isLoadingVisible = true
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult result) {
    Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result != null && result.isSuccess()) {
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = result.getSignInAccount();

//        account.getEmail().endsWith() TODO verify domain here

        firebaseAuthWithGoogle(account);
      } else {
        Toast.makeText(this, result == null ? getString(R.string.unknown_error) : result.getStatus().toString(), Toast.LENGTH_SHORT).show();
//        loadingLayout.isLoadingVisible = false;
      }
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    mLoginDisposable.dispose();
    mLoginDisposable = mAuthManager.login(account).subscribe(new Action() {
      @Override
      public void run() throws Exception {
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
        //        loadingLayout.isLoadingVisible = false;
        Toast.makeText(LoginActivity.this, R.string.login_failed_msg, Toast.LENGTH_SHORT).show();
      }
    });
  }

}
