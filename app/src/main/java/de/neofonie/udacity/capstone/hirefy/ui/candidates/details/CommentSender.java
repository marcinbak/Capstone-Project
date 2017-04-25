package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

/**
 * Created by marcinbak on 11.04.17.
 */

public interface CommentSender {

  void sendComment(String uuid, String comment);

  void updateComment(String newText);
}