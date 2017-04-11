package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

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

  private CandidateDetailsViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(CandidateDetails model) {
  }
}