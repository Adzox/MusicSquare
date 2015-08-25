package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.CommentListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * Adapter for displaying posts in a card-layout.
 */
public class PostRecyclerViewAdapter extends ModelRecyclerViewAdapter<Post> {

    public PostRecyclerViewAdapter(List<Post> posts, FragmentActivity fragmentActivity) {
        super(posts, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(final ModelCardViewHolder holder, int position) {
        Post post = getModels().get(position);
        post.getSender().setProfileImage(holder.image);
        holder.title.setText(post.getSender().getBaseUser().getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("PRESSED THE TITLE!");
            }
        });
        holder.subtitle.setText("in " + post.getOwner().getName());
        holder.subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("PRESSED THE SUBTITLE!");
            }
        });
        holder.content.setText(post.getMessage());
        holder.extra.setText(post.getDateSent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = getModels().get(holder.getLayoutPosition());
                System.out.println("CHANGE FRAGMENT...");
                Fragment fragment = CommentListFragment.newInstance(post);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, fragment)
                        .commit();
            }
        });
    }
}
