package de.neofonie.udacity.capstone.hirefy.ui.candidates.edit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.InterviewCreate;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.model.DateEvent;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.model.TimeEvent;
import de.neofonie.udacity.capstone.hirefy.ui.datetime.DatePickerDialogFragment;
import de.neofonie.udacity.capstone.hirefy.ui.datetime.DatePickerDialogFragmentBuilder;
import de.neofonie.udacity.capstone.hirefy.ui.datetime.TimePickerDialogFragment;
import de.neofonie.udacity.capstone.hirefy.ui.datetime.TimePickerDialogFragmentBuilder;
import de.neofonie.udacity.capstone.hirefy.utils.EventBus;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import org.parceler.Parcel;
import org.parceler.Parcels;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by marcinbak on 11.04.17.
 */
@FragmentWithArgs
public class AddInterviewFragment extends BaseDialogFragment {

  public final static String TAG = "AddInterviewFragment";

  private final static String STATE_KEY = "STATE_KEY";

  @Inject CandidatesManager mCandidatesManager;
  @Inject EventBus          mEventBus;

  @BindView(R.id.root_view)   View     mRootView;
  @BindView(R.id.type_tv)     EditText mTypeEdit;
  @BindView(R.id.date_btn)    Button   mDateBtn;
  @BindView(R.id.time_btn)    Button   mTimeBtn;
  @BindView(R.id.confirm_btn) Button   mConfiBtn;

  @Arg String candidateUuid;

  private State mState = new State();

  @Parcel
  static class State {
    Integer day;
    Integer month;
    Integer year;

    Integer hour;
    Integer minutes;

    boolean hasTime() {
      return hour != null && minutes != null;
    }
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    FragmentArgs.inject(this);

    mEventBus
        .observe(DateEvent.class)
        .subscribe(new Consumer<DateEvent>() {
          @Override
          public void accept(@NonNull DateEvent dateEvent) throws Exception {
            onDateChanged(dateEvent);
          }
        });

    mEventBus
        .observe(TimeEvent.class)
        .subscribe(new Consumer<TimeEvent>() {
          @Override
          public void accept(@NonNull TimeEvent timeEvent) throws Exception {
            onTimeChanged(timeEvent);
          }
        });
  }

  private void onTimeChanged(TimeEvent timeEvent) {
    mState.hour = timeEvent.getHour();
    mState.minutes = timeEvent.getMinutes();

    mTimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", mState.hour, mState.minutes));
  }

  private void onDateChanged(DateEvent dateEvent) {
    mState.year = dateEvent.getYear();
    mState.month = dateEvent.getMonth();
    mState.day = dateEvent.getDay();

    mDateBtn.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", mState.day, mState.month, mState.year));
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null && savedInstanceState.containsKey(STATE_KEY)) {
      mState = Parcels.unwrap(savedInstanceState.getParcelable(STATE_KEY));
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(STATE_KEY, Parcels.wrap(mState));
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.add_interview_fragment, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.confirm_btn)
  void confirmClicked(View view) {
    view.setEnabled(false);

    InterviewCreate interview = new InterviewCreate();
    interview.setType(mTypeEdit.getText().toString());
    Calendar date = Calendar.getInstance();
    date.setTimeZone(TimeZone.getDefault());
    date.set(mState.year, mState.month, mState.day, mState.hour, mState.minutes);
    interview.setTimestamp(date.getTimeInMillis());

    mCandidatesManager.createInterview(candidateUuid, interview);
    closeFragment();
  }

  private void closeFragment() {
    Activity activity = getActivity();
    if (activity != null) {
      activity.onBackPressed();
    }
  }

  @OnClick(R.id.date_btn)
  void dateClicked() {
    new DatePickerDialogFragmentBuilder()
        .dateOnly(mState.hasTime())
        .build()
        .show(getChildFragmentManager(), DatePickerDialogFragment.TAG);
  }

  @OnClick(R.id.time_btn)
  void timeClicked() {
    new TimePickerDialogFragmentBuilder()
        .build()
        .show(getChildFragmentManager(), TimePickerDialogFragment.TAG);
  }

}