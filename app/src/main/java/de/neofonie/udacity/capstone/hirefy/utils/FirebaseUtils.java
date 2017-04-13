package de.neofonie.udacity.capstone.hirefy.utils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import io.reactivex.*;
import io.reactivex.annotations.NonNull;

/**
 * Created by marcinbak on 12.04.17.
 */

public class FirebaseUtils {

  public static Completable updateToCompletable(final Task<Void> task) {
    return Completable.create(new CompletableOnSubscribe() {
      @Override
      public void subscribe(@NonNull final CompletableEmitter emitter) throws Exception {
        task
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                if (!emitter.isDisposed()) emitter.onComplete();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@android.support.annotation.NonNull Exception e) {
                if (!emitter.isDisposed()) emitter.onError(e);
              }
            });
      }
    });
  }

  public static <T> Single<T> singleFrom(final DatabaseReference ref, final Class<T> clazz) {
    return Single.create(new SingleOnSubscribe<T>() {
      @Override
      public void subscribe(@NonNull final SingleEmitter<T> emitter) throws Exception {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            emitter.onSuccess(dataSnapshot.getValue(clazz));
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            emitter.onError(databaseError.toException());
          }
        });
      }
    });
  }

}
