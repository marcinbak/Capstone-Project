package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;
import de.neofonie.udacity.capstone.hirefy.utils.AndroidUtils;

/**
 * Created by marcinbak on 11.04.17.
 */
public class AddCommentViewHolder extends ViewHolder<AddComment> implements TextWatcher {

  private AddComment mModel;

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

  @BindView(R.id.edit_text) EditText mEditText;


  private AddCommentViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          send();
          handled = true;
        }
        return handled;
      }
    });

    mEditText.addTextChangedListener(this);
  }

  @Override
  public void bind(AddComment model) {
    mModel = model;
    mEditText.setText(mModel.getSavedComment());
  }

  @OnClick(R.id.send_btn)
  void send() {
    mModel.clear();
    String comment = mEditText.getText().toString();
    Context c = getContext();
    if (c instanceof CommentSender) {
      ((CommentSender) c).sendComment(mModel.getCandidate().getUuid(), comment);
    }
    mEditText.setText("");
    AndroidUtils.hideKeyboardFrom(getContext(), itemView);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    String newText = s.toString();

    Context c = getContext();
    if (c instanceof CommentSender) {
      ((CommentSender) c).updateComment(newText);
    }
  }

}