package de.neofonie.udacity.capstone.hirefy.ui.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by marcinbak on 11.04.17.
 */

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

  protected Context getContext() {
    return itemView.getContext();
  }

  public ViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void bind(T model);


}
