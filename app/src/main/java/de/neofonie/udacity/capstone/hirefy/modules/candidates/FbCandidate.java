package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.Exclude;
import de.neofonie.udacity.capstone.hirefy.utils.DateUtils;
import org.parceler.Parcel;

/**
 * Created by marcinbak on 05/04/2017.
 */
@Parcel
public class FbCandidate {

  String key;
  String firstName;
  String lastName;
  String position;
  Long dateUnix;
  String createDate;
  //String linkedInProfile;

  public String getUuid() {
    return key;
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

  public void setKey(String key) {
    this.key = key;
  }

  @Exclude
  public String getCreateDate() {
    if (createDate == null) {
      createDate = DateUtils.format((long) dateUnix);
    }
    return createDate;
  }
}
