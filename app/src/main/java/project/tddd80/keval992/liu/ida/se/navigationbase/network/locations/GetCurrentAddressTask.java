package project.tddd80.keval992.liu.ida.se.navigationbase.network.locations;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kevin on 2015-08-25.
 */
public abstract class GetCurrentAddressTask extends GeocodingTask {

    public GetCurrentAddressTask(Activity activity) {
        super(activity);
    }

    @Override
    protected List<Address> whenStarted() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            try {
                return geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
