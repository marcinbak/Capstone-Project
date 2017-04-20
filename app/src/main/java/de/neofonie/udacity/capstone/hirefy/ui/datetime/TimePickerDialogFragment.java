package de.neofonie.udacity.capstone.hirefy.ui.datetime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.model.TimeEvent;
import de.neofonie.udacity.capstone.hirefy.utils.EventBus;

import javax.inject.Inject;
import java.util.Calendar;

/**
 * Created by marcinbak on 20.04.17.
 */
@FragmentWithArgs
public class TimePickerDialogFragment extends BaseDialogFragment implements TimePickerDialog.OnTimeSetListener {

  public static final String TAG = "TimePickerDialogFragment";

  @Inject EventBus mEventBus;

  @Arg(required = false) Integer hour;
  @Arg(required = false) Integer minutes;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current time as the default values for the picker
    final Calendar c = Calendar.getInstance();

    int hour = this.hour != null ? this.hour : c.get(Calendar.HOUR_OF_DAY);
    int minute = this.minutes != null ? this.minutes : c.get(Calendar.MINUTE);
    // Create a new instance of TimePickerDialog and return it
    return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
  }

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    mEventBus.send(new TimeEvent(hourOfDay, minute));
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }
}