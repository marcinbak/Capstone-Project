package de.neofonie.udacity.capstone.hirefy.appwidget;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.Log;

import static de.neofonie.udacity.capstone.hirefy.appwidget.HirefyWidget.ACTION_DATA_UPDATED;

/**
 * Created by marcinbak on 24.04.17.
 */
public class EventsObserverService extends Service {

  private final IBinder mBinder = new LocalBinder();

  private EventsObserver mContentObserver;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Log.i("Marcin", "Bind to service");
    if (mContentObserver == null) {
      mContentObserver = new EventsObserver(new Handler());
      getContentResolver().registerContentObserver(CalendarContract.Events.CONTENT_URI, true, mContentObserver);
    }
    return mBinder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Log.i("Marcin", "Unbind from service");
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    getContentResolver().unregisterContentObserver(mContentObserver);
  }

  private class EventsObserver extends ContentObserver {
    public EventsObserver(Handler handler) {
      super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
      this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
      Log.i("Marcin", "change arrived " + uri);
      Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
      getApplicationContext().sendBroadcast(dataUpdatedIntent);
    }
  }

  public class LocalBinder extends Binder {
    EventsObserverService getService() {
      // Return this instance of LocalService so clients can call public methods
      return EventsObserverService.this;
    }
  }


}
