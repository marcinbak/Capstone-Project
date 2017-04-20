package de.neofonie.udacity.capstone.hirefy.modules.candidates;

/**
 * Created by marcinbak on 10/04/2017.
 */
public class InterviewCreate {

  String key;
  String type;
  long   timestamp;

  public InterviewCreate() {
  }

  public String getType() {
    return type;
  }

  public String getKey() {
    return key;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
}
