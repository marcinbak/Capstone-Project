package de.neofonie.udacity.capstone.hirefy.utils;

import de.neofonie.udacity.capstone.hirefy.dagger.scope.ActivityScope;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

import javax.inject.Inject;

/**
 * Created by marcinbak on 20.04.17.
 */
@ActivityScope
public class EventBus {

  private PublishSubject<BusEvent> mBus;

  @Inject
  public EventBus() {
    mBus = PublishSubject.create();
  }

  public void send(BusEvent event) {
    mBus.onNext(event);
  }

  public <T extends BusEvent> Observable<T> observe(final Class<T> clazz) {
    return mBus
        .filter(new Predicate<BusEvent>() {
          @Override
          public boolean test(@NonNull BusEvent event) throws Exception {
            return event.getClass() == clazz;
          }
        })
        .map(new Function<BusEvent, T>() {
          @Override
          public T apply(@NonNull BusEvent event) throws Exception {
            return (T) event;
          }
        });
  }

}
