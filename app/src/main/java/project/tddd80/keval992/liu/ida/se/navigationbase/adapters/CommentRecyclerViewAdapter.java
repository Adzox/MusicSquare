package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
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
        String ending = "";
        if (baseUser.getId() == LoginInfo.getUserId()) {
            ending = " (me)";
        }
        holder.title.setText(baseUser.getName() + ending);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO USER PROFILE
            }
        });
        holder.subtitle.setText("");
        holder.content.setText(comment.getMessage());
        holder.extra.setText(comment.getDateSent());
    }
}
