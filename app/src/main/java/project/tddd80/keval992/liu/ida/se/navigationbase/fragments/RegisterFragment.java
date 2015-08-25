package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText username, name, password, repeated, information;
    private LocationViewHolder locationViewHolder;
    private boolean advanced = false;

    public RegisterFragment() {
    }

    public static final RegisterFragment newInstance(boolean advanced) {
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.advanced = advanced;
        return registerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (advanced) {
            view = inflater.inflate(R.layout.fragment_register_advanced, container, false);
            locationViewHolder = new LocationViewHolder(view, getActivity());
            information = (EditText) view.findViewById(R.id.information);
        } else {
            view = inflater.inflate(R.layout.fragment_register, container, false);
        }
        username = (EditText) view.findViewById(R.id.username);
        name = (EditText) view.findViewById(R.id.name);
        password = (EditText) view.findViewById(R.id.password);
        repeated = (EditText) view.findViewById(R.id.repeated);
        ((Button) view.findViewById(R.id.register)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String uname = username.getText().toString();
        String vname = name.getText().toString();
        String pass = password.getText().toString();
        String rep = repeated.getText().toString();
        if (!pass.equals(rep)) {
            Toast.makeText(getActivity(), "Repeated password is not the same as the entered password!", Toast.LENGTH_LONG).show();
            password.setText("");
            repeated.setText("");
        } else {
            if (advanced) {
                String loc = locationViewHolder.getLocation();
                String info = information.getText().toString();
                new HttpRequestTask("new-advanced") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        if (JSONParser.wasSuccessful(jsonObject)) {
                            whenFinishedRegistering();
                        } else {
                            Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(JSONFactory.createRegisterData(uname, vname, pass, loc, info));
            } else {
                new HttpRequestTask("new") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        if (JSONParser.wasSuccessful(jsonObject)) {
                            whenFinishedRegistering();
                        } else {
                            Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(JSONFactory.createRegisterData(uname, vname, pass));
            }
        }
    }

    private void whenFinishedRegistering() {
        LoginFragment loginFragment = new LoginFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment)
                .commit();
    }
}
