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
public abstract class GetAddressFromStringTask extends GeocodingTask {

    private final String address;

    public GetAddressFromStringTask(Activity activity, String address) {
        super(activity);
        this.address = address;
    }

    @Override
    protected List<Address> whenStarted() {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            return geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
