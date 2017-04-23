package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;
import de.neofonie.udacity.capstone.hirefy.utils.DateUtils;

/**
 * Created by marcinbak on 11.04.17.
 */
public class InterviewViewHolder extends ViewHolder<InterviewViewModel> {

  private InterviewViewModel mModel;

  public static class Factory extends ViewHolderFactory<InterviewViewModel> {

    public Factory() {
      super(InterviewViewModel.class);
    }

    @Override
    public ViewHolder<InterviewViewModel> build(ViewGroup parent) {
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
  public void bind(InterviewViewModel model) {
    mModel = model;
    mTitleTv.setText(model.getInterview().getType());
    mPlannedDateTv.setText(DateUtils.format(model.getInterview().getTimestamp()));
  }

  @OnClick(R.id.add_to_calendar_btn)
  void addToCalendar() {
    if (getContext() instanceof CalendarEventCreator) {
      ((CalendarEventCreator) getContext()).createAndOpenEvent(mModel.details, mModel.interview);
    } else {
      throw new IllegalStateException("Activity " + getContext().getClass().getSimpleName() + " must implement CalendarEventCreator interface");
    }
  }
}