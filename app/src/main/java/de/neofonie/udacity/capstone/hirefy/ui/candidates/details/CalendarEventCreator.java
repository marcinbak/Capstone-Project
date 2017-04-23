package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;

/**
 * Created by marcinbak on 21.04.17.
 */
public interface CalendarEventCreator {

  void createAndOpenEvent(CandidateDetails candidate, Interview interview);

}