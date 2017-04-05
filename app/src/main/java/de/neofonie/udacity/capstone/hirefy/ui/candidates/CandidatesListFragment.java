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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;

import javax.inject.Inject;

/**
 * Created by marcinbak on 05/04/2017.
 */
public class CandidatesListFragment extends BaseFragment {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  @Inject CandidatesManager mCandidatesManager;
  private CandidatesAdapter mAdapter;

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
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
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mAdapter.cleanup();
  }
}