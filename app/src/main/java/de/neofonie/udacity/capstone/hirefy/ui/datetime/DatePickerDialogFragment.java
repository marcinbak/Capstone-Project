package de.neofonie.udacity.capstone.hirefy.ui.datetime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.model.DateEvent;
import de.neofonie.udacity.capstone.hirefy.utils.EventBus;

import javax.inject.Inject;
import java.util.Calendar;

/**
 * Created by marcinbak on 20.04.17.
 */
@FragmentWithArgs
public class DatePickerDialogFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener {

  public static final String TAG = "DatePickerDialogFragment";

  @Arg(required = false) boolean dateOnly = true;
  @Arg(required = false) Integer day;
  @Arg(required = false) Integer month;
  @Arg(required = false) Integer year;

  @Inject EventBus mEventBus;

  @Override
  public void onStart() {
    super.onStart();
    FragmentArgs.inject(this);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = this.year != null ? this.year : c.get(Calendar.YEAR);
    int month = this.month != null ? this.month : c.get(Calendar.MONTH);
    int day = this.day != null ? this.day : c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    mEventBus.send(new DateEvent(day, month, year));
    if (!dateOnly) {
      TimePickerDialogFragment fragment = new TimePickerDialogFragmentBuilder().build();
      fragment.show(getFragmentManager(), TimePickerDialogFragment.TAG);
    }
    dismiss();
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }
}