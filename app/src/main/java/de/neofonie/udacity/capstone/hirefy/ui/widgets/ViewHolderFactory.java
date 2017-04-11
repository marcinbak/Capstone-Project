package de.neofonie.udacity.capstone.hirefy.ui.widgets;

import android.view.ViewGroup;

/**
 * Created by marcinbak on 10/04/2017.
 */
public abstract class ViewHolderFactory<T> {

  private final Class<T> dataClass;

  public ViewHolderFactory(Class<T> dataClass) {
    this.dataClass = dataClass;
  }

  public abstract ViewHolder<T> build(ViewGroup parent);

  public Class<T> getDataClass() {
    return dataClass;
  }
}
