package de.neofonie.udacity.capstone.hirefy.modules.candidates;

import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by marcinbak on 06/04/2017.
 */

public class CandidateCreate {

  String firstName;
  String lastName;
  String position;
  String resume;
  final Map<String, String> createTime = ServerValue.TIMESTAMP;

}
