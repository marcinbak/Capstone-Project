package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

/**
 * Created by marcinbak on 11.04.17.
 */
public class AddCommentViewHolder extends ViewHolder<AddComment> {

  public static class Factory extends ViewHolderFactory<AddComment> {

    public Factory() {
      super(AddComment.class);
    }

    @Override
    public ViewHolder<AddComment> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_add_comment, parent, false);
      return new AddCommentViewHolder(view);
    }
  }


  private AddCommentViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(AddComment model) {
  }
}