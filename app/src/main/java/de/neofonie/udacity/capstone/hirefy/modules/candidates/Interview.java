package de.neofonie.udacity.capstone.hirefy.modules.candidates;

/**
 * Created by marcinbak on 10/04/2017.
 */
public class Interview {

  String key;
  String title;
  String plannedDate;
  String questionSetUid;
  String resultsUid;

  public Interview() {
  }

  public String getTitle() {
    return title;
  }

  public String getKey() {
    return key;
  }

  public String getPlannedDate() {
    return plannedDate;
  }

  public String getQuestionSetUid() {
    return questionSetUid;
  }

  public String getResultsUid() {
    return resultsUid;
  }
}
