package de.neofonie.udacity.capstone.hirefy.dagger;

import dagger.Subcomponent;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ActivityScope;
import de.neofonie.udacity.capstone.hirefy.ui.LoginActivity;
import de.neofonie.udacity.capstone.hirefy.ui.SplashActivity;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CandidatesActivity;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.CandidatesListFragment;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.details.CandidateDetailFragment;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.details.CandidateDetailsActivity;
import de.neofonie.udacity.capstone.hirefy.ui.candidates.edit.AddCandidateFragment;

/**
 * Created by marcinbak on 03/04/2017.
 */
@Subcomponent(modules = {
    ActivityModule.class
})
@ActivityScope
public interface ActivityComponent {

//  void inject(BaseActivity activity);

  void inject(SplashActivity activity);

  void inject(LoginActivity activity);

  void inject(CandidateDetailFragment candidateDetailFragment);

  void inject(CandidatesListFragment candidatesListFragment);

  void inject(CandidatesActivity candidatesActivity);

  void inject(CandidateDetailsActivity candidateDetailsActivity);

  void inject(AddCandidateFragment addCandidateFragment);
}
