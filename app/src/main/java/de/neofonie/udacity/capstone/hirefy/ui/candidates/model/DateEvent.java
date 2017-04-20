package de.neofonie.udacity.capstone.hirefy.ui.candidates.model;

import de.neofonie.udacity.capstone.hirefy.utils.BusEvent;

/**
 * Created by marcinbak on 20.04.17.
 */
public class DateEvent extends BusEvent {

  final Integer day;
  final Integer month;
  final Integer year;

  public DateEvent(Integer day, Integer month, Integer year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public Integer getDay() {
    return day;
  }

  public Integer getMonth() {
    return month;
  }

  public Integer getYear() {
    return year;
  }
}
