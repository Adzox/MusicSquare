package project.tddd80.keval992.liu.ida.se.navigationbase.main;


import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.locations.GetAddressFromStringTask;

/**
 * A simple {@link Fragment} subclass for displaying a map with a marker placed down.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private String address;
    private String title;

    public MapFragment() {
    }

    public static final MapFragment newInstance(String address, String title) {
        MapFragment mapFragment = new MapFragment();
        mapFragment.address = address;
        mapFragment.title = title;
        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_layout, container);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        new GetAddressFromStringTask(getActivity(), address) {
            @Override
            protected void whenComplete(List<Address> addresses) {
                double lat = addresses.get(0).getLatitude();
                double lon = addresses.get(0).getLongitude();
                LatLng markerPosition = new LatLng(lat, lon);
                googleMap.addMarker(new MarkerOptions().position(markerPosition).title(title));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerPosition));
            }
        };
    }
}
