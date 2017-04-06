package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import org.parceler.Parcel;

/**
 * Created by marcinbak on 05/04/2017.
 */
@Parcel
public class FbCandidate {

  String uuid;
  String firstName;
  String lastName;
  String position;
  String createTime; // ServerValue.TIMESTAMP

  //String linkedInProfile;

  public String getUuid() {
    return uuid;
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
}
