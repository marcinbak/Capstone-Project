package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;

/**
 * Created by marcinbak on 20.04.17.
 */

public class InterviewViewModel {

  CandidateDetails details;
  Interview        interview;

  public InterviewViewModel(CandidateDetails details, Interview interview) {
    this.details = details;
    this.interview = interview;
  }

  public CandidateDetails getDetails() {
    return details;
  }

  public Interview getInterview() {
    return interview;
  }
}
