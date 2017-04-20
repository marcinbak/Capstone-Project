package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseActivity;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import org.parceler.Parcels;

import javax.inject.Inject;

/**
 * Created by marcinbak on 06/04/2017.
 */
public class CandidateDetailsActivity extends BaseActivity implements CommentSender, InterviewCreator {

  private final static String CANDIDATE_EXTRA = "CANDIDATE_EXTRA";

  public static void start(Activity context, FbCandidate candidate) {
    Intent i = new Intent(context, CandidateDetailsActivity.class);
    i.putExtra(CANDIDATE_EXTRA, Parcels.wrap(candidate));
    context.startActivity(i);
  }

  @BindView(R.id.container) View mContainer;

  @Inject CandidatesManager mCandidatesManager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_details);
    getComponent().inject(this);
    ButterKnife.bind(this);

    FbCandidate candidate = Parcels.unwrap(getIntent().getParcelableExtra(CANDIDATE_EXTRA));
    setTitle(candidate.getFirstName() + " " + candidate.getLastName());

    int containerId = mContainer.getId();
    if (containerId != 0 && getSupportFragmentManager().findFragmentById(containerId) == null) {
      getSupportFragmentManager().beginTransaction()
          .add(containerId, new CandidateDetailFragmentBuilder().candidate(candidate).build())
          .commit();
    }
  }

  @Override
  public void sendComment(String uuid, String comment) {
    mCandidatesManager.sendComment(uuid, comment);
  }

  @Override
  public void startInterviewCreation(String candidateUuid) {
    // TODO implement
  }
}
