package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.util.Log;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.BaseUser;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.User;

/**
 * Adapter for displaying posts in a card-layout.
 */
public class PostRecyclerViewAdapter extends ModelRecyclerViewAdapter<Post> implements CardViewHolder.OnItemClickListener {

    public PostRecyclerViewAdapter(List<Post> posts) {
        super(posts);
    }

    @Override
    public void onBindViewHolder(ModelCardViewHolder holder, int position) {
        Post post = getModels().get(position);
        BaseUser baseUser = BaseUser.getReferenceTo(post.getUserId());
        User user = User.getReferenceTo(post.getUserId());
        Page page = Page.getReferenceTo(post.getOwnerId());
        user.setProfileImage(holder.image);
        holder.title.setText(baseUser.getName());
        holder.subtitle.setText("in " + page.getName());
        holder.content.setText(post.getMessage());
        holder.extra.setText(post.getDateSent());
        holder.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Post post = getModels().get(position);
        Log.i("PostRecyclerViewAdapter", post.getMessage());
        // MAIN AREA: DISPLAY POST ONLY
        // LIKE BUTTON: LIKE POST
        // USERNAME: DISPLAY USER ONLY
        // PAGE NAME: DISPLAY PAGE ONLY
    }
}
