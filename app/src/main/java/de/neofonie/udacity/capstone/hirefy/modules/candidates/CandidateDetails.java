package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import java.util.List;

/**
 * Created by marcinbak on 05/04/2017.
 */
public class CandidateDetails {

  String          key;
  String          firstName;
  String          lastName;
  String          position;
  String          createTime; // ServerValue.TIMESTAMP
  List<Comment>   comments;
  List<Interview> interviews;
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

  public String getCreateTime() {
    return createTime;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public List<Interview> getInterviews() {
    return interviews;
  }
}
