package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.modules.candidates.Comment;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

/**
 * Created by marcinbak on 11.04.17.
 */
public class CommentViewHolder extends ViewHolder<Comment> {

  public static class Factory extends ViewHolderFactory<Comment> {

    public Factory() {
      super(Comment.class);
    }

    @Override
    public ViewHolder<Comment> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_comment, parent, false);
      return new CommentViewHolder(view);
    }
  }

  @BindView(R.id.author_time_tv) TextView mAuthorTimeTv;
  @BindView(R.id.comment_tv)     TextView mCommentTv;

  private CommentViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(Comment model) {
    mAuthorTimeTv.setText(model.getAuthor() + ", " + model.getDate());
    mCommentTv.setText(model.getText());
  }
}