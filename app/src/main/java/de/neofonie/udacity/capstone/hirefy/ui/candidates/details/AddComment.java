package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;

/**
 * Created by marcinbak on 11.04.17.
 */
public class AddComment {

  private CandidateDetails mCandidate;

  public AddComment(CandidateDetails candidate) {
    mCandidate = candidate;
  }

  public CandidateDetails getCandidate() {
    return mCandidate;
  }
}
