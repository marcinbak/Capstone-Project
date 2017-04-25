package de.neofonie.udacity.capstone.hirefy.ui.candidates.edit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.base.BaseDialogFragment;
import de.neofonie.udacity.capstone.hirefy.dagger.ActivityComponent;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateCreate;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidatesManager;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;
import de.neofonie.udacity.capstone.hirefy.modules.drive.FileMetadataLoader;
import de.neofonie.udacity.capstone.hirefy.ui.PlayServicesProvider;
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
public class AddCandidateFragment extends BaseDialogFragment implements LoaderManager.LoaderCallbacks<Metadata> {

  private final static String TAG                 = "AddCandidateFragment";
  private final static int    REQUEST_CODE_OPENER = 26317;
  private final static String DRIVE_ID_EXTRA      = "DRIVE_ID_EXTRA";

  @Inject CandidatesManager mCandidatesManager;

  @BindView(R.id.root_view)    View     mRootView;
  @BindView(R.id.firstname_tv) EditText mFirstNameEdit;
  @BindView(R.id.lastname_tv)  EditText mLastNameEdit;
  @BindView(R.id.position_tv)  EditText mPositionEdit;
  @BindView(R.id.resume_tv)    TextView mResumeTv;

  private Disposable mDisposable = Disposables.disposed();

  private String mSelectedFile;

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    FragmentArgs.inject(this);

    if (getDialog() != null) {
      int dialogWidth = getResources().getDimensionPixelSize(R.dimen.dialog_size_width);
      int dialogHeight = getResources().getDimensionPixelSize(R.dimen.dialog_size_height);
      getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
      getDialog().setTitle(getString(R.string.create_candidate));
    }

  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    return dialog;
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
    candidate.setResume(mSelectedFile);

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
    if (getDialog() != null) {
      dismiss();
    } else {
      if (activity != null) {
        activity.onBackPressed();
      }
    }
  }

  @OnClick(R.id.resume_btn)
  void resumeClicked(View view) {
    IntentSender intentSender = Drive.DriveApi
        .newOpenFileActivityBuilder()
        .build(getGoogleClient());

    try {
      startIntentSenderForResult(intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0, null);
    } catch (IntentSender.SendIntentException e) {
      Log.w(TAG, "Unable to send intent", e);
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    mDisposable.dispose();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_OPENER && resultCode == Activity.RESULT_OK) {
      DriveId driveId = data.getParcelableExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
      Bundle args = new Bundle(1);
      args.putString(DRIVE_ID_EXTRA, driveId.encodeToString());
      getLoaderManager().destroyLoader(LOADER_ID);
      getLoaderManager().initLoader(LOADER_ID++, args, this);
    }
  }

  private int LOADER_ID = 0;

  private GoogleApiClient getGoogleClient() {
    if (getActivity() instanceof PlayServicesProvider) {
      return ((PlayServicesProvider) getActivity()).getGoogleApiClient();
    } else {
      throw new IllegalStateException("The parent activity must implement PlayServicesProvider interface.");
    }
  }

  @Override
  public Loader<Metadata> onCreateLoader(int id, Bundle args) {
    return new FileMetadataLoader(getContext(), args.getString(DRIVE_ID_EXTRA), getGoogleClient());
  }

  @Override
  public void onLoadFinished(Loader<Metadata> loader, Metadata data) {
    mSelectedFile = data.getEmbedLink();
    mResumeTv.setText(data.getTitle());
  }

  @Override
  public void onLoaderReset(Loader<Metadata> loader) {
    Log.i(TAG, "Loader reset");
  }
}