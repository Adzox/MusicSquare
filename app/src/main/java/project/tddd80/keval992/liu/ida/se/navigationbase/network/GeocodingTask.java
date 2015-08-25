package project.tddd80.keval992.liu.ida.se.navigationbase.network;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kevin on 2015-08-25.
 */
public abstract class GeocodingTask implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Activity activity;

    public GeocodingTask(Activity activity) {
        this.activity = activity;
        buildGoogleApiClient(activity);
        mGoogleApiClient.connect();
    }

    private synchronized void buildGoogleApiClient(Activity activity) {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        new AsyncTask<Void, Void, List<Address>>() {

            @Override
            protected List<Address> doInBackground(Void... params) {
                return whenStarted();
            }

            @Override
            protected void onPostExecute(List<Address> addresses) {
                super.onPostExecute(addresses);
                whenComplete(addresses);
                if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
            }
        }.execute();
    }

    protected abstract List<Address> whenStarted();

    protected abstract void whenComplete(List<Address> addresses);

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

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
}
