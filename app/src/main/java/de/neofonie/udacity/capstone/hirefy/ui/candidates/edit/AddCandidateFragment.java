package de.neofonie.udacity.capstone.hirefy.ui.candidates.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;

/**
 * Created by marcinbak on 11.04.17.
 */
@FragmentWithArgs
public class AddCandidateFragment extends BaseDialogFragment {

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    FragmentArgs.inject(this);
//    Dialog dialog = getDialog();
//    if (dialog != null) {
//      dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//    }
  }


  @BindView(R.id.root_view) View mRootView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.create_candidate_fragment, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }


}