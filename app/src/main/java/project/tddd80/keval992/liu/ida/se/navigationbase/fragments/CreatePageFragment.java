package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.locations.GetCurrentAddressTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePageFragment extends Fragment implements View.OnClickListener {

    private EditText name, information;
    private AutoCompleteTextView location;
    private Spinner type;

    public CreatePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_page, container, false);
        name = (EditText) view.findViewById(R.id.name);
        type = (Spinner) view.findViewById(R.id.type);
        location = (AutoCompleteTextView) view.findViewById(R.id.location);
        ((Button) view.findViewById(R.id.currentLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetCurrentAddressTask(getActivity()) {
                    @Override
                    protected void whenComplete(List<Address> addresses) {

                    }
                };
            }
        });
        information = (EditText) view.findViewById(R.id.information);
        view.findViewById(R.id.create_page).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_page) {
            new HttpRequestTask("new") {

                @Override
                protected void atPostExecute(JSONObject jsonObject) {
                    if (JSONParser.wasSuccessful(jsonObject)) {
                        Fragment fragment = PageListFragment.newInstance(PageListFragment.MODE_MEMBERSHIPED);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.menu_content, fragment)
                                .commit();
                    } else {
                        Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_SHORT);
                    }
                }
            }.execute(JSONFactory.createCreateNewPageData(name.getText().toString(),
                    type.getSelectedItem().toString(), location.getText().toString(),
                    information.getText().toString()));
        }
    }
}
