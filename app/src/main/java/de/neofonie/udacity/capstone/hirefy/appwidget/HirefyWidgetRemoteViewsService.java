/*
 * (c) Neofonie Mobile GmbH (2017)
 *
 * This computer program is the sole property of Neofonie Mobile GmbH (http://mobile.neofonie.de)
 * and is protected under the German Copyright Act (paragraph 69a UrhG).
 *
 * All rights are reserved. Making copies, duplicating, modifying, using or distributing
 * this computer program in any form, without prior written consent of Neofonie Mobile GmbH, is prohibited.
 * Violation of copyright is punishable under the German Copyright Act (paragraph 106 UrhG).
 *
 * Removing this copyright statement is also a violation.
 */
package de.neofonie.udacity.capstone.hirefy.appwidget;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import de.neofonie.udacity.capstone.hirefy.BuildConfig;
import de.neofonie.udacity.capstone.hirefy.R;

/**
 * Created by marcinbak on 22/01/2017.
 */
public class HirefyWidgetRemoteViewsService extends RemoteViewsService {

  private static final long ONE_MIN  = 1000 * 60;
  private static final long ONE_HOUR = 1000 * 60 * 60;
  private static final long ONE_DAY  = 1000 * 60 * 60 * 24;
  private static final long ONE_WEEK = 1000 * 60 * 60 * 24 * 7;

  private static final String[] EVENTS_COLUMNS = {
      BaseColumns._ID,
      CalendarContract.Events.DTSTART,
      CalendarContract.Events.TITLE
  };

  private static final int INDEX_ID    = 0;
  private static final int INDEX_START = 1;
  private static final int INDEX_TITLE = 2;

  EventsObserverService mService;
  boolean mBound = false;

  @Override
  public void onCreate() {
    super.onCreate();
    Intent intent = new Intent(this, EventsObserverService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mBound) {
      unbindService(mConnection);
      mBound = false;
    }
  }

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new RemoteViewsFactory() {
      private Cursor data = null;

      @Override
      public void onCreate() {
      }

      @Override
      public void onDataSetChanged() {
        if (data != null) {
          data.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
        } else {
          final String oneWeek = String.valueOf(System.currentTimeMillis() + ONE_WEEK);

          data = getContentResolver().query(CalendarContract.Events.CONTENT_URI, EVENTS_COLUMNS,
              CalendarContract.Events.CUSTOM_APP_PACKAGE + " = ? AND " + CalendarContract.Events.DTSTART + " < ?",
              new String[]{BuildConfig.APPLICATION_ID, oneWeek}, CalendarContract.Events.DTSTART + " ASC " + " LIMIT 7");
        }
        Binder.restoreCallingIdentity(identityToken);
      }

      @Override
      public void onDestroy() {
        if (data != null) {
          data.close();
          data = null;
        }
      }

      @Override
      public int getCount() {
        return data == null ? 0 : data.getCount();
      }

      @Override
      public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
          return null;
        }
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
        String title = data.getString(INDEX_TITLE);
        long startDate = data.getLong(INDEX_START);
        long timeLeft = startDate - System.currentTimeMillis();

        String leftStr;
        long daysCount = timeLeft / ONE_DAY;
        if (daysCount == 0) {
          long hours = timeLeft / ONE_HOUR;
          if (hours == 0) {
            long minutes = timeLeft / ONE_MIN;
            if (minutes == 0) {
              leftStr = "NOW";
            } else {
              leftStr = "+" + minutes + "M";
            }
          } else {
            leftStr = "+" + hours + "H";
          }
        } else {
          leftStr = "+" + daysCount + "D";
        }

        views.setTextViewText(R.id.event_title_tv, title);
        views.setTextViewText(R.id.time_left_tv, leftStr);

//        final Intent fillInIntent = new Intent();
//        fillInIntent.putExtra(STOCK_CODE_EXTRA, symbol);
//        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
        return views;
      }

      @Override
      public RemoteViews getLoadingView() {
        return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
      }

      @Override
      public int getViewTypeCount() {
        return 1;
      }

      @Override
      public long getItemId(int position) {
        if (data.moveToPosition(position)) {
          return data.getLong(INDEX_ID);
        }
        return position;
      }

      @Override
      public boolean hasStableIds() {
        return true;
      }
    };
  }

  private ServiceConnection mConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
      // We've bound to LocalService, cast the IBinder and get LocalService instance
      Log.i("Marcin", "Service connected");
      EventsObserverService.LocalBinder binder = (EventsObserverService.LocalBinder) service;
      mService = binder.getService();
      mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      Log.i("Marcin", "Service disconnected");
      mBound = false;
    }
  };

}
