package de.neofonie.udacity.capstone.hirefy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marcinbak on 11.04.17.
 */

public class DateUtils {

  private final static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

  public static String format(long timestamp) {
    return SDF.format(new Date(timestamp));
  }

}
