package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;

/**
 * Created by marcinbak on 05/04/2017.
 */
public class CandidatesAdapter extends FirebaseRecyclerAdapter<FbCandidate, FbCandidateVH> {

  private boolean isTablet = false;

  /**
   * @param modelLayout This is the layout used to represent a single item in the list.
   *                    You will be responsible for populating an instance of the corresponding
   *                    view with the data from an instance of modelClass.
   * @param ref         The Firebase location to watch for data changes. Can also be a slice of a location,
   *                    using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
   */
  public CandidatesAdapter(int modelLayout, Query ref) {
    super(FbCandidate.class, modelLayout, FbCandidateVH.class, ref);
  }

  @Override
  protected void populateViewHolder(FbCandidateVH viewHolder, FbCandidate model, int position) {
    viewHolder.mCandidateNameTv.setText(model.getFirstName() + " " + model.getLastName());
    viewHolder.mPositionTv.setText(model.getPosition());
    if (position == 0 && !isTablet) {
      // TODO show extra button to go to interview??
    }
  }
}
