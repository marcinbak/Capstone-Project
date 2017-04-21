package de.neofonie.udacity.capstone.hirefy.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;

import java.util.Locale;

/**
 * Created by marcinbak on 20.04.17.
 */
public class CalendarUtils {

  private final static long HALF_HOUR = 30 * 60 * 1000;

  public static void addEvent(Activity context, Interview interview, CandidateDetails details) {
    Intent intent = new Intent(Intent.ACTION_EDIT);
    intent.setData(CalendarContract.Events.CONTENT_URI);

    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, interview.getTimestamp());
    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, interview.getTimestamp() + HALF_HOUR);
    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

    String title = String.format(Locale.getDefault(), "%s %s - Interview", details.getFirstName(), details.getLastName());

//    intent.putExtra(CalendarContract.Events._ID, id);
    intent.putExtra(CalendarContract.Events.TITLE, title);
    intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
//    intent.putExtra(CalendarContract.ExtendedProperties., "My Guest House");
    context.startActivity(intent);
  }

//  public static long getNewEventId(ContentResolver cr) {
//    Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, new String[]{"MAX(_id) as max_id"}, null, null, "_id");
//
//    cursor.moveToFirst();
//    long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));
//    cursor.close();
//    return max_val + 1;
//}

}
