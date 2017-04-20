package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.support.annotation.StringRes;

/**
 * Created by marcinbak on 20.04.17.
 */
public class SimpleText {

  @StringRes
  private int mText;

  public SimpleText(@StringRes int text) {
    mText = text;
  }

  @StringRes
  public int getText() {
    return mText;
  }
}
