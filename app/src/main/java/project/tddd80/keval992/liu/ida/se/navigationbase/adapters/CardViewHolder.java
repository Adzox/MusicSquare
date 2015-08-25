package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.CardViewModel;

/**
 * Base for ViewHolder when using CardView and its layout.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public ImageView image;
    public TextView title;
    public TextView subtitle;
    public TextView content;
    public TextView extra;

    public CardViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.image = (ImageView) itemView.findViewById(R.id.card_image);
        this.title = (TextView) itemView.findViewById(R.id.card_title);
        this.subtitle = (TextView) itemView.findViewById(R.id.card_subtitle);
        this.content = (TextView) itemView.findViewById(R.id.card_content);
        this.extra = (TextView) itemView.findViewById(R.id.card_extra);
    }

    public void setCardViewModel(CardViewModel cardViewModel) {
        cardViewModel.setImageView(image);
        title.setText(cardViewModel.title);
        subtitle.setText(cardViewModel.subtitle);
        content.setText(cardViewModel.content);
        extra.setText(cardViewModel.extra);
    }
}
