package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.CardViewModel;

/**
 * Adapter for displaying CardViewModels in a card-layout.
 */
public class CardViewModelRecyclerViewAdapter extends ModelRecyclerViewAdapter<CardViewModel> {

    public CardViewModelRecyclerViewAdapter(List<CardViewModel> cardViewModels, FragmentActivity fragmentActivity) {
        super(cardViewModels, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(ModelCardViewHolder holder, int position) {
        holder.setCardViewModel(getModels().get(position));
    }
}
