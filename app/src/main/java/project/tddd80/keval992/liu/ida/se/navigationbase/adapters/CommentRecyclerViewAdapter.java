package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.BaseUser;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Comment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Post;

/**
 * Adapter for displaying comments in a card-layout.
 */
public class CommentRecyclerViewAdapter extends ModelRecyclerViewAdapter<Comment> {

    public CommentRecyclerViewAdapter(List<Comment> comments) {
        super(comments);
    }

    @Override
    public void onBindViewHolder(ModelCardViewHolder holder, int position) {
        Comment comment = getModels().get(position);
        BaseUser baseUser = comment.getSender().getBaseUser();
        Post post = comment.getPost();
        Page page = comment.getPost().getOwner();
        holder.title.setText(baseUser.getName());
        holder.subtitle.setText("in " + page.getName());
        holder.content.setText(comment.getMessage());
        holder.extra.setText(comment.getDateSent());
    }
}
