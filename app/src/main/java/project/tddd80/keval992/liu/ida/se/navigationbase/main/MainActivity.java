package project.tddd80.keval992.liu.ida.se.navigationbase.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONObject;

import project.tddd80.keval992.liu.ida.se.navigationbase.R;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.GcmClientManager;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.GcmMessageHandler;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.HttpRequestTask;
import project.tddd80.keval992.liu.ida.se.navigationbase.network.JSONFactory;


public class MainActivity extends AppCompatActivity {

    String PROJECT_NUMBER = "1065612490812"; // SUPERDUPERSECRETHIDDENNUMBERIKNOWRIGHT?
    private Toolbar toolbar;
    private GcmClientManager pushClientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initGcm();
        LoginInfo.loadFromPreferences(this);
        if (savedInstanceState == null) {
            MenuFragment menuFragment;
            if (LoginInfo.hasLoggedIn()) {
                if (LoginInfo.isAdvancedUser()) {
                    menuFragment = MenuFragment.advancedMode();
                } else {
                    menuFragment = MenuFragment.basicMode();
                }
            } else {
                menuFragment = MenuFragment.basicMode();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, menuFragment)
                    .commit();
        }
    }

    public final void setShowNavigationButton() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initGcm() {
        pushClientManager = new GcmClientManager(this, PROJECT_NUMBER);
    }

    /**
     * Only to be called when user has logged in and LoginInfo has been set.
     */
    public final void registerGcm() {
        pushClientManager.registerIfNeeded(new GcmClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LoginInfo.saveRegistrationId(registrationId);
                Log.i("MainActivity", "Registration ID saved successfully to LoginInfo.");
                new HttpRequestTask("new") {

                    @Override
                    protected void atPostExecute(JSONObject jsonObject) {
                        Log.i("MainActivity", "Advanced users device has been registered at the server.");
                    }
                }.execute(JSONFactory.createNewRegIdData(LoginInfo.getUserId(), registrationId));
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onResume() {
        GcmMessageHandler.myMainActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        GcmMessageHandler.myMainActivity = null;
        LoginInfo.saveToPreferences(this);
        super.onPause();
    }
}
