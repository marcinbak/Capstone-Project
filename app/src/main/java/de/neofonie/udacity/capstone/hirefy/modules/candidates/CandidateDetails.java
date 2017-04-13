package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.DataSnapshot;
import de.neofonie.udacity.capstone.hirefy.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by marcinbak on 05/04/2017.
 */
public class CandidateDetails {

  String          key;
  String          firstName;
  String          lastName;
  String          position;
  String          createTime;
  String          resume;
  List<Comment>   comments;
  List<Interview> interviews;
  //String linkedInProfile;

  public CandidateDetails() {
  }

  public CandidateDetails(DataSnapshot snapshot) {
    key = snapshot.getKey();

    for (DataSnapshot s : snapshot.getChildren()) {
      switch (s.getKey()) {
        case "firstName":
          firstName = s.getValue(String.class);
          break;
        case "lastName":
          lastName = s.getValue(String.class);
          break;
        case "position":
          position = s.getValue(String.class);
          break;
        case "createTime":
          createTime = s.getValue(String.class);
          break;
        case "comments":
          comments = new ArrayList<>((int) s.getChildrenCount());
          for (DataSnapshot ss : s.getChildren()) {
            comments.add(ss.getValue(Comment.class));
          }
          break;
        case "interviews":
          interviews = new ArrayList<>((int) s.getChildrenCount());
          for (DataSnapshot ss : s.getChildren()) {
            Interview interview = ss.getValue(Interview.class);
            interview.key = ss.getKey();
            interviews.add(interview);
          }
          break;
        case "dateUnix":
          createTime = DateUtils.format(s.getValue(Long.class));
          break;
        case "resume":
          resume = s.getValue(String.class);
      }
    }
  }

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

  public Collection<Comment> getComments() {
    return comments;
  }

  public Collection<Interview> getInterviews() {
    return interviews;
  }

  /**
   * Returns sum of comments and interviews lists sizes.
   *
   * @return
   */
  public int getSize() {
    return getCommentsSize() + getInterviewsSize();
  }

  public int getCommentsSize() {
    if (comments != null) {
      return comments.size();
    }
    return 0;
  }

  public int getInterviewsSize() {
    if (interviews != null) {
      return interviews.size();
    }
    return 0;
  }

  public boolean hasInterviews() {
    return getInterviewsSize() > 0;
  }

  public boolean hasComments() {
    return getCommentsSize() > 0;
  }

  public String getResume() {
    return resume;
  }
}
