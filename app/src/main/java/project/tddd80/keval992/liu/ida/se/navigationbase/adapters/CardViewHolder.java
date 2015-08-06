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
public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image;
    public TextView title;
    public TextView subtitle;
    public TextView content;
    public TextView extra;
    private OnItemClickListener listener;

    public CardViewHolder(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.card_image);
        this.title = (TextView) itemView.findViewById(R.id.card_title);
        this.subtitle = (TextView) itemView.findViewById(R.id.card_subtitle);
        this.content = (TextView) itemView.findViewById(R.id.card_content);
        this.extra = (TextView) itemView.findViewById(R.id.card_extra);
        itemView.setOnClickListener(this);
    }

    public void setCardViewModel(CardViewModel cardViewModel) {
        cardViewModel.setImageView(image);
        title.setText(cardViewModel.title);
        subtitle.setText(cardViewModel.subtitle);
        content.setText(cardViewModel.content);
        extra.setText(cardViewModel.extra);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onItemClick(v, getLayoutPosition());
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
}
