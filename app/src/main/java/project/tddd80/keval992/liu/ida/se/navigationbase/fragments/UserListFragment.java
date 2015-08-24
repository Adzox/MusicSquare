package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.UserRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.MessageRoom;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.User;

/**
 * Created by kevin on 2015-08-20.
 */
public class UserListFragment extends ModelListFragment {

    public static final int MODE_PAGEMEMBERS = 0;
    public static final int MODE_MESSAGEROOM = 1;
    private boolean enableAdd;
    private int mode = 0;
    private List<User> users;

    public UserListFragment() {
    }

    private static UserListFragment newInstance(int mode) {
        UserListFragment userListFragment = new UserListFragment();
        userListFragment.setModelRecyclerViewAdapterClass(UserRecyclerViewAdapter.class);
        userListFragment.mode = mode;
        return userListFragment;
    }

    public static UserListFragment newInstance(Page page) {
        UserListFragment userListFragment = newInstance(MODE_PAGEMEMBERS);
        userListFragment.users = page.getMembers();
        userListFragment.enableAdd = page.isUserMember();
        return userListFragment;
    }

    public static UserListFragment newInstance(MessageRoom messageRoom) {
        UserListFragment userListFragment = newInstance(MODE_MESSAGEROOM);
        userListFragment.users = messageRoom.getMembers();
        userListFragment.enableAdd = true;
        return userListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableAdd) setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        setItems(users);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.menu.add_button) == null) {
            inflater.inflate(R.menu.add_button, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                Fragment fragment = null;
                if (mode == MODE_PAGEMEMBERS) {
                    fragment = null;
                } else if (mode == MODE_MESSAGEROOM) {
                    fragment = null;
                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, fragment)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void itemClicked(View view, int position) {
        // GO TO USERS PAGE ON CLICK.
    }
}
