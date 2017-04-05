package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import de.neofonie.udacity.capstone.hirefy.base.BaseFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;

/**
 * Created by marcinbak on 05/04/2017.
 */

public class CandidateDetailFragment extends BaseFragment {

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }
}
