package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import javax.inject.Inject;

/**
 * Created by marcinbak on 05/04/2017.
 */
@FragmentWithArgs
public class CandidatesListFragment extends BaseFragment {

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
    // TODO create new candidate
  }

  public void setSelectedCandidate(FbCandidate candidate, int index) {
    mAdapter.setSelectedPosition(index);
  }
}
