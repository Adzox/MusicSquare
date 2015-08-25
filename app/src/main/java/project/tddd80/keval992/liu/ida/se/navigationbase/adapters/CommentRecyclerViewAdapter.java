package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PageFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PostListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.UserListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.sliders.SlidingFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.BaseUser;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Comment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * Adapter for displaying comments in a card-layout.
 */
public class CommentRecyclerViewAdapter extends ModelRecyclerViewAdapter<Comment> {

    public CommentRecyclerViewAdapter(List<Comment> comments, FragmentActivity fragmentActivity) {
        super(comments, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(final ModelCardViewHolder holder, int position) {
        Comment comment = getModels().get(position);
        BaseUser baseUser = comment.getSender().getBaseUser();
        Post post = comment.getPost();
        Page page = comment.getPost().getOwner();
        holder.title.setText(baseUser.getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO USER PROFILE
            }
        });
        holder.subtitle.setText("in " + page.getName());
        holder.subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPage(holder);
            }
        });
        holder.content.setText(comment.getMessage());
        holder.extra.setText(comment.getDateSent());
    }

    private void toPage(final ModelCardViewHolder holder) {
        final Comment comment = getModels().get(holder.getLayoutPosition());
        SlidingFragment slidingFragment = new SlidingFragment() {
            @Override
            protected void initAdapter() {
                addFragment("Information", PageFragment.newInstance(comment.getPost().getOwner()));
                addFragment("Members", UserListFragment.newInstance(comment.getPost().getOwner()));
                addFragment("Posts", PostListFragment.newInstance(comment.getPost().getOwner()));
            }
        };
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_content, slidingFragment)
                .addToBackStack(null)
                .commit();
    }
}
