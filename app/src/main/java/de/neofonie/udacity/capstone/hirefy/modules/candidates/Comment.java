package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import de.neofonie.udacity.capstone.hirefy.utils.DateUtils;

/**
 * Created by marcinbak on 10/04/2017.
 */
public class Comment {

  String text;
  String date;
  Object dateUnix = ServerValue.TIMESTAMP;
  String author;

  public Comment() {
  }

  public String getAuthor() {
    return author;
  }

  public String getText() {
    return text;
  }

  @Exclude
  public String getDate() {
    if (date == null) {
      long timestamp = (long) dateUnix;
      date = DateUtils.format(timestamp);
    }
    return date;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Used by firebase
   *
   * @return
   */
  public Object getDateUnix() {
    return dateUnix;
  }
}
