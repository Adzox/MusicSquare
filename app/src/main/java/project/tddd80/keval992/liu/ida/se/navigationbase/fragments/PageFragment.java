package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;

/**
 * A simple {@link Fragment} subclass for displaying a pages information.
 */
public class PageFragment extends Fragment implements View.OnClickListener {

    private Page page;
    private ImageView profileImage;
    private TextView name, type, location, information;

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
        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        page.setProfileImage(profileImage);
        setPageCard(view.findViewById(R.id.page_card), page);
        setInfoCard(view.findViewById(R.id.info_card), page);
        return view;
    }

    private void setPageCard(View pageView, Page page) {
        View linView = pageView.findViewById(R.id.card_content);
        name = (TextView) linView.findViewById(R.id.title);
        name.setText(page.getName());
        type = (TextView) linView.findViewById(R.id.type);
        type.setText(page.getType());
        location = (TextView) linView.findViewById(R.id.location);
        location.setText(page.getLocation());
        location.setOnClickListener(this);
    }

    private void setInfoCard(View infoView, Page page) {
        information = (TextView) infoView.findViewById(R.id.information);
        information.setText(page.getInformation());
    }

    @Override
    public void onClick(View v) {
        // Go to Google Maps and show location on map with a marker.
    }
}
