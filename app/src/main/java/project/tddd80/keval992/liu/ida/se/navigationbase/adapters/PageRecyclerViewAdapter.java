package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;

/**
 * Adapter for displaying pages in a card-layout.
 */
public class PageRecyclerViewAdapter extends ModelRecyclerViewAdapter<Page> {

    public PageRecyclerViewAdapter(List<Page> pages, FragmentActivity fragmentActivity) {
        super(pages, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(ModelCardViewHolder holder, int position) {
        Page page = getModels().get(position);
        page.setProfileImage(holder.image);
        holder.title.setText(page.getName());
        holder.subtitle.setText(page.getType());
        holder.content.setText("");
        holder.extra.setText(page.getLocation());
    }


}
