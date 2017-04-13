package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;
import de.neofonie.udacity.capstone.hirefy.utils.FirebaseUtils;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcinbak on 05/04/2017.
 */
@ApplicationScope
public class CandidatesManager {

  private final static String CANDIDATES_REF        = "candidates";
  private final static String CANDIDATE_DETAILS_REF = "candidatesFull";

  private FirebaseDatabase mDb;

  @Inject
  public CandidatesManager() {
    mDb = FirebaseDatabase.getInstance();
  }

  public DatabaseReference getCandidatesList() {
    return mDb.getReference(CANDIDATES_REF);
  }

  public DatabaseReference getCandidateDetails(FbCandidate candidate) {
    return mDb.getReference(CANDIDATE_DETAILS_REF + "/" + candidate.getUuid());
  }

  public Single<FbCandidate> createCandidate(CandidateCreate create) {
    final String key = mDb.getReference(CANDIDATES_REF).push().getKey();

    final Map<String, Object> map = new HashMap<>(2);
    map.put(CANDIDATES_REF + "/" + key, create);
    map.put(CANDIDATE_DETAILS_REF + "/" + key, create);

    DatabaseReference ref = mDb.getReference(CANDIDATES_REF + "/" + key);

    return FirebaseUtils.updateToCompletable(mDb.getReference().updateChildren(map))
        .andThen(FirebaseUtils.singleFrom(ref, FbCandidate.class))
        .map(new Function<FbCandidate, FbCandidate>() {
          @Override
          public FbCandidate apply(@NonNull FbCandidate fbCandidate) throws Exception {
            fbCandidate.setKey(key);
            return fbCandidate;
          }
        });
  }

  public void sendComment(String uuid, String comment) {
    String refStr = CANDIDATE_DETAILS_REF + "/" + uuid + "/comments";
    Comment commentObj = new Comment();
    commentObj.setAuthor(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    commentObj.setText(comment);

    DatabaseReference ref = mDb.getReference(refStr);
    String key = ref.push().getKey();
    ref.child(key).setValue(commentObj);
  }
}
