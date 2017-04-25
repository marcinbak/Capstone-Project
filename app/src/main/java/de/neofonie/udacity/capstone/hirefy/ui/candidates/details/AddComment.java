package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;

/**
 * Created by marcinbak on 11.04.17.
 */
public class AddComment {

  private CandidateDetails mCandidate;
  private String           mSavedComment;

  public AddComment(CandidateDetails candidate, String savedComment) {
    mCandidate = candidate;
    mSavedComment = savedComment;
  }

  public CandidateDetails getCandidate() {
    return mCandidate;
  }

  public String getSavedComment() {
    return mSavedComment;
  }

  public void clear() {
    mSavedComment = null;
  }
}
