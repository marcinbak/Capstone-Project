package de.neofonie.udacity.capstone.hirefy.ui.widgets;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.*;

/**
 * Created by marcinbak on 10/04/2017.
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private       List<Object>                                 mData;
  private final Map<Class, Pair<ViewHolderFactory, Integer>> mViewHoldersMap;
  private final SparseArray<ViewHolderFactory>               mFactoriesArray;

  private CustomRecyclerAdapter(Map<Class, Pair<ViewHolderFactory, Integer>> viewHoldersMap, SparseArray<ViewHolderFactory> factoriesArray) {
    this.mViewHoldersMap = viewHoldersMap;
    this.mFactoriesArray = factoriesArray;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewHolderFactory factory = mFactoriesArray.get(viewType);
    if (factory == null) {
      throw new IllegalStateException("Factory not registered for type " + viewType);
    }
    return factory.build(parent);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(mData.get(position));
  }

  @Override
  public int getItemViewType(int position) {
    Class<?> clazz = mData.get(position).getClass();
    return mViewHoldersMap.get(clazz).second;
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  public void setData(List<Object> list) {
    mData = list;
    notifyDataSetChanged();
  }

  public static class Builder {

    Set<ViewHolderFactory> factories = new HashSet<>();

    public Builder registerFactory(ViewHolderFactory factory) {
      factories.add(factory);
      return this;
    }

    public CustomRecyclerAdapter build() {
      int size = factories.size();
      int counter = 0;

      Map<Class, Pair<ViewHolderFactory, Integer>> viewHoldersMap = new HashMap<>(size);
      SparseArray<ViewHolderFactory> factoriesArray = new SparseArray<>(size);


      for (ViewHolderFactory factory : factories) {
        viewHoldersMap.put(factory.getDataClass(), new Pair<>(factory, counter));
        factoriesArray.put(counter, factory);
        counter++;
      }

      return new CustomRecyclerAdapter(viewHoldersMap, factoriesArray);
    }

  }

}
