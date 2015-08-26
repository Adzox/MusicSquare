package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import android.content.Context;
import android.location.Address;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Custom ArrayAdapter for populating a location AutoCompleteTextView with suggestions.
 * <p/>
 * Created by kevin on 2015-08-25.
 */
public class AddressArrayAdapter extends ArrayAdapter<String> {

    public AddressArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public final void setItems(List<Address> addresses) {
        clear();
        for (Address address : addresses) {
            String fullAddress = "";
            for (int n = 0; n < address.getMaxAddressLineIndex(); n++) {
                fullAddress += address.getAddressLine(n);
            }
            add(fullAddress);
        }
        notifyDataSetChanged();
    }
}
