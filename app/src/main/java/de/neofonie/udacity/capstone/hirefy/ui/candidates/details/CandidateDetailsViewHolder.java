package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;
import de.neofonie.udacity.capstone.hirefy.utils.AndroidUtils;

/**
 * Created by marcinbak on 11.04.17.
 */
public class CandidateDetailsViewHolder extends ViewHolder<CandidateDetails> {

  public static class Factory extends ViewHolderFactory<CandidateDetails> {

    public Factory() {
      super(CandidateDetails.class);
    }

    @Override
    public ViewHolder<CandidateDetails> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_candidate_details, parent, false);
      return new CandidateDetailsViewHolder(view);
    }
  }

  @BindView(R.id.position_tv) TextView mPositionTv;
  @BindView(R.id.resume_btn)  View     mResumeBtn;

  private String mResumeUrl;

  private CandidateDetailsViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(CandidateDetails model) {
    mPositionTv.setText(model.getPosition());
    mResumeUrl = model.getResume();
    mResumeBtn.setVisibility(mResumeUrl == null ? View.INVISIBLE : View.VISIBLE);
  }

  @OnClick(R.id.resume_btn)
  void resumeClicked() {
    // open resume
    AndroidUtils.openLink((Activity) getContext(), mResumeUrl);
  }
}