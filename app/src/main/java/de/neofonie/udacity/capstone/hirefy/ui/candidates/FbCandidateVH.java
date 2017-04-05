package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;

/**
 * Created by marcinbak on 05/04/2017.
 */

class FbCandidateVH extends RecyclerView.ViewHolder {

  @BindView(R.id.candidate_name_tv) TextView mCandidateNameTv;
  @BindView(R.id.next_interview_tv) TextView mNextInterviewTv;
  @BindView(R.id.position_tv)       TextView mPositionTv;

  public FbCandidateVH(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

}
