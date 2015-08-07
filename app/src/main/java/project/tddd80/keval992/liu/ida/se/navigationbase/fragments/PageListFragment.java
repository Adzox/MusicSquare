package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.adapters.PageRecyclerViewAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.ResultsReceiver;
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
        if (mode == MODE_MEMBERSHIPED) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    protected List<Page> getModels() {
        List<Page> pages = new LinkedList<>();
        ResultsReceiver.newSearch();
        switch (mode) {
            case MODE_FAVORITES:
                return fetchPages("favorites");
            case MODE_MEMBERSHIPED:
                return fetchPages("memberships");
            default:
                return pages;
        }
    }

    private List<Page> fetchPages(String type) {
        List<Page> pages = new ArrayList<>();
        new HttpRequestTask(type, getActivity()) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                try {
                    JSONParser.parseJSONObject(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(JSONFactory.createIdData());
        for (Serializable serializable : ResultsReceiver.getResults(Page.class)) {
            pages.add((Page) serializable);
        }
        return pages;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                getFragmentManager().beginTransaction()
                        .add(R.id.menu_content, new CreatePageFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
