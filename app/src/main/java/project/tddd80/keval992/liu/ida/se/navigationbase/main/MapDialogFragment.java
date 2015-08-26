package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;

/**
 * Simple dialog fragment containing the MapFragment.
 * <p/>
 * Created by kevin on 2015-08-25.
 */
public class MapDialogFragment extends DialogFragment {

    private String address = "", title = "";

    public static final MapDialogFragment newInstance(String address, String title) {
        MapDialogFragment mapDialogFragment = new MapDialogFragment();
        mapDialogFragment.address = address;
        mapDialogFragment.title = title;
        return mapDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_dialog_layout, container);
        ((Button) v.findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapDialogFragment.this.dismiss();
            }
        });
        MapFragment mapFragment = MapFragment.newInstance(address, title);
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.map_dialog_container, mapFragment)
                .commit();
        return v;
    }
}
