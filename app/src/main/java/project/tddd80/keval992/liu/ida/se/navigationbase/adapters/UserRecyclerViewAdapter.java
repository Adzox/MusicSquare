package project.tddd80.keval992.liu.ida.se.navigationbase.adapters;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.models.BaseUser;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.User;

/**
 * Adapter for displaying users in a card-layout.
 */
public class UserRecyclerViewAdapter extends ModelRecyclerViewAdapter<User> {

    public UserRecyclerViewAdapter(List<User> pages, FragmentActivity fragmentActivity) {
        super(pages, fragmentActivity);
    }

    @Override
    public void onBindViewHolder(ModelCardViewHolder holder, int position) {
        User user = getModels().get(position);
        BaseUser baseUser = user.getBaseUser();
        user.setProfileImage(holder.image);
        holder.title.setText(baseUser.getName());
        holder.subtitle.setText("");
        holder.content.setText("");
        holder.extra.setText(user.getLocation());
    }
}
