package de.neofonie.udacity.capstone.hirefy.ui.widgets;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by marcinbak on 06/04/2017.
 */
public class HidingFabBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {

  private final static String TAG = HidingFabBehaviour.class.getSimpleName();

  public HidingFabBehaviour(Context context, AttributeSet attrs) {
    super();
  }

  @Override
  public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                     final View directTargetChild, final View target, final int nestedScrollAxes) {
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override
  public void onNestedScroll(final CoordinatorLayout coordinatorLayout,
                             final FloatingActionButton child,
                             final View target, final int dxConsumed, final int dyConsumed,
                             final int dxUnconsumed, final int dyUnconsumed) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
      child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
        @Override
        public void onHidden(FloatingActionButton fab) {
          fab.setVisibility(View.INVISIBLE);
        }
      });
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
      child.show();
    }
  }
}