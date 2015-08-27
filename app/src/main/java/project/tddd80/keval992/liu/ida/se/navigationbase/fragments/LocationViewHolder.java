package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;

import android.app.Activity;
import android.location.Address;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.AddressArrayAdapter;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.locations.GetAddressFromStringTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.locations.GetCurrentAddressTask;

/**
 * ViewHolder for containing the AutoCompleteTextView, its dependencies and functions as well as the
 * fetch current location button.
 * <p/>
 * Created by kevin on 2015-08-25.
 */
public class LocationViewHolder {

    private final AutoCompleteTextView location;
    private final AddressArrayAdapter adapter;

    public LocationViewHolder(View view, final Activity activity) {
        adapter = new AddressArrayAdapter(activity, R.layout.text_layout, R.id.textView, new ArrayList<String>());
        location = (AutoCompleteTextView) view.findViewById(R.id.location);
        ((Button) view.findViewById(R.id.currentLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentLocation(activity);
            }
        });
        location.setAdapter(adapter);
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fetchNewSuggestions(activity, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void fetchNewSuggestions(Activity activity, String query) {
        new GetAddressFromStringTask(activity, query) {
            @Override
            protected void whenComplete(List<Address> addresses) {
                adapter.setItems(addresses);
            }
        };
    }

    private void setCurrentLocation(Activity activity) {
        new GetCurrentAddressTask(activity) {
            @Override
            protected void whenComplete(List<Address> addresses) {
                String fullAddress = "";
                Address address = addresses.get(0);
                for (int n = 0; n < address.getMaxAddressLineIndex(); n++) {
                    fullAddress += address.getAddressLine(n) + " ";
                }
                location.setText(fullAddress);
            }
        };
    }

    public final String getLocation() {
        return location.getText().toString();
    }
}
