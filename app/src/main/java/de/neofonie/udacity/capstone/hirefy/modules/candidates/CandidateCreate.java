package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.ServerValue;

/**
 * Created by marcinbak on 06/04/2017.
 */
public class CandidateCreate {

  String firstName;
  String lastName;
  String position;
  String resume;
  Object dateUnix = ServerValue.TIMESTAMP;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public void setResume(String resume) {
    this.resume = resume;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPosition() {
    return position;
  }

  public String getResume() {
    return resume;
  }

  public Object getDateUnix() {
    return dateUnix;
  }
}
