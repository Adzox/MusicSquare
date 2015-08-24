package project.tddd80.keval992.liu.ida.se.navigationbase.main;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.LoginFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PageListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.fragments.PostListFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.sliders.SlidingFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int mode = 0;

    public MenuFragment() {
    }

    public static final MenuFragment normalMode() {
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.mode = 0;
        return menuFragment;
    }

    public static final MenuFragment basicMode() {
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.mode = 1;
        return menuFragment;
    }

    public static final MenuFragment advancedMode() {
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.mode = 2;
        return menuFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (mode == 2) {
            view = inflater.inflate(R.layout.fragment_menu_advanced, container, false);
        } else if (mode == 1) {
            view = inflater.inflate(R.layout.fragment_menu_basic, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_menu, container, false);
        }
        drawerLayout = (DrawerLayout) view.findViewById(R.id.menu_drawer_layout);
        Toolbar toolbar = ((MainActivity) getActivity()).getToolbar();
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.menu_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setShowNavigationButton();
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigator_login:
                LoginFragment loginFragment = new LoginFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, loginFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.navigator_news:
                SlidingFragment slidingFragment = new SlidingFragment() {
                    @Override
                    protected void initAdapter() {
                        addFragment("Global News", PostListFragment.newInstanceGlobal());
                        if (LoginInfo.hasLoggedIn()) {
                            addFragment("News from favorites", PostListFragment.newInstanceFavorites());
                        }
                    }
                };
                getFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, slidingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            /*
            case R.id.navigator_profile:
                PostListFragment postListFragment = new PostListFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.menu_content, postListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            */
            case R.id.navigator_favorites:
                PageListFragment pageListFragment = PageListFragment.newInstance(PageListFragment.MODE_FAVORITES);
                getFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, pageListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.navigator_pages:
                Fragment fragment = PageListFragment.newInstance(PageListFragment.MODE_MEMBERSHIPED);
                getFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, fragment)
                        .addToBackStack(null)
                        .commit();
                break;
            /*
            case R.id.navigator_messages:
                PostListFragment postListFragment = new PostListFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.menu_content, postListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            */
            case R.id.navigator_logout:
                LoginInfo.logout();
                MenuFragment menuFragment = MenuFragment.normalMode();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, menuFragment)
                        .commit();
                break;
            /*
            case R.id.navigator_about:
                Fragment fragment = new PostListFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.menu_content, postListFragment)
                        .addToBackStack(null)
                        .commit();
                break;
             */

        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
