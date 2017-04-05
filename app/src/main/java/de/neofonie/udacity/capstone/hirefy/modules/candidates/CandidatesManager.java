package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by marcinbak on 05/04/2017.
 */
@ApplicationScope
public class CandidatesManager {

  private FirebaseDatabase mDb;

  @Inject
  public CandidatesManager() {
    mDb = FirebaseDatabase.getInstance();
  }

  public DatabaseReference getCandidatesList() {
    return mDb.getReference("candidates");
  }

}
