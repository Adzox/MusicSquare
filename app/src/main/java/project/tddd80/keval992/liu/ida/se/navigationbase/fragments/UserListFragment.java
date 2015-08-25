package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

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
    private Page page;
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
        userListFragment.page = page;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if ((page != null && page.isUserMember()) || mode == MODE_MESSAGEROOM) {
            FrameLayout bottomContainer = (FrameLayout) view.findViewById(R.id.bottomContainer);
            initBottomContainer(bottomContainer);
        }
        return view;
    }

    private void initBottomContainer(FrameLayout frameLayout) {
        Button button = new Button(getActivity());
        button.setText("Add member...");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        Fragment fragment = null;
        if (mode == MODE_PAGEMEMBERS && page.isUserMember()) {
            fragment = null;
        } else if (mode == MODE_MESSAGEROOM) {
            fragment = null;
        }
        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.menu_content, fragment)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setItems(users);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        // MAYBE IMPLEMENT REFRESH FETCHING FROM SERVER.
    }
}
