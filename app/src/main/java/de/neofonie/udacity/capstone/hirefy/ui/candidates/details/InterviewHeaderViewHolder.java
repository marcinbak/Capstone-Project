package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

/**
 * Created by marcinbak on 11.04.17.
 */

public class InterviewHeaderViewHolder extends ViewHolder<InterviewsHeader> {

  public static class Factory extends ViewHolderFactory<InterviewsHeader> {

    public Factory() {
      super(InterviewsHeader.class);
    }

    @Override
    public ViewHolder<InterviewsHeader> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_interview_header, parent, false);
      return new InterviewHeaderViewHolder(view);
    }
  }

  private InterviewHeaderViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(InterviewsHeader model) {
  }

  @OnClick(R.id.add_btn)
  void addInterviewClicked() {
    // TODO start adding interview
  }
}
