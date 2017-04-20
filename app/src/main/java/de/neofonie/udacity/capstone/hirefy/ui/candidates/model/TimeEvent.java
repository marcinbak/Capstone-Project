package de.neofonie.udacity.capstone.hirefy.ui.candidates.model;

import de.neofonie.udacity.capstone.hirefy.utils.BusEvent;

/**
 * Created by marcinbak on 20.04.17.
 */
public class TimeEvent extends BusEvent {

  final Integer hour;
  final Integer minutes;

  public TimeEvent(Integer hour, Integer minutes) {
    this.hour = hour;
    this.minutes = minutes;
  }

  public Integer getHour() {
    return hour;
  }

  public Integer getMinutes() {
    return minutes;
  }

}
