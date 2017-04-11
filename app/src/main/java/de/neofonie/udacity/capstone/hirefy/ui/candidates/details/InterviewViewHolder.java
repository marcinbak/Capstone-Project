package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

/**
 * Created by marcinbak on 11.04.17.
 */
public class InterviewViewHolder extends ViewHolder<Interview> {

  public static class Factory extends ViewHolderFactory<Interview> {

    public Factory() {
      super(Interview.class);
    }

    @Override
    public ViewHolder<Interview> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_interview, parent, false);
      return new InterviewViewHolder(view);
    }
  }

  @BindView(R.id.title_tv)        TextView mTitleTv;
  @BindView(R.id.planned_date_tv) TextView mPlannedDateTv;


  private InterviewViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(Interview model) {
    mTitleTv.setText(model.getTitle());
    mPlannedDateTv.setText(model.getPlannedDate());
  }
}