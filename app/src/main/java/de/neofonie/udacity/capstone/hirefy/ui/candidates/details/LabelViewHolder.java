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

public class LabelViewHolder extends ViewHolder<String> {

  public static class Factory extends ViewHolderFactory<String> {

    public Factory() {
      super(String.class);
    }

    @Override
    public ViewHolder<String> build(ViewGroup parent) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_label, parent, false);
      return new LabelViewHolder(view);
    }
  }

  @BindView(R.id.label_tv) TextView mLabelText;

  private LabelViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(String model) {
    mLabelText.setText(model);
  }
}
