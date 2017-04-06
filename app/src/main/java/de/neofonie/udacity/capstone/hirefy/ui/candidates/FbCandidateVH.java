package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;

/**
 * Created by marcinbak on 05/04/2017.
 */

public class FbCandidateVH extends RecyclerView.ViewHolder {

  @BindView(R.id.candidate_name_tv) TextView    mCandidateNameTv;
  @BindView(R.id.next_interview_tv) TextView    mNextInterviewTv;
  @BindView(R.id.position_tv)       TextView    mPositionTv;
  @BindView(R.id.root_layout)       View        mRootView;
  private                           FbCandidate mModel;

  public FbCandidateVH(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @OnClick(R.id.root_layout)
  void onItemClicked() {
    if (mRootView.getContext() instanceof CandidateSelectedListener) {
      ((CandidateSelectedListener) mRootView.getContext()).onCandidateSelected(mModel, getAdapterPosition());
    } else {
      throw new IllegalStateException("Parent activity must implement CandidateSelectedListener");
    }
  }

  public void setModel(FbCandidate model, boolean isFirst, boolean selected) {
    mModel = model;
    mCandidateNameTv.setText(model.getFirstName() + " " + model.getLastName());
    mPositionTv.setText(model.getPosition());
    if (isFirst) {
      // TODO show extra button to go to interview??
    }

    mRootView.setBackgroundResource(selected ? R.color.selected_item_background : R.drawable.candidate_li_background);
  }
}
