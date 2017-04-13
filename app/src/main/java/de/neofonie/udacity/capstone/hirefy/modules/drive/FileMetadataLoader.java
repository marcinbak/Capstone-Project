package de.neofonie.udacity.capstone.hirefy.modules.drive;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

/**
 * Created by marcinbak on 13.04.17.
 */
public class FileMetadataLoader extends AsyncTaskLoader<Metadata> {

  private final static String TAG = "FileMetadataLoader";

  private final String          driveIdStr;
  private final GoogleApiClient googleClient;

  public FileMetadataLoader(Context context, String driveIdStr, GoogleApiClient googleClient) {
    super(context);
    this.driveIdStr = driveIdStr;
    this.googleClient = googleClient;
  }

  @Override
  public Metadata loadInBackground() {
    DriveId driveId = DriveId.decodeFromString(driveIdStr);
    DriveResource.MetadataResult result = driveId.asDriveFile().getMetadata(googleClient).await();

    if (result.getStatus().isSuccess()) {
      return result.getMetadata();
    } else {
      Log.w(TAG, "Failed to get metadata: " + result.getStatus().getStatusMessage());
      return null;
    }
  }

  @Override
  protected void onStartLoading() {
    forceLoad();
  }

}
