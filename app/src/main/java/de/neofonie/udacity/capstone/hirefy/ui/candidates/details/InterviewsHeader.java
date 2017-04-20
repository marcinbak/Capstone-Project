package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;

/**
 * Created by marcinbak on 20.04.17.
 */

public class InterviewsHeader {

  private CandidateDetails mCandidate;

  public InterviewsHeader(CandidateDetails candidate) {
    mCandidate = candidate;
  }

  public CandidateDetails getCandidate() {
    return mCandidate;
  }
}
