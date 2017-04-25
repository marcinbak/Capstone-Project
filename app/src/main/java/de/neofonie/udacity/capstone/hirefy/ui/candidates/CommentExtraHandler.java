package de.neofonie.udacity.capstone.hirefy.ui.candidates;

import android.os.Bundle;

import javax.inject.Inject;

/**
 * Created by marcinbak on 25.04.17.
 */
public class CommentExtraHandler {

  private final static String COMMENT_EXTRA = "COMMENT_EXTRA";

  private String currentText;

  @Inject
  public CommentExtraHandler() {
  }

  public void clearCurrentComment() {
    currentText = null;
  }

  public void updateCurrentText(String newText) {
    currentText = newText;
  }

  public void saveToExtras(Bundle outState) {
    outState.putString(COMMENT_EXTRA, currentText);
  }

  public void checkExtras(Bundle savedInstanceState) {
    if (savedInstanceState != null && savedInstanceState.containsKey(COMMENT_EXTRA)) {
      currentText = savedInstanceState.getString(COMMENT_EXTRA);
    }
  }

  public String getCurrentText() {
    return currentText;
  }
}
