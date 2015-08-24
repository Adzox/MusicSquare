package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.models.Page;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * A simple {@link Fragment} subclass for displaying a pages information.
 */
public class PageFragment extends Fragment implements View.OnClickListener {

    private Page page;
    private ImageView profileImage;
    private Button favorite;
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
        // FOR MAPS
        // location.setOnClickListener(this);
        // location.setTextColor(getResources().getColor(R.color.linkColour));
        favorite = (Button) linView.findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFavoriteRequest();
            }
        });
    }

    private void setInfoCard(View infoView, Page page) {
        information = (TextView) infoView.findViewById(R.id.information);
        information.setText(page.getInformation());
    }

    private void sendFavoriteRequest() {
        String routing = "";
        if (page.isHasUserFavorited()) {
            routing = "delete";
        } else {
            routing = "new";
        }
        new HttpRequestTask(routing) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                if (JSONParser.wasSuccessful(jsonObject)) {
                    toogleFavoriteButton();
                }
            }
        }.execute(JSONFactory.createFavoriteData(page.getId()));
    }

    private void toogleFavoriteButton() {
        if (!page.isUserMember()) {
            if (page.isHasUserFavorited()) {
                favorite.setText("Remove from favorites");
                favorite.setBackgroundColor(getResources().getColor(R.color.normalColor));
                page.setHasUserFavorited(false);
            } else {
                favorite.setText("Add to favorites");
                favorite.setBackgroundColor(getResources().getColor(R.color.toogleColor));
                page.setHasUserFavorited(true);
            }
        } else {
            favorite.setVisibility(View.GONE);
            favorite.setEnabled(false);
            favorite.setOnClickListener(null);
        }
    }

    @Override
    public void onClick(View v) {
        // Go to Google Maps and show location on map with a marker.
    }
}
