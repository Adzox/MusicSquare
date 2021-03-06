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
import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.MapDialogFragment;
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
        location.setOnClickListener(this);
        location.setTextColor(getResources().getColor(R.color.linkColour));
        favorite = (Button) linView.findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFavoriteRequest();
            }
        });
        initFavoriteButton();
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

    private void initFavoriteButton() {
        if (LoginInfo.hasLoggedIn() && !page.isUserMember()) {
            if (page.isHasUserFavorited()) {
                setFavoritedOn();
            } else {
                setFavoritedOff();
            }
        } else {
            disableFavoriteButton();
        }
    }

    private void toogleFavoriteButton() {
        if (page.isHasUserFavorited()) {
            setFavoritedOff();
        } else {
            setFavoritedOn();
        }
    }

    private void setFavoritedOff() {
        favorite.setText("Add page to favorites");
        favorite.setTextColor(getResources().getColor(R.color.normalColor));
        page.setHasUserFavorited(false);
    }

    private void setFavoritedOn() {
        favorite.setText("Remove page from favorites");
        favorite.setTextColor(getResources().getColor(R.color.toogleColor));
        page.setHasUserFavorited(true);
    }

    private void disableFavoriteButton() {
        favorite.setVisibility(View.GONE);
        favorite.setEnabled(false);
        favorite.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        MapDialogFragment mapDialogFragment = MapDialogFragment.newInstance(page.getLocation(), page.getName());
        mapDialogFragment.show(getFragmentManager(), MapDialogFragment.class.getSimpleName());
    }
}
