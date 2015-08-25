package project.tddd80.keval992.liu.ida.se.navigationbase.network.locations;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kevin on 2015-08-25.
 */
public abstract class SearchForAddressesTask extends GeocodingTask {

    private final String query;
    private final int maxNumberOfResults;

    public SearchForAddressesTask(Activity activity, String query, int maxNumberOfResults) {
        super(activity);
        this.query = query;
        this.maxNumberOfResults = maxNumberOfResults;
    }

    @Override
    protected List<Address> whenStarted() {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            return geocoder.getFromLocationName(query, maxNumberOfResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
