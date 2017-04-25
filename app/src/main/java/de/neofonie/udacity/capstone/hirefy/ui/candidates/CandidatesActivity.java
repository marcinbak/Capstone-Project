package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import de.neofonie.udacity.capstone.hirefy.appwidget.HirefyWidgetRemoteViewsService;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.auth.AuthManager;
import de.neofonie.udacity.capstone.hirefy.modules.calendar.CalendarManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;
import de.neofonie.udacity.capstone.hirefy.ui.LoginActivity;
import de.neofonie.udacity.capstone.hirefy.ui.PlayServicesProvider;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.details.*;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragment;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragmentBuilder;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Created by marcinbak on 03/04/2017.
 */
public class CandidatesActivity extends BaseActivity implements CandidateSelectedListener, CommentSender,
    GoogleApiClient.OnConnectionFailedListener, PlayServicesProvider, InterviewCreator, CalendarEventCreator {

  public static void start(Activity context) {
    Intent i = new Intent(context, CandidatesActivity.class);
    context.startActivity(i);
  }

  private final static int PERMISSIONS_REQ = 3731;

  @BindView(R.id.candidates_frag) View mCandidatesListFragment;

  @Nullable
  @BindView(R.id.candidate_details_frag)
  View mCandidateDetailsContainer;

  @Inject AuthManager         mAuthManager;
  @Inject CandidatesManager   mCandidatesManager;
  @Inject CalendarManager     mCalendarManager;
  @Inject CommentExtraHandler mCommentExtraHandler;

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
    mCommentExtraHandler.checkExtras(savedInstanceState);

    int listContainerId = R.id.candidates_frag;
    if (getSupportFragmentManager().findFragmentById(listContainerId) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(listContainerId, new CandidatesListFragmentBuilder(isTablet).build())
          .commit();
    }

    int detailsContainerId = R.id.candidate_details_frag;
    if (isTablet && getSupportFragmentManager().findFragmentById(detailsContainerId) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(detailsContainerId, new CandidateDetailFragmentBuilder(mCommentExtraHandler.getCurrentText()).build())
          .commit();
    }
    checkIntentForEventId(getIntent());
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mCommentExtraHandler.saveToExtras(outState);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    checkIntentForEventId(intent);
  }

  private void checkIntentForEventId(Intent intent) {
    if (intent.hasExtra(HirefyWidgetRemoteViewsService.EVENT_ID_EXTRA)) {
      Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, intent.getLongExtra(HirefyWidgetRemoteViewsService.EVENT_ID_EXTRA, -1));
      startActivity(new Intent(Intent.ACTION_VIEW).setData(uri));
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
    mCommentExtraHandler.clearCurrentComment();
    mCandidatesManager.sendComment(uuid, comment);
  }

  @Override
  public void updateComment(String newText) {
    mCommentExtraHandler.updateCurrentText(newText);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(this, R.string.play_services_failed, Toast.LENGTH_SHORT).show();
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

  @Override
  public void createAndOpenEvent(CandidateDetails candidate, Interview interview) {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSIONS_REQ);
      mPermissionInterview = interview;
      mPermissionCandidate = candidate;
    } else {
      mCalendarManager.addEvent(this, interview, candidate);
    }
  }

  private CandidateDetails mPermissionCandidate;
  private Interview        mPermissionInterview;

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PERMISSIONS_REQ && mPermissionCandidate != null) {
      for (int i = 0; i < permissions.length; i++) {
        if (Objects.equals(permissions[i], Manifest.permission.WRITE_CALENDAR) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
          mCalendarManager.addEvent(this, mPermissionInterview, mPermissionCandidate);
          break;
        }
      }
    }
    mPermissionInterview = null;
    mPermissionCandidate = null;
  }
}
