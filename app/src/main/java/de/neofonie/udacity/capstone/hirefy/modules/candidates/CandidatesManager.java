package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;

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

  public void createCandidate(CandidateCreate create) {
    String key = mDb.getReference(CANDIDATES_REF).push().getKey();

    Map<String, Object> map = new HashMap<>(2);
    map.put(CANDIDATES_REF + "/" + key, create);
    map.put(CANDIDATE_DETAILS_REF + "/" + key, create);

    mDb.getReference().updateChildren(map);
  }

}
