package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PageFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PostListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.UserListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.sliders.SlidingFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;

/**
 * Adapter for displaying pages in a card-layout.
 */
public class PageRecyclerViewAdapter extends ModelRecyclerViewAdapter<Page> {

    public PageRecyclerViewAdapter(List<Page> pages, FragmentActivity fragmentActivity) {
        super(pages, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(final ModelCardViewHolder holder, int position) {
        Page page = getModels().get(position);
        page.setProfileImage(holder.image);
        holder.title.setText(page.getName());
        holder.subtitle.setText(page.getType());
        holder.content.setText("");
        holder.extra.setText(page.getLocation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPage(holder);
            }
        });
    }

    private void toPage(final ModelCardViewHolder holder) {
        final Page page = getModels().get(holder.getLayoutPosition());
        SlidingFragment slidingFragment = new SlidingFragment() {
            @Override
            protected void initAdapter() {
                addFragment("Information", PageFragment.newInstance(page));
                addFragment("Members", UserListFragment.newInstance(page));
                addFragment("Posts", PostListFragment.newInstance(page));
            }
        };
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_content, slidingFragment)
                .addToBackStack(null)
                .commit();
    }
}
