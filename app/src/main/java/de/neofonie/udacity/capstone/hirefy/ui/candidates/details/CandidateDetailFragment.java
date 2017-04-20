package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.CustomRecyclerAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcinbak on 05/04/2017.
 */
@FragmentWithArgs
public class CandidateDetailFragment extends BaseFragment implements ValueEventListener {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  private CustomRecyclerAdapter mAdapter;
  private DatabaseReference     mRef;

  @Inject CandidatesManager mCandidatesManager;

  @Arg(required = false, bundler = ParcelerArgsBundler.class) FbCandidate mCandidate;

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FragmentArgs.inject(this);
    if (mCandidate != null) {
      mRef = mCandidatesManager.getCandidateDetails(mCandidate);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.candidate_details_fragment, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (mRef != null) mRef.addValueEventListener(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mRef != null) mRef.removeEventListener(this);
  }

  private void initViews() {
    mAdapter = new CustomRecyclerAdapter.Builder()
        .registerFactory(new InterviewHeaderViewHolder.Factory())
        .registerFactory(new SimpleTextViewHolder.Factory())
        .registerFactory(new LabelViewHolder.Factory())
        .registerFactory(new CandidateDetailsViewHolder.Factory())
        .registerFactory(new InterviewViewHolder.Factory())
        .registerFactory(new CommentViewHolder.Factory())
        .registerFactory(new AddCommentViewHolder.Factory())
        .build();

    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setAdapter(mAdapter);
  }

  public void setCandidate(FbCandidate candidate) {
    mCandidate = candidate;
    mRef.removeEventListener(this);
    mRef = mCandidatesManager.getCandidateDetails(mCandidate);
    mRef.addValueEventListener(this);
  }

  @Override
  public void onDataChange(DataSnapshot dataSnapshot) {
    CandidateDetails details = new CandidateDetails(dataSnapshot);

    List<Object> data = new ArrayList<>(details.getSize() + 4); // interviews + details + 2xlabel + add comment + details

    data.add(details);

    data.add(new InterviewsHeader(details));
    if (details.hasInterviews()) {
      data.addAll(details.getInterviews());
    } else {
      data.add(new SimpleText(R.string.no_interviews));
    }

    data.add(getString(R.string.comments_label));
    if (details.hasComments()) {
      data.addAll(details.getComments());
    }

    data.add(new AddComment(details));

    mAdapter.setData(data);
  }

  @Override
  public void onCancelled(DatabaseError databaseError) {
    Toast.makeText(getContext(), "Error during loading candidate information", Toast.LENGTH_SHORT).show();
  }
}
