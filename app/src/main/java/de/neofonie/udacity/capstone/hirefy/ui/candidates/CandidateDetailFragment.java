package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import de.neofonie.udacity.capstone.hirefy.base.BaseFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;

/**
 * Created by marcinbak on 05/04/2017.
 */
@FragmentWithArgs
public class CandidateDetailFragment extends BaseFragment {

  @Arg(required = false, bundler = ParcelerArgsBundler.class) FbCandidate mCandidate;

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FragmentArgs.inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = new View(getContext());
    mUnbinder = ButterKnife.bind(this, view);
    refreshViews();
    return view;
  }

  public void setCandidate(FbCandidate candidate) {
    this.mCandidate = candidate;
    refreshViews();
  }

  private void refreshViews() {

  }
}
