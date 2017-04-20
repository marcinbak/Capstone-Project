package de.neofonie.udacity.capstone.hirefy.modules.candidates;

/**
 * Created by marcinbak on 10/04/2017.
 */
public class Interview {

  String key;
  String type;
  Long   timestamp;
  String questionSetUid;
  String resultsUid;

  public Interview() {
  }

  public String getType() {
    return type;
  }

  public String getKey() {
    return key;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public String getQuestionSetUid() {
    return questionSetUid;
  }

  public String getResultsUid() {
    return resultsUid;
  }
}
