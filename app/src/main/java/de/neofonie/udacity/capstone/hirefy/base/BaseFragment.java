package de.neofonie.udacity.capstone.hirefy.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import butterknife.Unbinder;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;

/**
 * Created by marcinbak on 03/04/2017.
 */

public abstract class BaseFragment extends Fragment {

  protected Unbinder mUnbinder;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    inject(getComponent(context));
  }

  protected abstract void inject(ActivityComponent component);

  private ActivityComponent getComponent(Context context) {
    if (context instanceof BaseActivity) {
      return ((BaseActivity) context).getComponent();
    }

    throw new IllegalArgumentException("This fragment should be attached to activity extending BaseActivity for Dagger2 to work correctly.");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }

  }
}
