package de.neofonie.udacity.capstone.hirefy.ui.candidates.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.udacity.capstone.hirefy.R;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolder;
import de.neofonie.udacity.capstone.hirefy.ui.widgets.ViewHolderFactory;

/**
 * Created by marcinbak on 11.04.17.
 */
public class SimpleTextViewHolder extends ViewHolder<SimpleText> {

  public static class Factory extends ViewHolderFactory<SimpleText> {

    public Factory() {
      super(SimpleText.class);
    }

    @Override
    public ViewHolder<SimpleText> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_simpletext, parent, false);
      return new SimpleTextViewHolder(view);
    }
  }

  @BindView(R.id.label_tv) TextView mLabelText;

  private SimpleTextViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(SimpleText model) {
    mLabelText.setText(model.getText());
  }
}
