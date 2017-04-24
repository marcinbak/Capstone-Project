package de.neofonie.udacity.capstone.hirefy.modules.calendar;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import de.neofonie.udacity.capstone.hirefy.BuildConfig;
import de.neofonie.udacity.capstone.hirefy.dagger.qualifier.ForApplication;
import de.neofonie.udacity.capstone.hirefy.dagger.scope.ApplicationScope;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.CandidateDetails;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Interview;

import javax.inject.Inject;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by marcinbak on 21.04.17.
 */
@ApplicationScope
public class CalendarManager {

  @Inject
  @ForApplication
  Context mContext;

  @Inject
  public CalendarManager() {
  }

  private final static long HALF_HOUR = 30 * 60 * 1000;

  private final static String PROP_APP_ID       = "APP_ID";
  private final static String PROP_INTERVIEW_ID = "INTERVIEW_ID";
  private final static String PROP_CANDIDATE_ID = "CANDIDATE_ID";

  public void addEvent(Context context, Interview interview, CandidateDetails details) {
    long calendarId = getPrimaryCalendarId();
    long eventID = insertEvent(calendarId, interview, details);
    insertExtendedTag(eventID, interview, details);

    Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
    Intent intent = new Intent(Intent.ACTION_VIEW).setData(uri);
    context.startActivity(intent);
  }

  private long insertEvent(long calendarId, Interview interview, CandidateDetails details) {
    ContentResolver cr = mContext.getContentResolver();
    ContentValues values = new ContentValues();
    values.put(CalendarContract.Events.DTSTART, interview.getTimestamp());
    values.put(CalendarContract.Events.DTEND, interview.getTimestamp() + HALF_HOUR);
//    values.put(CalendarContract.Events.HAS_EXTENDED_PROPERTIES, 1);
    values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
    values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
    values.put(CalendarContract.Events.CUSTOM_APP_PACKAGE, BuildConfig.APPLICATION_ID);

    String title = String.format(Locale.getDefault(), "%s %s - Interview", details.getFirstName(), details.getLastName());
    values.put(CalendarContract.Events.TITLE, title);

    values.put(CalendarContract.Events.DESCRIPTION, "Interview " + details.getFirstName() + " " + details.getLastName() + " for position " + details.getPosition());

    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
    return Long.parseLong(uri.getLastPathSegment());
  }

  private void insertExtendedTag(long eventId, Interview interview, CandidateDetails details) {
    Uri extendedPropertyURI = CalendarContract.ExtendedProperties.CONTENT_URI
        .buildUpon()
        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "accountName")
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
        .build();

    ContentResolver cr = mContext.getContentResolver();

    ContentValues values = new ContentValues();
    values.put(CalendarContract.ExtendedProperties.EVENT_ID, eventId);
    values.put(CalendarContract.ExtendedProperties.NAME, PROP_INTERVIEW_ID);
    values.put(CalendarContract.ExtendedProperties.VALUE, interview.getKey());
    cr.insert(extendedPropertyURI, values);

    values.put(CalendarContract.ExtendedProperties.NAME, PROP_APP_ID);
    values.put(CalendarContract.ExtendedProperties.VALUE, BuildConfig.APPLICATION_ID);
    cr.insert(extendedPropertyURI, values);

    values.put(CalendarContract.ExtendedProperties.NAME, PROP_CANDIDATE_ID);
    values.put(CalendarContract.ExtendedProperties.VALUE, details.getUuid());
    cr.insert(extendedPropertyURI, values);
  }

  public void getComingInterviews() {

  }

  public Long getPrimaryCalendarId() {
    final int PROJECTION_ID_INDEX = 0;

    final String projection[] = {
        CalendarContract.Calendars._ID,
    };

    final String value[] = {
        "1",
    };

    ContentResolver contentResolver = mContext.getContentResolver();
    Uri uri = CalendarContract.Calendars.CONTENT_URI;

//    CalendarContract.CalendarColumns.IS_PRIMARY + "=1"

    Cursor cur = contentResolver.query(uri, projection, CalendarContract.Calendars.IS_PRIMARY + " = ?", value, null);

    if (cur != null && cur.moveToNext()) {
      return cur.getLong(PROJECTION_ID_INDEX);
    }

    return null;
  }
}
