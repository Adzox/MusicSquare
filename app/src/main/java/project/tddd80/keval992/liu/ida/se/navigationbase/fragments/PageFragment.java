package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;

/**
 * A simple {@link Fragment} subclass for displaying a pages members and information.
 */
public class PageFragment extends Fragment {

    private Page page;

    public PageFragment() {
    }

    public static final PageFragment newInstance(Page page) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.page = page;
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }


}
