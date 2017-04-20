package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.auth.AuthManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.ui.LoginActivity;
import de.neofonie.udacity.capstone.hirefy.ui.PlayServicesProvider;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.details.*;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragment;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragmentBuilder;

import javax.inject.Inject;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class CandidatesActivity extends BaseActivity implements CandidateSelectedListener, CommentSender,
    GoogleApiClient.OnConnectionFailedListener, PlayServicesProvider, InterviewCreator {

  public static void start(Activity context) {
    Intent i = new Intent(context, CandidatesActivity.class);
    context.startActivity(i);
  }

  @BindView(R.id.candidates_frag) View mCandidatesListFragment;

  @Nullable
  @BindView(R.id.candidate_details_frag)
  View mCandidateDetailsContainer;

  @Inject AuthManager       mAuthManager;
  @Inject CandidatesManager mCandidatesManager;

  private GoogleApiClient mGoogleApiClient;
  private boolean         isTablet;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.candidates_activity);
    getComponent().inject(this);
    ButterKnife.bind(this);
    isTablet = mCandidateDetailsContainer != null;

    initPlayServices();

    int listContainerId = R.id.candidates_frag;
    if (getSupportFragmentManager().findFragmentById(listContainerId) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(listContainerId, new CandidatesListFragmentBuilder(isTablet).build())
          .commit();
    }

    int detailsContainerId = R.id.candidate_details_frag;
    if (isTablet && getSupportFragmentManager().findFragmentById(detailsContainerId) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(detailsContainerId, new CandidateDetailFragmentBuilder().build())
          .commit();
    }
  }

  private void initPlayServices() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, this)
        .addApi(Drive.API)
        .addScope(Drive.SCOPE_FILE)
        .build();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mGoogleApiClient.connect();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mGoogleApiClient.disconnect();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.candidates_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        mAuthManager.logout();
        finishAffinity();
        LoginActivity.start(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCandidateSelected(FbCandidate candidate, int index) {
    if (isTablet) {
      CandidateDetailFragment details = (CandidateDetailFragment) getSupportFragmentManager().findFragmentById(R.id.candidate_details_frag);
      if (details != null) {
        details.setCandidate(candidate);
      }

      CandidatesListFragment list = (CandidatesListFragment) getSupportFragmentManager().findFragmentById(R.id.candidates_frag);
      if (list != null) {
        list.setSelectedCandidate(candidate, index);
      }
    } else {
      CandidateDetailsActivity.start(this, candidate);
    }
  }

  @Override
  public void sendComment(String uuid, String comment) {
    mCandidatesManager.sendComment(uuid, comment);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(this, "Failed to connect to play services.", Toast.LENGTH_SHORT).show();
  }

  public GoogleApiClient getGoogleApiClient() {
    return mGoogleApiClient;
  }

  @Override
  public void startInterviewCreation(String candidateUuid) {
    AddInterviewFragment f = new AddInterviewFragmentBuilder(candidateUuid).build();
    String tag = AddInterviewFragment.TAG;
    f.show(getSupportFragmentManager(), tag);
  }
}
