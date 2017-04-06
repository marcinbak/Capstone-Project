package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import de.neofonie.udacity.capstone.hirefy.modules.candidates.FbCandidate;

/**
 * Created by marcinbak on 06/04/2017.
 */
public interface CandidateSelectedListener {

  void onCandidateSelected(FbCandidate candidate, int index);

}