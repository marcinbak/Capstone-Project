package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.calendar.CalendarManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CommentExtraHandler;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragment;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddInterviewFragmentBuilder;
import org.parceler.Parcels;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Created by marcinbak on 06/04/2017.
 */
public class CandidateDetailsActivity extends BaseActivity implements CommentSender, InterviewCreator, CalendarEventCreator {

  private final static String CANDIDATE_EXTRA = "CANDIDATE_EXTRA";
  private final static int    PERMISSIONS_REQ = 3821;

  public static void start(Activity context, FbCandidate candidate) {
    Intent i = new Intent(context, CandidateDetailsActivity.class);
    i.putExtra(CANDIDATE_EXTRA, Parcels.wrap(candidate));
    context.startActivity(i);
  }

  @BindView(R.id.container) View mContainer;

  @Inject CandidatesManager   mCandidatesManager;
  @Inject CalendarManager     mCalendarManager;
  @Inject CommentExtraHandler mCommentExtraHandler;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_details);
    getComponent().inject(this);
    ButterKnife.bind(this);

    mCommentExtraHandler.checkExtras(savedInstanceState);

    FbCandidate candidate = Parcels.unwrap(getIntent().getParcelableExtra(CANDIDATE_EXTRA));
    setTitle(candidate.getFirstName() + " " + candidate.getLastName());

    int containerId = mContainer.getId();
    if (containerId != 0) {
      if (getSupportFragmentManager().findFragmentById(containerId) == null) {
        getSupportFragmentManager().beginTransaction()
            .add(containerId, new CandidateDetailFragmentBuilder(mCommentExtraHandler.getCurrentText()).candidate(candidate).build())
            .commit();
      } else {
        ((CandidateDetailFragment) getSupportFragmentManager().findFragmentById(containerId)).setSavedComment(mCommentExtraHandler.getCurrentText());
      }
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mCommentExtraHandler.saveToExtras(outState);
  }

  @Override
  public void sendComment(String uuid, String comment) {
    mCommentExtraHandler.clearCurrentComment();
    if (getSupportFragmentManager().findFragmentById(mContainer.getId()) != null) {
      ((CandidateDetailFragment) getSupportFragmentManager().findFragmentById(mContainer.getId())).setSavedComment(mCommentExtraHandler.getCurrentText());
    }

    mCandidatesManager.sendComment(uuid, comment);
  }

  @Override
  public void updateComment(String newText) {
    mCommentExtraHandler.updateCurrentText(newText);
  }

  @Override
  public void startInterviewCreation(String candidateUuid) {
    AddInterviewFragment f = new AddInterviewFragmentBuilder(candidateUuid).build();
    String tag = AddInterviewFragment.TAG;
    getSupportFragmentManager()
        .beginTransaction()
        .setCustomAnimations(R.anim.right_in, R.anim.right_out, R.anim.right_in, R.anim.right_out)
        .add(R.id.container, f, tag)
        .addToBackStack(tag)
        .commit();
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
