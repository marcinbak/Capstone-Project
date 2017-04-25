package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddCandidateFragmentBuilder;

import javax.inject.Inject;

/**
 * Created by marcinbak on 05/04/2017.
 */
@FragmentWithArgs
public class CandidatesListFragment extends BaseFragment {

  private static final String SELECTION_POSITION = "SELECTION_POSITION";

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  @Inject CandidatesManager mCandidatesManager;
  private CandidatesAdapter mAdapter;

  @Arg boolean isTablet;

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
    View view = inflater.inflate(R.layout.candidates_list_fragment, container, false);
    mUnbinder = ButterKnife.bind(this, view);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    mRecyclerView.addItemDecoration(dividerItemDecoration);

    mAdapter = new CandidatesAdapter(R.layout.candidate_li, mCandidatesManager.getCandidatesList());
    mAdapter.setTablet(isTablet);
    if (isTablet && savedInstanceState != null && savedInstanceState.containsKey(SELECTION_POSITION)) {
      mAdapter.setSelectedPosition(savedInstanceState.getInt(SELECTION_POSITION));
    }
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mAdapter.cleanup();
  }

  @OnClick(R.id.add_candidate_fab)
  void onAddNewCandidateClick() {
    DialogFragment f = new AddCandidateFragmentBuilder().build();
    String tag = f.getClass().getSimpleName();
    if (isTablet) {
      f.show(getActivity().getSupportFragmentManager(), tag);
    } else {
      getActivity().getSupportFragmentManager()
          .beginTransaction()
          .setCustomAnimations(R.anim.right_in, R.anim.right_out, R.anim.right_in, R.anim.right_out)
          .add(R.id.candidates_frag, f, tag)
          .addToBackStack(tag)
          .commit();
    }
  }

  public void setSelectedCandidate(FbCandidate candidate, int index) {
    if (index < 0) {
      int size = mAdapter.getItemCount();
      for (int i = 0; i < size; i++) {
        if (candidate.getUuid().equals(mAdapter.getItem(i).getUuid())) {
          mAdapter.selectPosition(i);
          return;
        }
      }
    } else {
      mAdapter.selectPosition(index);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SELECTION_POSITION, mAdapter.getSelectedPosition());
  }
}
