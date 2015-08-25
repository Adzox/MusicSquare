package project.tddd80.keval992.liu.ida.se.navigationbase.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.LoginInfo;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.MainActivity;
import project.tddd80.keval992.liu.ida.se.navigationbase.main.MenuFragment;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText username, password;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        view.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        view.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(false);
            }
        });
        view.findViewById(R.id.register_advanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(true);
            }
        });
        return view;
    }

    private void signUp() {
        new HttpRequestTask("login", getActivity()) {

            @Override
            protected void atPostExecute(JSONObject jsonObject) {
                if (JSONParser.wasSuccessful(jsonObject)) {
                    JSONParser.extractLoginData(getActivity(), jsonObject);
                    MenuFragment menuFragment;
                    if (LoginInfo.isAdvancedUser()) {
                        System.out.println("ADVANCD MODE");
                        ((MainActivity) LoginFragment.this.getActivity()).registerGcm();
                        menuFragment = MenuFragment.advancedMode();
                    } else {
                        System.out.println("BASIC");
                        menuFragment = MenuFragment.basicMode();
                    }
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, menuFragment)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), JSONParser.extractError(jsonObject), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(JSONFactory.createLoginData(username.getText().toString(), password.getText().toString()));
    }

    private void register(boolean advanced) {
        RegisterFragment registerFragment = RegisterFragment.newInstance(advanced);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, registerFragment)
                .addToBackStack(null)
                .commit();
    }
}
