package de.neofonie.udacity.capstone.hirefy.ui.candidates.edit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateCreate;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CandidateSelectedListener;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;

import javax.inject.Inject;

/**
 * Created by marcinbak on 11.04.17.
 */
@FragmentWithArgs
public class AddCandidateFragment extends BaseDialogFragment {

  private final static String TAG = "AddCandidateFragment";

  @Inject CandidatesManager mCandidatesManager;

  @BindView(R.id.root_view)    View     mRootView;
  @BindView(R.id.firstname_tv) EditText mFirstNameEdit;
  @BindView(R.id.lastname_tv)  EditText mLastNameEdit;
  @BindView(R.id.position_tv)  EditText mPositionEdit;

  private Disposable mDisposable = Disposables.disposed();

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

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.create_candidate_fragment, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.confirm_btn)
  void confirmClicked(View view) {
    view.setEnabled(false);

    CandidateCreate candidate = new CandidateCreate();
    candidate.setFirstName(mFirstNameEdit.getText().toString());
    candidate.setLastName(mLastNameEdit.getText().toString());
    candidate.setPosition(mPositionEdit.getText().toString());

    mDisposable = mCandidatesManager.createCandidate(candidate)
        .subscribe(new Consumer<FbCandidate>() {
          @Override
          public void accept(@NonNull FbCandidate fbCandidate) throws Exception {
            createdCandidate(fbCandidate);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(@NonNull Throwable throwable) throws Exception {
            failedToCreate(throwable);
          }
        });
  }

  private void failedToCreate(Throwable throwable) {
    Log.e(TAG, "Failed to save candidate.", throwable);
    Toast.makeText(getContext(), R.string.candidate_save_failed_msg, Toast.LENGTH_SHORT).show();
  }

  private void createdCandidate(FbCandidate fbCandidate) {
    Activity activity = getActivity();
    if (activity instanceof CandidateSelectedListener) {
      ((CandidateSelectedListener) activity).onCandidateSelected(fbCandidate, -1);
    }
    activity.onBackPressed();
  }

  @Override
  public void onStop() {
    super.onStop();
    mDisposable.dispose();
  }
}