package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MapFragment mapFragment = MapFragment.newInstance(address, title);
        builder.setView(R.layout.map_dialog_layout)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setTitle(title);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.map_dialog_container, mapFragment)
                .commit();
        return builder.create();
    }
}
