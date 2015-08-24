package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PageRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.sliders.SlidingFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * Created by kevin on 2015-08-03.
 */
public class PageListFragment extends ModelListFragment<Page> {

    public static final int MODE_FAVORITES = 0;
    public static final int MODE_MEMBERSHIPED = 1;
    private int mode = 0;

    public static PageListFragment newInstance(int mode) {
        PageListFragment pageListFragment = new PageListFragment();
        pageListFragment.setModelRecyclerViewAdapterClass(PageRecyclerViewAdapter.class);
        pageListFragment.mode = mode;
        return pageListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadModels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mode == MODE_MEMBERSHIPED) {
            FrameLayout bottomContainer = (FrameLayout) view.findViewById(R.id.bottomContainer);
            initBottomContainer(bottomContainer);
        }
        return view;
    }

    private void initBottomContainer(FrameLayout frameLayout) {
        Button button = new Button(getActivity());
        button.setText("Create new page");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.menu_content, new CreatePageFragment())
                        .commit();
            }
        });
        frameLayout.addView(button);
    }

    public final void loadModels() {
        ResultsReceiver.newSearch();
        switch (mode) {
            case MODE_FAVORITES:
                fetchPages("favorites");
                break;
            case MODE_MEMBERSHIPED:
                fetchPages("memberships");
                break;
        }
    }

    private void fetchPages(String type) {
        final List<Page> pages = new ArrayList<>();
        new HttpRequestTask(type, getActivity()) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                try {
                    JSONParser.parseJSONObject(jsonObject);
                    List<Serializable> serializables = ResultsReceiver.getResults(Page.class);
                    if (serializables != null && !serializables.isEmpty()) {
                        for (Serializable serializable : serializables) {
                            pages.add((Page) serializable);
                        }
                    }
                    setItems(pages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(JSONFactory.createIdData());
    }

    @Override
    protected void itemClicked(View view, int position) {
        final Page page = getItem(position);
        SlidingFragment slidingFragment = new SlidingFragment() {
            @Override
            protected void initAdapter() {
                addFragment("Information", PageFragment.newInstance(page));
                addFragment("Members", UserListFragment.newInstance(page));
                addFragment("Posts", PostListFragment.newInstance(page));
            }
        };
        getFragmentManager().beginTransaction()
                .replace(R.id.menu_content, slidingFragment)
                .commit();
    }
}
