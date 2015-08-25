package project.tddd80.keval992.liu.ida.se.navigationbase.network.locations;

import android.app.Activity;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Abstract asynchrous task for fetching locations and addresses.
 *
 * Created by kevin on 2015-08-25.
 */
public abstract class GeocodingTask implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    protected Activity activity;

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

            @Override
            protected void onCancelled() {
                super.onCancelled();
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
}
